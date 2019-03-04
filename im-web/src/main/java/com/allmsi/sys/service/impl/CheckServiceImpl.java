package com.allmsi.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.sys.dao.WebUserMapper;
import com.allmsi.sys.model.po.WebUser;
import com.allmsi.sys.service.CheckService;

/**
 * 校验实现类
 * 
 * @author sunnannan
 *
 */
@Service
public class CheckServiceImpl implements CheckService {

	@Resource
	private WebUserMapper loginDao;

	public String checkPhone(String phone) {
		WebUser webUser = new WebUser();
		webUser.setPhone(phone);
		return check(webUser) ? phone : "";
	}

	public String checkEmail(String email) {
		WebUser webUser = new WebUser();
		webUser.setPhone(email);
		return check(webUser) ? email : "";
	}

	private boolean check(WebUser webUser) {
		return (loginDao.checkWebUser(webUser) == 0) ? true : false;
	}
}
