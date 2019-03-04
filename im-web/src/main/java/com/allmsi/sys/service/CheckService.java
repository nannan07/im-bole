package com.allmsi.sys.service;

/**
 * 校验需要实现方法
 * @author sunnannan
 *
 */
public interface CheckService {

	/**
	 * 检验手机号是否已经注册过
	 * @param phone
	 * @return
	 */
	String checkPhone(String phone);

	/**
	 * 检验邮箱是否已经注册过
	 * @param email
	 * @return
	 */
	String checkEmail(String email);

}
