package com.allmsi.plugin.flow.model.external;

import com.allmsi.plugin.flow.model.FlowInstance;
import com.allmsi.plugin.flow.model.FlowNodeDeal;

public class FlowUserModel {

	private String userId;
	
	private String name;

	private String type;
	
	public FlowUserModel() {
		
	}
	
	public FlowUserModel(FlowInstance flowInstancePo) {
		this.userId = flowInstancePo.getcUserId();
	}
	
	public FlowUserModel(FlowNodeDeal flowNodeDeal2) {
		this.userId = flowNodeDeal2.getNodeDealId();
		this.type = flowNodeDeal2.getNodeDealType();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
