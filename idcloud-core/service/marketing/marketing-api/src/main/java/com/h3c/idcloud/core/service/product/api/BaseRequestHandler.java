package com.h3c.idcloud.core.service.product.api;


import com.h3c.idcloud.core.pojo.dto.product.ServiceInstance;
import com.h3c.idcloud.core.pojo.instance.ResInstResult;

import java.util.List;
import java.util.Map;


public abstract class BaseRequestHandler implements ResourceRequestHanlder{

	/**
	 * 调用申请资源接口
	 * 
	 * @param processObjectId 申请资源对象ids
	 * @return TODO
	 */
	public boolean invoke(Long processObjectId) {
		return false;
	}

	/**
	 * 调用申请资源接口
	 * @param params 动态参数
	 * @return
	 */
	public boolean invoke(Map<String, Object> params) {
		return false;
	}

	/**
	 * 调用申请资源接口
	 */
	public boolean invoke(List<Long> instanceSids) {
		return false;
	}

	/**
	 * 申请资源接口回调方法
	 * 
	 * @param result
	 */
	public void operate(ResInstResult result) {
		
	}
	
	
	/**
	 * 完成操作数据处理
	 */
	public void handlerData(ServiceInstance instance, String resVmSid) {
		
	}
	
}
