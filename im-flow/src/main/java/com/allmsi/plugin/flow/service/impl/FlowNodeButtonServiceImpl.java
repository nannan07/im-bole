package com.allmsi.plugin.flow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.plugin.flow.dao.FlowNodeButtonMapper;
import com.allmsi.plugin.flow.model.FlowNodeButton;
import com.allmsi.plugin.flow.model.external.FlowUserModel;
import com.allmsi.plugin.flow.model.ivo.FlowNodeButtonIVo;
import com.allmsi.plugin.flow.model.ovo.FlowNodeButtonOVo;
import com.allmsi.plugin.flow.service.FlowNodeButtonService;
import com.allmsi.plugin.flow.service.FlowUserService;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class FlowNodeButtonServiceImpl implements FlowNodeButtonService {

	@Resource
	private FlowNodeButtonMapper flowNodeButtonDao;

	@Resource
	private FlowUserService flowUserService;

	@Override
	public List<FlowNodeButtonOVo> selectNodeButtonByNodeId(String nodeId) {
		List<FlowNodeButton> flowNodeButtonPList = flowNodeButtonDao.selectByNodeId(nodeId);
		List<String> ids = new ArrayList<String>();
		for (FlowNodeButton fb : flowNodeButtonPList) {
			if (StrUtil.notEmpty(fb.getcUserId())) {
				ids.add(fb.getcUserId());
			}
		}
		Map<String, FlowUserModel> map = flowUserService.getFlowUserList(ids);
		List<FlowNodeButtonOVo> flowNodeButtonVList = new ArrayList<FlowNodeButtonOVo>();
		for (FlowNodeButton fb : flowNodeButtonPList) {
			FlowNodeButtonOVo flowNodeB = new FlowNodeButtonOVo(fb);
			if (StrUtil.notEmpty(fb.getcUserId())) {
				FlowUserModel fu = map.get(fb.getcUserId());
				if (fu != null) {
					flowNodeB.setUser(fu);
				}
			}
			flowNodeButtonVList.add(flowNodeB);
		}
		return flowNodeButtonVList;
	}

	@Override
	public FlowNodeButtonOVo selectByPrimaryKey(String id) {
		if (StrUtil.isEmpty(id)) {
			return null;
		}
		FlowNodeButton fb = flowNodeButtonDao.selectByPrimaryKey(id);
		if (fb != null) {
			FlowNodeButtonOVo fbVo = new FlowNodeButtonOVo(fb);
			if(StrUtil.notEmpty(fb.getcUserId())){
				Map<String, FlowUserModel> map = flowUserService.getFlowUserSingle(fb.getcUserId());
				FlowUserModel fu = map.get(fb.getcUserId());
				fbVo.setUser(fu);
			}
			return fbVo;
		}
		return null;
	}

	@Override
	public String addFlowNodeButton(FlowNodeButtonIVo flowNodeButtonIVo) {
		if (flowNodeButtonIVo == null) {
			return null;
		}
		FlowNodeButton fb = new FlowNodeButton(flowNodeButtonIVo);
		fb.setId(UUIDUtil.getUUID());
		fb.setcUserId(flowNodeButtonIVo.getcUserId());
		return (flowNodeButtonDao.insertSelective(fb) == 0) ? null : fb.getId();
	}

	@Override
	public String updateFlowNodeButton(FlowNodeButtonIVo flowNodeButtonIVo) {
		if (flowNodeButtonIVo == null || StrUtil.isEmpty(flowNodeButtonIVo.getId())) {
			return null;
		}
		FlowNodeButton fb = new FlowNodeButton(flowNodeButtonIVo);
		fb.setuUserId(flowNodeButtonIVo.getcUserId());
		return (flowNodeButtonDao.updateByPrimaryKeySelective(fb) == 0) ? null : fb.getId();
	}

	@Override
	public boolean delFlowNodeButton(String id, String uUserId) {
		if (StrUtil.isEmpty(id)) {
			return false;
		}
		FlowNodeButton fb = new FlowNodeButton();
		fb.setId(id);
		fb.setuUserId(uUserId);
		flowNodeButtonDao.deleteByPrimaryKey(fb);
		return true;
	}
}
