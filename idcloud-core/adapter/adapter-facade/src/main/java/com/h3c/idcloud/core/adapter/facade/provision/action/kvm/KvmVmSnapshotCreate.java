package com.h3c.idcloud.core.adapter.facade.provision.action.kvm;

import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.KvmException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.scan.Template;
import com.h3c.idcloud.core.adapter.facade.util.BaseUtil;
import com.h3c.idcloud.core.adapter.pojo.common.Base;
import com.h3c.idcloud.core.adapter.pojo.vm.VmSnapshotCreate;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
public class KvmVmSnapshotCreate extends ActionKvm {

    private static Logger log = LoggerFactory.getLogger(KvmVmImageCreate.class);

    @Override
    protected CommonAdapterResult execute(Base base) throws KvmException,
            AdapterUnavailableException {
        VmSnapshotCreate vmSnapshotCreate = (VmSnapshotCreate) base;

        Template template = new Template();

        String json = helper.getParam(vmSnapshotCreate.getProviderType(), vmSnapshotCreate);

        log.info("vm vmSnapshotCreate post data : " + json);

        WebClient client = WebClient.create(this.getAdapterUrl(), providers);

        HTTPConduit conduit = client.getConfig(client).getHttpConduit();

        conduit.getClient().setReceiveTimeout(0);

        client.accept(MediaType.APPLICATION_JSON);

        client = BaseUtil.setHeaders(client, base);
        try {
            Response resp = client.path("/" + vmSnapshotCreate.getTenantId() + "/servers/" + vmSnapshotCreate.getServerId() + "/action").type(MediaType.APPLICATION_JSON).post(json);

            if (resp.getStatus() == 200) {
                Map respMap = resp.readEntity(Map.class);
                Map imageMap = (Map) respMap.get("image");
                template.setImageId((String) imageMap.get("id"));
                template.setImageName((String) imageMap.get("name"));
                template.setSuccess(true);
            } else {
                KvmException kvmException = BaseUtil.changeToKvmException(resp);
                log.error(kvmException.getErrMsg());
                throw kvmException;
            }
        } catch (ClientException e) {
            log.info("kvm adapter is not available when vmSnapshotCreating vm");
            throw new AdapterUnavailableException();
        } catch (Exception e) {
            if (e instanceof KvmException) {
                throw (KvmException) e;
            } else {
                throw new KvmException("500", e.getMessage());
            }
        }
        return template;
    }
}
