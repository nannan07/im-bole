package com.allmsi.plugin.flow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.plugin.flow.dao.FlowNodeButtonMapper;
import com.allmsi.plugin.flow.dao.FlowNodeDealMapper;
import com.allmsi.plugin.flow.dao.FlowNodeMapper;
import com.allmsi.plugin.flow.dao.FlowRouteMapper;
import com.allmsi.plugin.flow.model.FlowNode;
import com.allmsi.plugin.flow.model.FlowNodeDeal;
import com.allmsi.plugin.flow.model.external.FlowDealIdATypeModel;
import com.allmsi.plugin.flow.model.external.FlowUserModel;
import com.allmsi.plugin.flow.model.ivo.FlowNodeIVo;
import com.allmsi.plugin.flow.model.ovo.FlowDealModelOVo;
import com.allmsi.plugin.flow.model.ovo.FlowNodeOVo;
import com.allmsi.plugin.flow.service.FlowNodeService;
import com.allmsi.plugin.flow.service.FlowUserService;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class FlowNodeServiceImpl implements FlowNodeService {

	@Resource
	private FlowNodeMapper flowNodeDao;
	
	@Resource
	private FlowNodeButtonMapper flowNodeButtonDao;
	
	@Resource
	private FlowRouteMapper flowRouteDao;
	
	@Resource
	private FlowNodeDealMapper flowNodeDealDao;
	
	@Resource
	private FlowUserService flowUserService;

	@Override
	public List<FlowNodeOVo> selectFlowNodeByFlowId(String flowId) {
		List<FlowNodeOVo> list = new ArrayList<FlowNodeOVo>();
		if(StrUtil.isEmpty(flowId)){
			return list;
		}
		
		List<FlowNode> flowNodeList = flowNodeDao.selectFlowNodeByFlowId(flowId);
		List<String> ids = new ArrayList<String>();
		for (FlowNode fn : flowNodeList) {
			if(StrUtil.notEmpty(fn.getcUserId())){
				ids.add(fn.getcUserId());
			}
		}
		Map<String, FlowUserModel>  map = flowUserService.getFlowUserList(ids);
		for (FlowNode fn : flowNodeList) {
			FlowNodeOVo flowNodeOVo = new FlowNodeOVo(fn);
			if(StrUtil.notEmpty(fn.getcUserId())){
				FlowUserModel flowUser = map.get(fn.getcUserId());
				if(flowUser != null){
					flowNodeOVo.setUser(flowUser);
				}
			}
			list.add(flowNodeOVo);
		}
		return list;
	}

	@Override
	public FlowNodeOVo selectByPrimaryKey(String id) {
		if (StrUtil.isEmpty(id)) {
			return null;
		}
		FlowNode flowNode = flowNodeDao.selectByPrimaryKey(id);
		if(flowNode != null){
			FlowNodeOVo flowNodeOVo = new FlowNodeOVo(flowNode);
			if(StrUtil.notEmpty(flowNode.getcUserId())){
				Map<String,FlowUserModel> map = flowUserService.getFlowUserSingle(flowNode.getcUserId());
				FlowUserModel flowUser =map.get(flowNode.getcUserId());
				if(flowUser != null){
					flowNodeOVo.setUser(flowUser);
				}
			}
			return flowNodeOVo;
		}
		return null;
	}

	@Override
	public String addFlowNode(FlowNodeIVo flowNodeIVo) {
		if (flowNodeIVo == null) {
			return null;
		}
		FlowNode flowNode = new FlowNode(flowNodeIVo);
		flowNode.setcUserId(flowNodeIVo.getcUserId());
		flowNode.setId(UUIDUtil.getUUID());
		return (flowNodeDao.insertSelective(flowNode) == 0) ? null : flowNode.getId();
	}

	@Override
	public String updateFlowNode(FlowNodeIVo flowNodeIVo) {
		if (flowNodeIVo == null || StrUtil.isEmpty(flowNodeIVo.getId())) {
			return null;
		}
		FlowNode flowNode = new FlowNode(flowNodeIVo);
		flowNode.setuUserId(flowNodeIVo.getcUserId());
		return (flowNodeDao.updateByPrimaryKeySelective(flowNode) == 0) ? null : flowNode.getId();
	}

	@Override
	public boolean delFlowNode(String id, String uUserId) {
		if (StrUtil.isEmpty(id)) {
			return false;
		}
		flowNodeButtonDao.deleteByNodeId(id);
		flowRouteDao.deleteByNodeId(id);
		flowNodeDealDao.deleteByNodeId(id);
		
		FlowNode flowNode = new FlowNode();
		flowNode.setId(id);
		flowNode.setuUserId(uUserId);
		flowNodeDao.deleteByPrimaryKey(flowNode);
		return true;
	}
	@Override
	public List<FlowDealModelOVo> receiverSelect(String nodeId, String routeId) {
		List<FlowDealModelOVo> lists = new ArrayList<FlowDealModelOVo>();
		if(StrUtil.isEmpty(routeId) || StrUtil.isEmpty(nodeId)){
			return lists;
		}
		FlowNodeDeal flowNodeDeal = new FlowNodeDeal();
		flowNodeDeal.setNodeId(nodeId);
		flowNodeDeal.setRouteId(routeId);
		List<FlowNodeDeal> flowNodeDealList = flowNodeDealDao.receiverSelect(flowNodeDeal);
		
		List<FlowDealIdATypeModel> dealIds = new ArrayList<FlowDealIdATypeModel>();
		for (FlowNodeDeal flowNodeDeal2 : flowNodeDealList) {
			FlowDealIdATypeModel fdm = new FlowDealIdATypeModel();
			fdm.setDealId(flowNodeDeal2.getNodeDealId());
			fdm.setDealType(flowNodeDeal2.getNodeDealType());
			dealIds.add(fdm);
		}
		Map<String, String> map = flowUserService.getFlowDealList(dealIds);
		for (FlowNodeDeal flowNodeDeal2 : flowNodeDealList) {
			String name = map.get(flowNodeDeal2.getNodeDealId());
			FlowDealModelOVo fdm = new FlowDealModelOVo(flowNodeDeal2);
			fdm.setDealName(name);
			lists.add(fdm);
		}
		return lists;
	}
}
