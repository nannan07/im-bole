package com.allmsi.plugin.open.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allmsi.plugin.open.config.SpringContextOpen;
import com.allmsi.plugin.open.dao.OpenMapper;
import com.allmsi.plugin.open.model.OpenPo;
import com.allmsi.plugin.open.model.OpenVo;
import com.allmsi.plugin.open.service.OpenSelect;
import com.allmsi.plugin.open.service.OpenService;
import com.allmsi.sys.config.PropertyConfig;
import com.allmsi.sys.model.protocol.ErrorProtocol;
import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.util.RandomUtil;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.sys.util.UUIDUtil;

@Service
public class OpenServiceImpl implements OpenService {

	@Resource
	private OpenMapper openDao;

	@Autowired
	private SpringContextOpen springContextOpen;

	@Autowired
	private PropertyConfig properties;

	private final String EXPIRY_DATE = "expiry_date";

	private final String OPEN_PATH = "open_path";

	private final String TCOUNT = "tCount";

	@Override
	public boolean checkToken(String token) {
		if (StrUtil.isEmpty(token)) {
			return false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expiry_date", Integer.valueOf(properties.getProperty(EXPIRY_DATE)));
		map.put("token", token);
		OpenPo openBo = new OpenPo();
		openBo = openDao.select(map);
		return (!(openBo == null || StrUtil.isEmpty(openBo.getId())));
	}

	@Override
	public Object select(String token, String code) {
		if (StrUtil.isEmpty(token) || StrUtil.isEmpty(code)) {
			return new ErrorProtocol();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("expiry_date", Integer.valueOf(properties.getProperty(EXPIRY_DATE)));
		map.put("token", token);
		map.put("code", code);
		OpenPo openBo = new OpenPo();
		openBo = openDao.select(map);
		if (openBo == null || StrUtil.isEmpty(openBo.getId())) {
			return new ErrorProtocol();
		}
		String className = properties.getProperty(openBo.getType());
		if (StrUtil.isEmpty(className)) {
			return new ErrorProtocol();
		}
		openDao.updateByPrimaryKey(openBo.getId());
		OpenSelect openSelect = springContextOpen.getServiceImpl(OpenSelect.class, className);
		return new SuccessProtocol(openSelect.select(openBo.getObjectId()));
	}

	@Override
	public Object insert(String objectId, List<String> receiveIds, String cUser, String type) {
		if (StrUtil.isEmpty(objectId) || StrUtil.isEmpty(cUser) || StrUtil.isEmpty(type) || receiveIds == null
				|| receiveIds.size() <= 0) {
			return new ErrorProtocol();
		}
		return insert(objectId, receiveIds, cUser, type, 0);
	}

	@Override
	public Object insert(String objectId, List<String> receiveIds, String cUser, String type, int tCount) {
		if (StrUtil.isEmpty(objectId) || StrUtil.isEmpty(cUser) || StrUtil.isEmpty(type) || receiveIds == null
				|| receiveIds.size() <= 0) {
			return new ErrorProtocol();
		}
		List<OpenVo> volist = new ArrayList<OpenVo>();
		volist.addAll(insertOpen(objectId, receiveIds, cUser, type, tCount));
		return new SuccessProtocol(volist);
	}

	private List<OpenVo> insertOpen(String objectId, List<String> receiveIds, String cUser, String type, int tCount) {
		List<OpenPo> list = new ArrayList<OpenPo>();
		List<OpenVo> volist = new ArrayList<OpenVo>();
		for (String receiveid : receiveIds) {
			OpenPo bo = new OpenPo();
			String id = UUIDUtil.getUUID();
			String code = RandomUtil.random(100000, 1000000) + " ";
			bo.setId(id);
			bo.setObjectId(objectId);
			bo.setReceiveId(receiveid);
			bo.setToken(id);
			bo.setCode(code);
			bo.setcUserId(cUser);
			bo.setType(type);
			if (0 == tCount) {
				bo.settCount(Integer.valueOf(properties.getProperty(TCOUNT)));
			} else {
				bo.settCount(tCount);
			}
			list.add(bo);
			String url = properties.getProperty(OPEN_PATH) + type + "?token=" + id;
			OpenVo fileOpen = new OpenVo();
			fileOpen.setCode(code);
			fileOpen.setUrl(url);
			volist.add(fileOpen);
		}
		int msg = openDao.insertBatch(list);
		return (msg != 0) ? volist : null;
	}
}
