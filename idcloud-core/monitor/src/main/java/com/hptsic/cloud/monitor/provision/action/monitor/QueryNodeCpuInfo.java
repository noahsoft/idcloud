package com.hptsic.cloud.monitor.provision.action.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.NodeDeviceInfoGet;
import com.hptsic.cloud.monitor.pojo.node.NodeCpuInfo;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.MonitorException;
import com.hptsic.cloud.monitor.provision.model.CommonAdapterResult;
import com.hptsic.cloud.monitor.provision.model.NodeMonitorResult;
import com.hptsic.cloud.monitor.util.BaseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.client.ClientException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Service
public class QueryNodeCpuInfo extends ActionNode{

	@Override
	protected CommonAdapterResult execute(Base base) throws MonitorException,
			AdapterUnavailableException {
		log.info("get nodeCpuInfo monitor message");
		NodeDeviceInfoGet infoGet = (NodeDeviceInfoGet) base;
		NodeMonitorResult result = new NodeMonitorResult();
		WebClient client = WebClient.create(this.getAdapterUrl(),
				this.providers);

		client.accept(MediaType.APPLICATION_JSON);

		try {
			if(base.isRetrieveAll()){
				client.query("retrieveAll", base.isRetrieveAll());
			}
			if (!CollectionUtils.isEmpty(infoGet.getNodeIds())) {
				client.query("node_id", infoGet.getNodeIds().get(0));
				if (!StringUtils.isEmpty(infoGet.getTimeFrom())) {
					client.query("time_form", infoGet.getTimeFrom());
				}
				if (!StringUtils.isEmpty(infoGet.getTimeTo())) {
					client.query("time_till", infoGet.getTimeTo());
				}
			}
			Response resp = client.path("/data").query("type", "cpu")
					.type(MediaType.APPLICATION_JSON).get();
			
			if (resp.getStatus() == 200) {
				String entity = resp.readEntity(String.class);
				NodeCpuInfo info = BaseUtil.castObject(entity, NodeCpuInfo.class);
				result.setCpuInfo(info);
				result.setSuccess(true);
			}else {
				String entity = resp.readEntity(String.class);
				MonitorException mException = new MonitorException(resp.getStatus()+"",entity);
				log.error(mException.getErrMsg());
				throw mException;
			}
		} catch (ClientException e) {
			throw new AdapterUnavailableException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
