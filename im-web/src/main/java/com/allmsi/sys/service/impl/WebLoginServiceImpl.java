package com.allmsi.sys.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allmsi.cache.CacheKeyPrefix;
import com.allmsi.sys.config.CacheInstence;
import com.allmsi.sys.config.PropertyConfig;
import com.allmsi.sys.dao.WebLoginMapper;
import com.allmsi.sys.model.TreeDataBean;
import com.allmsi.sys.model.po.WebUser;
import com.allmsi.sys.model.vo.WebMenuTree;
import com.allmsi.sys.model.vo.WebMenuVo;
import com.allmsi.sys.model.vo.WebUserCache;
import com.allmsi.sys.service.WebLoginService;
import com.allmsi.sys.util.MD5Util;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.TreeUtil;
import com.allmsi.sys.util.UUIDUtil;

/**
 * 系统登录和退出实现
 * @author sunnannan
 */
@Service
public class WebLoginServiceImpl implements WebLoginService {

	/**
	 * 菜单树根结点id
	 */
	private final static String MENUT_ROOT_ID = "im.menu.root.id";
	
	/**
	 * im-config.properties中的用户登录信息有效时长。
	 */
	private final String IM_SESSION_OUT = "im.session.out";

	/**
	 * 系统默认有效时长，单位秒
	 */
	private final int DEFAULT_SESSION_OUT = 1800;
	
	@Resource
	private CacheInstence cacheInstence;
	
	@Autowired
	private PropertyConfig properties;
	
	@Resource
	private WebLoginMapper loginDao;
	
	public Object login(String loginName,String password) {
		if (StrUtil.notEmpty(loginName, password)) {
			WebUser userPo = new WebUser();
			userPo.setLoginName(loginName);
			userPo.setPassword(MD5Util.encode(password));
			WebUserCache userCache = new WebUserCache(loginDao.login(userPo));
			userCache.setToken(UUIDUtil.getUUID());
			String sessionOut = properties.getProperty(IM_SESSION_OUT);
			int iSessionOut = StrUtil.isNumeric(sessionOut) ? Integer.valueOf(sessionOut) : DEFAULT_SESSION_OUT;
			cacheInstence.setObject(CacheKeyPrefix.TOKEN.getValue() + userCache.getToken(), userCache, iSessionOut); 
			if (userCache == null || StrUtil.isEmpty(userCache.getId())) {
				return null;
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userInfo", userCache);
				map.put("menu", selectMenuTreeByUserId(userCache.getId()));
				return map;
			}
		} else {
			return null;
		}
	}

	public Object logout(String token) {
		return cacheInstence.del(CacheKeyPrefix.TOKEN.getValue() + token);
	}
	
	/**
	 * 用户可以访问的菜单树
	 */
	public TreeDataBean selectMenuTreeByUserId(String userId) {
		List<WebMenuTree> menuTreeList = getRemovalMenuVoList(userId);
		return new TreeDataBean(TreeUtil.getTreeWithOutRootNood(properties.getProperty(MENUT_ROOT_ID), menuTreeList));
	}
	
	/**
	 * 排序后的菜单信息集合
	 * @param userId
	 * @return
	 */
	private List<WebMenuTree> getRemovalMenuVoList(String userId) {
		List<WebMenuTree> menuTreeList = new ArrayList<WebMenuTree>();
		Map<String, WebMenuVo> map = getMenuIdMenuVoMap(userId);
		Collection<WebMenuVo> value = map.values();
		for (WebMenuVo webMenuVo : value) {
			if (map.containsKey(webMenuVo.getpId()) || StrUtil.isEmpty(webMenuVo.getpId())) {
				menuTreeList.add(new WebMenuTree(webMenuVo));
			}
		}
		Collections.sort(menuTreeList, new Comparator<WebMenuTree>() {
			public int compare(WebMenuTree arg0, WebMenuTree arg1) {
				int hits0 = arg0.getSort();
				int hits1 = arg1.getSort();
				if (hits0 < hits1) {
					return -1;
				}
				return 1;
			}
		});
		return menuTreeList;
	}

	/**
	 * 对菜单信息去重
	 * @param webMenuVoList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, WebMenuVo> getMenuIdMenuVoMap(String userId) {
		List<WebMenuVo> webMenuVoList = new ArrayList<WebMenuVo>();
		if (StrUtil.notEmpty(userId)) {
			List<String> roleIds = new ArrayList<String>();
			roleIds.addAll(loginDao.selectRoleIdsByUserId(userId));
			if (roleIds != null && roleIds.size() > 0) {
				for (String roleId : roleIds) {
					webMenuVoList
							.addAll((List<WebMenuVo>) cacheInstence.getObject(CacheKeyPrefix.MENU.getValue() + roleId));
				}
			}
		}
		
		Map<String, WebMenuVo> map = new HashMap<String, WebMenuVo>();
		if (webMenuVoList != null && webMenuVoList.size() > 0) {
			for (WebMenuVo webMenuVo : webMenuVoList) {
				String menuId = webMenuVo.getId();
				if (!map.containsKey(menuId)) {
					map.put(menuId, webMenuVo);
				}
			}
		}
		return map;
	}
}