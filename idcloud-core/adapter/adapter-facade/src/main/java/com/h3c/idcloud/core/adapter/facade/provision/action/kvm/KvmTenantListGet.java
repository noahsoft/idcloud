package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.tenant.Tenant;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmTenantListGet extends ActionKvm {

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        Tenants tenants = new Tenants();

        log.info("tenant list get");

        WebClient client = WebClient.create(this.getAdapterUrl(), this.providers);

        client.accept(MediaType.APPLICATION_JSON);
        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("/tenants").type(MediaType.APPLICATION_JSON).get();

            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                if (respMap.containsKey("tenants")) {
                    List<Tenant> tenants2 = BaseUtil.castObject(respMap.get("tenants"), List.class);
                    tenants.getTenantListGetResult().setTenants(tenants2);
                }
                tenants.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when creating tenant");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return tenants;
    }

}
