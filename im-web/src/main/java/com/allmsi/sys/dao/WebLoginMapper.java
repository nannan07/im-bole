package com.allmsi.sys.dao;

import java.util.List;

import com.allmsi.sys.model.po.WebUser;

public interface WebLoginMapper {

	WebUser login(WebUser user);

	List<String> selectRoleIdsByUserId(String userId);
}
