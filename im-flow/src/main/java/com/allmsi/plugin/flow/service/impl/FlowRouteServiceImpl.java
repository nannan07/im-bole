package com.allmsi.plugin.flow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.plugin.flow.dao.FlowRouteDealMapper;
import com.allmsi.plugin.flow.dao.FlowRouteMapper;
import com.allmsi.plugin.flow.model.FlowRoute;
import com.allmsi.plugin.flow.model.external.FlowUserModel;
import com.allmsi.plugin.flow.model.ivo.FlowRouteIVo;
import com.allmsi.plugin.flow.model.ovo.FlowRouteOVo;
import com.allmsi.plugin.flow.service.FlowRouteService;
import com.allmsi.plugin.flow.service.FlowUserService;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class FlowRouteServiceImpl implements FlowRouteService {

	@Resource
	private FlowRouteMapper flowRouteDao;
	
	@Resource
	private FlowRouteDealMapper flowRouteDealDao;

	@Resource
	private FlowUserService flowUserService;

	@Override
	public List<FlowRouteOVo> selectRouteList(String flowId) {
		List<FlowRouteOVo> list = new ArrayList<FlowRouteOVo>();
		if (StrUtil.isEmpty(flowId)) {
			return list;
		}
		List<FlowRoute> flowRouteList = flowRouteDao.selectRouteList(flowId);
		List<String> ids = new ArrayList<String>();
		for (FlowRoute flowRoute : flowRouteList) {
			ids.add(flowRoute.getcUserId());
		}
		Map<String, FlowUserModel> FlowUserMap = flowUserService.getFlowUserList(ids);
		for (FlowRoute flowRoute : flowRouteList) {
			FlowRouteOVo flowRouteVo = new FlowRouteOVo(flowRoute);
			if(StrUtil.notEmpty(flowRoute.getcUserId())){
				FlowUserModel flowUser = FlowUserMap.get(flowRoute.getcUserId());
				if(flowUser != null){
					flowRouteVo.setUser(flowUser);
				}
			}
			list.add(flowRouteVo);
		}
		return list;
	}

	@Override
	public FlowRouteOVo selectRouteInfo(String id) {
		if (StrUtil.isEmpty(id)) {
			return null;
		}
		FlowRoute flowRoute = flowRouteDao.selectByPrimaryKey(id);
		if(flowRoute != null){
			FlowRouteOVo flowRouteOVo = new FlowRouteOVo(flowRoute);
			if(StrUtil.notEmpty(flowRoute.getcUserId())){
				Map<String, FlowUserModel> map = flowUserService.getFlowUserSingle(flowRoute.getcUserId());
				FlowUserModel flowUser = map.get(flowRoute.getcUserId());
				if(flowUser != null){
					flowRouteOVo.setUser(flowUser);
				}
			}
			return flowRouteOVo;
		}
		return null;
	}

	@Override
	public String addFlowRoute(FlowRouteIVo flowRouteIVo) {
		if (flowRouteIVo == null) {
			return null;
		}
		FlowRoute flowRoute = new FlowRoute(flowRouteIVo);
		flowRoute.setId(UUIDUtil.getUUID());
		flowRoute.setcUserId(flowRouteIVo.getcUserId());
		return (flowRouteDao.insertSelective(flowRoute) == 0) ? null : flowRoute.getId();
	}

	@Override
	public String updateFlowRoute(FlowRouteIVo flowRouteIVo) {
		if (flowRouteIVo == null || StrUtil.isEmpty(flowRouteIVo.getId())) {
			return null;
		}
		FlowRoute flowRoute = new FlowRoute(flowRouteIVo);
		flowRoute.setuUserId(flowRouteIVo.getcUserId());
		return (flowRouteDao.updateByPrimaryKeySelective(flowRoute) == 0) ? null : flowRoute.getId();
	}

	@Override
	public boolean delFlowRoute(String id, String uUserId) {
		if (StrUtil.isEmpty(id)) {
			return false;
		}
		flowRouteDealDao.deleteByRouteId(id);
		FlowRoute flowRoute = new FlowRoute();
		flowRoute.setId(id);
		flowRoute.setuUserId(uUserId);
		flowRouteDao.deleteByPrimaryKey(flowRoute);
		return true;
	}

	@Override
	public List<FlowRouteOVo> selectRouteListByPreNode(String flowId, String preNode) {
		List<FlowRouteOVo> flowRouteVoList = new ArrayList<FlowRouteOVo>();
		if(StrUtil.isEmpty(flowId) || StrUtil.isEmpty(preNode)){
			return flowRouteVoList;
		}
		FlowRoute flowRoute = new FlowRoute();
		flowRoute.setFlowId(flowId);
		flowRoute.setPreNode(preNode);
		List<FlowRoute> flowRouteList = flowRouteDao.selectRouteListByPreNode(flowRoute);
		for (FlowRoute flowRoute2 : flowRouteList) {
			flowRouteVoList.add(new FlowRouteOVo(flowRoute2));
		}
		return flowRouteVoList;
	}
}
