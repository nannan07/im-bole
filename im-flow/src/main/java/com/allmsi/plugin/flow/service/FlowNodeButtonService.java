package com.allmsi.plugin.flow.service;

import java.util.List;

import com.allmsi.plugin.flow.model.ivo.FlowNodeButtonIVo;
import com.allmsi.plugin.flow.model.ovo.FlowNodeButtonOVo;

public interface FlowNodeButtonService {

	List<FlowNodeButtonOVo> selectNodeButtonByNodeId(String nodeId);

	FlowNodeButtonOVo selectByPrimaryKey(String id);

	String addFlowNodeButton(FlowNodeButtonIVo flowNodeButtonIVo);

	String updateFlowNodeButton(FlowNodeButtonIVo flowNodeButtonIVo);

	boolean delFlowNodeButton(String id, String uUserId);
	
}
