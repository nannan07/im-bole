package com.allmsi.plugin.flow.dao;

import java.util.List;

import com.allmsi.plugin.flow.model.FlowNodeDeal;

public interface FlowNodeDealMapper {
    List<FlowNodeDeal> selectByNodeId(String nodeId);

	FlowNodeDeal selectByPrimaryKey(String id);

    int insertSelective(FlowNodeDeal record);

	int updateByPrimaryKeySelective(FlowNodeDeal record);

	int deleteByPrimaryKey(FlowNodeDeal fnd);

	int deleteByNodeId(String id);

	List<FlowNodeDeal> receiverSelect(FlowNodeDeal flowNodeDeal);
}