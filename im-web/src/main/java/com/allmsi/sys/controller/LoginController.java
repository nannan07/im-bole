package com.allmsi.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.vo.WebLoginVo;
import com.allmsi.sys.service.WebLoginService;
import com.allmsi.sys.util.StrUtil;

/**
 * 用户登录和退出入口
 * 
 * @author sunnannan
 *
 */
@Controller
@RequestMapping()
public class LoginController extends BaseController {

	@Resource
	private WebLoginService webLoginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(WebLoginVo loginVo) {
		if (StrUtil.notEmpty(loginVo.getLoginName(), loginVo.getPassword())) {
			Object object = webLoginService.login(loginVo.getLoginName(), loginVo.getPassword());
			if (object == null) {
				return new SuccessProtocol("error");
			}
			return new SuccessProtocol(object);
		} else {
			return new SuccessProtocol("error");
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(@RequestHeader(AUTHORIZATION) String token) {
		return new SuccessProtocol(webLoginService.logout(token));
	}
}
