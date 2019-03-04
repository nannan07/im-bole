package com.allmsi.plugin.flow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allmsi.plugin.external.service.impl.FlowExternalService;
import com.allmsi.plugin.flow.model.external.FlowDealIdATypeModel;
import com.allmsi.plugin.flow.model.external.FlowUserModel;
import com.allmsi.plugin.flow.service.FlowUserService;
import com.allmsi.sys.config.PropertyConfig;
import com.allmsi.sys.config.SpringContextRegister;
import com.allmsi.sys.util.StrUtil;

@Service
public class FlowUserServiceImpl implements FlowUserService {

	@Autowired
	private PropertyConfig properties;

	@Autowired
	private SpringContextRegister springContextRegister;

	private final String FLOW_USERLIST = "flow_userList";

	private final String DEFAULT_FLOW_SERVICE = "defaultFlowExternalService";

	@Override
	public Map<String, FlowUserModel> getFlowUserSingle(String id) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		return getFlowUserList(ids);
	}

	@Override
	public Map<String, FlowUserModel> getFlowUserList(List<String> ids) {
		FlowExternalService flowExternalService = getFlowExternalService();
		return flowExternalService.getFlowUserInfoList(ids);
	}

	@Override
	public String getFlowDealSingle(String dealId, String dealType) {
		if (StrUtil.isEmpty(dealId) || StrUtil.isEmpty(dealType)) {
			return null;
		}
		FlowExternalService flowExternalService = getFlowExternalService();
		return flowExternalService.getDealNameByDealIdAndType(dealId, dealType);
	}

	@Override
	public Map<String, String> getFlowDealList(List<FlowDealIdATypeModel> dealIds) {
		FlowExternalService flowExternalService = getFlowExternalService();
		return flowExternalService.getDealNameByDealIdAndType(dealIds);
	}

	@Override
	public Map<String, List<String>> getUserAuthIdSort(String userId) {
		FlowExternalService flowExternalService = getFlowExternalService();
		return flowExternalService.getUserAuthIdSort(userId);
	}

	private FlowExternalService getFlowExternalService() {
		FlowExternalService flowExternalService = null;
		String className = properties.getProperty(FLOW_USERLIST);
		if (StrUtil.isEmpty(className)) {
			flowExternalService = springContextRegister.getServiceImpl(FlowExternalService.class, DEFAULT_FLOW_SERVICE);
		} else {
			flowExternalService = springContextRegister.getServiceImpl(FlowExternalService.class, className);
		}
		return flowExternalService;
	}

}
