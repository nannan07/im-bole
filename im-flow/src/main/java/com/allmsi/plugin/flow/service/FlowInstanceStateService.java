package com.allmsi.plugin.flow.service;

import com.allmsi.plugin.flow.model.ivo.FlowInstanceStateIVo;
import com.allmsi.plugin.flow.model.ovo.FlowInstanceStateOVo;

public interface FlowInstanceStateService {
	
	FlowInstanceStateOVo selectFlowInstanceState(String instanceId);

	String addFlowInstanceState(FlowInstanceStateIVo flowInstanceStateIVo);
	
	String updateFlowInstanceState(FlowInstanceStateIVo flowInstanceStateIVo);
}
