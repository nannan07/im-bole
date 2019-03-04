package com.allmsi.plugin.flow.service;

import java.util.List;

import com.allmsi.plugin.flow.model.ivo.FlowRouteIVo;
import com.allmsi.plugin.flow.model.ovo.FlowRouteOVo;

public interface FlowRouteService {

	List<FlowRouteOVo> selectRouteList(String flowId);
	
	FlowRouteOVo selectRouteInfo(String id);

	String addFlowRoute(FlowRouteIVo flowRouteIVo);

	String updateFlowRoute(FlowRouteIVo flowRouteIVo);

	boolean delFlowRoute(String id, String uUserId);

	List<FlowRouteOVo> selectRouteListByPreNode(String flowId, String preNode);

}
