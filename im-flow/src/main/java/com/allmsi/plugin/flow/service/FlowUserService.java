package com.allmsi.plugin.flow.service;

import java.util.List;
import java.util.Map;

import com.allmsi.plugin.flow.model.external.FlowDealIdATypeModel;
import com.allmsi.plugin.flow.model.external.FlowUserModel;

public interface FlowUserService {
	
	Map<String,FlowUserModel> getFlowUserSingle(String id);
	
	Map<String,FlowUserModel> getFlowUserList(List<String> ids);
	
	String getFlowDealSingle(String dealId, String dealType);

	Map<String, String> getFlowDealList(List<FlowDealIdATypeModel> dealIds);

	Map<String, List<String>> getUserAuthIdSort(String userId);
	
}
