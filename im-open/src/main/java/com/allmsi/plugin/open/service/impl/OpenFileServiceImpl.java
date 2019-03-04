package com.allmsi.plugin.open.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.plugin.file.service.FileService;
import com.allmsi.plugin.open.service.OpenSelect;
import com.allmsi.sys.util.StrUtil;

@Service("com.allmsi.plugin.open.service.impl.OpenFileServiceImpl")
public class OpenFileServiceImpl implements OpenSelect{
	
	@Resource
	private FileService fileService;

	@Override
	public List<Object> select(String objectId) {
		if(StrUtil.isEmpty(objectId)){
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		list.addAll(fileService.selectByObject(objectId));
		return list;
	}
}
