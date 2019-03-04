package com.allmsi.plugin.external.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.allmsi.plugin.flow.model.external.FlowDealIdATypeModel;
import com.allmsi.plugin.flow.model.external.FlowUserModel;

@Service("defaultFlowExternalService")
public class FlowExternalServiceImpl implements FlowExternalService {

	@Override
	public Map<String, FlowUserModel> getFlowUserInfoList(List<String> ids) {
		Map<String, FlowUserModel> map = new HashMap<String, FlowUserModel>();
		for (String key : ids) {
			FlowUserModel f = new FlowUserModel();
			f.setName("name");
			f.setType("type");
			map.put(key, f);
		}
		return map;
	}

	@Override
	public Map<String, String> getDealNameByDealIdAndType(List<FlowDealIdATypeModel> dealIds) {
		Map<String, String> map = new HashMap<String, String>();
		for (FlowDealIdATypeModel model : dealIds) {
			String dealName = null;
			map.put(model.getDealId(), dealName);
		}
		return map;
	}

	@Override
	public String getDealNameByDealIdAndType(String dealId, String dealType) {
		String dealName = null;
		return dealName;
	}

	@Override
	public Map<String, List<String>> getUserAuthIdSort(String userId) {
		Map<String, List<String>> authMap = new HashMap<String, List<String>>();
		List<String> type1IdList = new ArrayList<String>();
		type1IdList.add("01");
		type1IdList.add("011");
		type1IdList.add("0111");
		authMap.put("01", type1IdList);

		List<String> type2IdList = new ArrayList<String>();
		type2IdList.add("02");
		type2IdList.add("022");
		type2IdList.add("0222");
		authMap.put("02", type2IdList);
		return authMap;
	}

}
