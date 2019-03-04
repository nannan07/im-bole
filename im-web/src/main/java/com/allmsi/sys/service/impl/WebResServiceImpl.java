package com.allmsi.sys.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.cache.CacheKeyPrefix;
import com.allmsi.sys.config.CacheInstence;
import com.allmsi.sys.dao.WebResourcesMapper;
import com.allmsi.sys.model.po.WebRes;
import com.allmsi.sys.service.WebResService;
import com.allmsi.sys.service.WebUserService;

@Service
public class WebResServiceImpl implements WebResService {

	@Resource
	private WebResourcesMapper webResDao;

	@Resource
	private WebUserService webUserService;

	@Resource
	private CacheInstence cacheInstence;

	public void setResCache() {
		Map<String, List<String>> map = getRoleResMapForCache();
		for (Entry<String, List<String>> entry : map.entrySet()) {
			List<String> valueList = entry.getValue();
			if (valueList != null && valueList.size() > 0) {
				String key = entry.getKey();
				Set<String> value = new HashSet<String>();
				value.addAll(valueList);
				cacheInstence.setObject(CacheKeyPrefix.RES.getValue() + key, value);
			}
		}
	}

	private Map<String, List<String>> getRoleResMapForCache() {
		List<WebRes> resList = new ArrayList<WebRes>();
		resList.addAll(webResDao.selectResListForCacheAdd());
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (WebRes webRes : resList) {
			String roleId = webRes.getAuthDealId();
			if (!map.containsKey(roleId)) {
				List<String> list = new ArrayList<String>();
				list.add(webRes.getResUrl());
				map.put(roleId, list);
			} else {
				List<String> list = new ArrayList<String>();
				list.addAll(map.get(roleId));
				list.add(webRes.getResUrl());
				map.put(roleId, list);
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public boolean isVisit(String token, String resUrl) {
		String userId = cacheInstence.getUserId(token);
		Set<String> values = new HashSet<String>();
		List<String> roleIds = new ArrayList<String>();
		roleIds.addAll(webUserService.selectRoleIdsByUserId(userId));
		if (roleIds != null && roleIds.size() > 0) {
			for (String roleId : roleIds) {
				if (cacheInstence.getObject((CacheKeyPrefix.RES.getValue() + roleId)) != null) {
					values.addAll((Collection<? extends String>) cacheInstence
							.getObject(CacheKeyPrefix.RES.getValue() + roleId));
				}
			}
		}
		return (values.contains(resUrl)) ? true : false;
	}
}
