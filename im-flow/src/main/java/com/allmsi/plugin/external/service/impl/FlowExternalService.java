package com.allmsi.plugin.external.service.impl;

import java.util.List;
import java.util.Map;

import com.allmsi.plugin.flow.model.external.FlowDealIdATypeModel;
import com.allmsi.plugin.flow.model.external.FlowUserModel;

public interface FlowExternalService {

	/**
	 * key:userId 
	 * value:FlowUserModel
	 */
	Map<String, FlowUserModel> getFlowUserInfoList(List<String> userIds);

	String getDealNameByDealIdAndType(String dealId, String dealType);

	/**
	 * key:dealId 
	 * value:dealName
	 */
	Map<String, String> getDealNameByDealIdAndType(List<FlowDealIdATypeModel> dealIdAndDealType);

	/**
	 * key:type(01/02/03)
	 * value:dealIdList
	 */
	Map<String, List<String>> getUserAuthIdSort(String userId);

}
