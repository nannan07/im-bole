package com.allmsi.plugin.open.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.plugin.open.service.OpenService;
import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.util.StrUtil;

@Controller
@RequestMapping("/open")
public class OpenFileController {

	@Resource
	private OpenService openService;

	@RequestMapping(value = "/*", method = RequestMethod.GET)
	@ResponseBody
	public Object openSelect(String token) {
		if(StrUtil.isEmpty(token)){
			return new WarnProtocol();
		}
		return new SuccessProtocol(openService.checkToken(token));
	}

	@RequestMapping(value = "/*", method = RequestMethod.POST)
	@ResponseBody
	public Object openSelect(String token,String code) {
		if(StrUtil.isEmpty(token) || StrUtil.isEmpty(code)){
			return new WarnProtocol();
		}
		return openService.select(token,code);
	}

}
