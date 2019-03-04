package com.allmsi.plugin.flow.service;

import java.util.List;

import com.allmsi.plugin.flow.model.ivo.FlowNodeDealIVo;
import com.allmsi.plugin.flow.model.ovo.FlowNodeDealOVo;

public interface FlowNodeDealService {

	List<FlowNodeDealOVo> selectFlowNodeDealList(String nodeId);

	FlowNodeDealOVo selectFlowNodeDealInfo(String id);
	
	String addFlowNodeDeal(FlowNodeDealIVo flowNodeDealIVO);

	String updateFlowNodeDeal(FlowNodeDealIVo flowNodeDealIVO);

	boolean delFlowNodeDeal(String id, String uUserId);

}
