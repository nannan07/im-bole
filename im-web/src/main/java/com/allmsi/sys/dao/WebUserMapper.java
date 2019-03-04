package com.allmsi.sys.dao;

import java.util.List;

import com.allmsi.sys.model.po.WebUser;

public interface WebUserMapper {

	WebUser selectByPrimaryKey(String id);

	/**
	 * 查询满足条件的用户id
	 * @param user
	 * @return
	 */
	List<String> checkUniqueId(WebUser user);

	/**
	 * 用户修改邮箱、手机号时的校验
	 * @param webUser
	 * @return
	 */
	int checkWebUser(WebUser webUser);

	/**
	 * 查询满足条件的用户个数
	 * @param user
	 * @return
	 */
	int checkUnique(WebUser user);

	int updateByPrimaryKeySelective(WebUser user);

	int insertSelective(WebUser user);

	List<WebUser> selectRoleByUserId(String id);
}
