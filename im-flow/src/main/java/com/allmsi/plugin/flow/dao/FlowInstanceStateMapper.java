package com.allmsi.plugin.flow.dao;

import com.allmsi.plugin.flow.model.FlowInstanceState;

public interface FlowInstanceStateMapper {

    int insertSelective(FlowInstanceState record);

    FlowInstanceState selectByInstanceId(String instanceId);

    int updateByPrimaryKeySelective(FlowInstanceState record);
}