package com.allmsi.plugin.flow.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.plugin.flow.model.ivo.FlowInstanceStateIVo;
import com.allmsi.plugin.flow.service.FlowInstanceStateService;
import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.util.StrUtil;

@Controller
@RequestMapping("/flowInstanceState")
public class FlowInstanceStateController {

	@Resource
	private FlowInstanceStateService flowInstanceStateService;

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public Object selectFlowInstanceState(String instanceId) {
		if (StrUtil.isEmpty(instanceId)) {
			return new WarnProtocol();
		}
		return new SuccessProtocol("", flowInstanceStateService.selectFlowInstanceState(instanceId));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object saveFlowInstanceState(FlowInstanceStateIVo flowInstanceStateIVo) {
		if (flowInstanceStateIVo == null || StrUtil.isEmpty(flowInstanceStateIVo.getInstanceId())) {
			return new WarnProtocol();
		}
		String id = flowInstanceStateIVo.getId();
		if (StrUtil.isEmpty(id)) {
			id = flowInstanceStateService.addFlowInstanceState(flowInstanceStateIVo);
		} else {
			id = flowInstanceStateService.updateFlowInstanceState(flowInstanceStateIVo);
		}
		return (StrUtil.isEmpty(id)) ? new WarnProtocol() : new SuccessProtocol("成功", id);
	}
}
