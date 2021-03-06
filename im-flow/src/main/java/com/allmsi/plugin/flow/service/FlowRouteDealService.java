package com.allmsi.plugin.flow.service;

import java.util.List;

import com.allmsi.plugin.flow.model.ivo.FlowRouteDealIVo;
import com.allmsi.plugin.flow.model.ovo.FlowRouteDealOVo;

public interface FlowRouteDealService {
	
	List<FlowRouteDealOVo> selectFlowRouteDealList(String routeId);

	FlowRouteDealOVo selectFlowRouteDealInfo(String id);

	String addFlowRouteDeal(FlowRouteDealIVo flowRouteDealIVo);

	String updateFlowRouteDeal(FlowRouteDealIVo flowRouteDealIVo);

	boolean delFlowRouteDeal(String id, String uUserId);

}
