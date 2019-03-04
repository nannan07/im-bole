package com.allmsi.plugin.flow.service;

import java.util.List;

import com.allmsi.plugin.flow.model.ivo.FlowNodeIVo;
import com.allmsi.plugin.flow.model.ovo.FlowDealModelOVo;
import com.allmsi.plugin.flow.model.ovo.FlowNodeOVo;

public interface FlowNodeService {
	
	List<FlowNodeOVo> selectFlowNodeByFlowId(String flowId);
	
	FlowNodeOVo selectByPrimaryKey(String id);
	
	String addFlowNode(FlowNodeIVo FlowNodeIVo);
	
	String updateFlowNode(FlowNodeIVo FlowNodeIVo);
	
	boolean delFlowNode(String id,String uUserId);
	
	List<FlowDealModelOVo> receiverSelect(String nodeId, String routeId);

}
