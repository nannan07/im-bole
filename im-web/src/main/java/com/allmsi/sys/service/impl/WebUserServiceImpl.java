package com.allmsi.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.cache.CacheKeyPrefix;
import com.allmsi.sys.config.CacheInstence;
import com.allmsi.sys.dao.WebUserMapper;
import com.allmsi.sys.model.po.WebUser;
import com.allmsi.sys.model.vo.WebUserVo;
import com.allmsi.sys.service.WebMenuService;
import com.allmsi.sys.service.WebUserService;
import com.allmsi.sys.util.MD5Util;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class WebUserServiceImpl implements WebUserService {

	@Resource
	private WebUserMapper webUserDao;

	@Resource
	private WebMenuService webMenuService;
	
	@Resource
	private CacheInstence cacheInstence;

	public WebUserVo selectByPrimaryKey(String id) {
		if (StrUtil.isEmpty(id)) {
			return null;
		}
		return new WebUserVo(webUserDao.selectByPrimaryKey(id));
	}

	public List<String> selectRoleIdsByUserId(String id) {
		List<String> ids = new ArrayList<String>();
		List<WebUser> roleList = webUserDao.selectRoleByUserId(id);
		for (WebUser user : roleList) {
			ids.add(user.getRoleId());
		}
		return ids;
	}

	public String insert(WebUserVo userVo) {
		WebUser user = new WebUser(userVo);
		int count = webUserDao.checkUnique(user);
		if (count > 0) {
			return "";
		} else {
			user.setId(UUIDUtil.getUUID());
			user.setPassword(MD5Util.encode(user.getPassword()));
			return (webUserDao.insertSelective(user) == 0) ? "" : user.getId();
		}
	}

	public boolean updatePwd(String token, String pwd, String newPwd) {
		if (StrUtil.isEmpty(pwd) || StrUtil.isEmpty(newPwd)) {
			return false;
		}
		if (cacheInstence.isExists(CacheKeyPrefix.TOKEN.getValue() + token)) {
			String userId = cacheInstence.getUserId(token);
			WebUser user = webUserDao.selectByPrimaryKey(userId);
			if (user != null && user.getPassword().equals(MD5Util.encode(pwd))) {
				WebUser userPo = new WebUser();
				userPo.setId(userId);
				userPo.setPassword(MD5Util.encode(newPwd));
				userPo.setuUserId(userId);
				if (webUserDao.updateByPrimaryKeySelective(userPo) != 0) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean updatePhone(String token, String newPhone) {
		if (StrUtil.isEmpty(newPhone)) {
			return false;
		}
		if (cacheInstence.isExists(CacheKeyPrefix.TOKEN.getValue() + token)) {
			String userId = cacheInstence.getUserId(token);
			WebUser userPo = new WebUser();
			userPo.setId(userId);
			userPo.setPhone(newPhone);
			userPo.setuUserId(userId);
			if (updateByPrimaryKeySelective(userPo)) {
				return true;
			}
		}
		return false;
	}

	public boolean updateEmail(String token, String newEmail) {
		if (StrUtil.isEmpty(newEmail)) {
			return false;
		}
		if (cacheInstence.isExists(CacheKeyPrefix.TOKEN.getValue() + token)) {
			String userId = cacheInstence.getUserId(token);
			WebUser userPo = new WebUser();
			userPo.setId(userId);
			userPo.setEmail(newEmail);
			userPo.setuUserId(userId);
			if (updateByPrimaryKeySelective(userPo)) {
				return true;
			}
		}
		return false;
	}

	private boolean updateByPrimaryKeySelective(WebUser user) {
		List<String> list = webUserDao.checkUniqueId(user);
		int count = list.size();
		if (0 == count || (1 == count && list.get(0).equals(user.getId()))) {
			return (webUserDao.updateByPrimaryKeySelective(user) != 0) ? true : false;
		}
		return false;
	}
}
