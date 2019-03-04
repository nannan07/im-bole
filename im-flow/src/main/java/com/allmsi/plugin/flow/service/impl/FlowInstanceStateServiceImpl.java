package com.allmsi.plugin.flow.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.plugin.flow.dao.FlowInstanceStateMapper;
import com.allmsi.plugin.flow.model.FlowInstanceState;
import com.allmsi.plugin.flow.model.external.FlowUserModel;
import com.allmsi.plugin.flow.model.ivo.FlowInstanceLogIVo;
import com.allmsi.plugin.flow.model.ivo.FlowInstanceStateIVo;
import com.allmsi.plugin.flow.model.ovo.FlowInstanceStateOVo;
import com.allmsi.plugin.flow.service.FlowInstanceLogService;
import com.allmsi.plugin.flow.service.FlowInstanceStateService;
import com.allmsi.plugin.flow.service.FlowUserService;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class FlowInstanceStateServiceImpl implements FlowInstanceStateService {

	@Resource
	private FlowInstanceStateMapper flowInstanceStateDao;

	@Resource
	private FlowUserService flowUserService;

	@Resource
	private FlowInstanceLogService flowInstanceLogService;

	@Override
	public FlowInstanceStateOVo selectFlowInstanceState(String instanceId) {
		if (StrUtil.isEmpty(instanceId)) {
			return null;
		}
		FlowInstanceState fis = flowInstanceStateDao.selectByInstanceId(instanceId);
		FlowInstanceStateOVo flowInstanceStateOVo = new FlowInstanceStateOVo(fis);

		String preDealId = fis.getPreDealId();
		String preDealName = flowUserService.getFlowUserSingle(preDealId).get(preDealId).getName();
		flowInstanceStateOVo.setPreDealName(preDealName);

		String userId = fis.getuUserId();
		if (StrUtil.notEmpty(userId)) {
			FlowUserModel user = flowUserService.getFlowUserSingle(userId).get(userId);
			flowInstanceStateOVo.setUser(user);
		}

		String sufDealId = fis.getSufDealId();
		String sufDealType = fis.getSufDealType();

		String sufDealName = flowUserService.getFlowDealSingle(sufDealId, sufDealType);
		flowInstanceStateOVo.setSufDealName(sufDealName);
		return flowInstanceStateOVo;
	}

	@Override
	public String addFlowInstanceState(FlowInstanceStateIVo flowInstanceStateIVo) {
		if (flowInstanceStateIVo == null || StrUtil.isEmpty(flowInstanceStateIVo.getInstanceId())) {
			return null;
		}
		FlowInstanceState fis = new FlowInstanceState(flowInstanceStateIVo);
		String id = UUIDUtil.getUUID();
		fis.setId(id);
		int msg = flowInstanceStateDao.insertSelective(fis);
		flowInstanceLogService.addFlowInstanceLog(new FlowInstanceLogIVo(flowInstanceStateIVo));
		return (msg == 0) ? null : id;
	}

	@Override
	public String updateFlowInstanceState(FlowInstanceStateIVo flowInstanceStateIVo) {
		if (flowInstanceStateIVo == null || StrUtil.isEmpty(flowInstanceStateIVo.getId())
				|| StrUtil.isEmpty(flowInstanceStateIVo.getInstanceId())) {
			return null;
		}
		FlowInstanceState fis = new FlowInstanceState(flowInstanceStateIVo);
		int msg = flowInstanceStateDao.updateByPrimaryKeySelective(fis);
		flowInstanceLogService.addFlowInstanceLog(new FlowInstanceLogIVo(flowInstanceStateIVo));
		return (msg == 0) ? null : fis.getId();
	}

}
