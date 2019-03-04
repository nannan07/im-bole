package com.allmsi.plugin.flow.dao;

import java.util.List;
import java.util.Map;

import com.allmsi.plugin.flow.model.FlowInstanceLog;

public interface FlowInstanceLogMapper {
    int insertSelective(FlowInstanceLog record);

	int selectCountInstanceLog(String instanceId);

	List<FlowInstanceLog> selectByInstanceId(Map<String, Object> map);
}