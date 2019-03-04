package com.allmsi.sys.service;

import java.util.List;

import com.allmsi.sys.model.vo.WebUserVo;

/**
 * 用户信息操作
 * @author sunnannan
 *
 */
public interface WebUserService {

	/**
	 * 用户信息查询
	 * @param id
	 * @return
	 */
	WebUserVo selectByPrimaryKey(String id);

	/**
	 * 添加用户
	 * @param userVo
	 * @return
	 */
	String insert(WebUserVo userVo);

	/**
	 * 用户修改密码
	 * @param token
	 * @param pwd 就密码
	 * @param newPwd 新密码
	 * @return
	 */
	boolean updatePwd(String token, String pwd, String newPwd);

	/**
	 * 用户修改手机号
	 * @param token
	 * @param newPhone
	 * @return
	 */
	boolean updatePhone(String token, String newPhone);

	/**
	 * 用户修改邮箱
	 * @param token
	 * @param newEmail
	 * @return
	 */
	boolean updateEmail(String token, String newEmail);

	/**
	 * 查询用户的角色集合
	 * @param id
	 * @return
	 */
	List<String> selectRoleIdsByUserId(String id);
}
