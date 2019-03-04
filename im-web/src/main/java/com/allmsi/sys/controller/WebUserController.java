package com.allmsi.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.service.WebUserService;
import com.allmsi.sys.util.StrUtil;

/**
 * 用户查询个人信息以及修改密码、手机号和邮箱的入口
 * 
 * @author sunnannan
 *
 */
@Controller
@RequestMapping("/user")
public class WebUserController extends BaseController {

	@Resource
	private WebUserService webUserService;

	@RequestMapping(value = "/info/select", method = RequestMethod.GET)
	@ResponseBody
	public Object selectUser(String id) {
		if (StrUtil.isEmpty(id)) {
			return new WarnProtocol();
		}
		return new SuccessProtocol(webUserService.selectByPrimaryKey(id));
	}

	@RequestMapping(value = "/pwd/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePwd(@RequestHeader(AUTHORIZATION) String token, String pwd, String newPwd) {
		if (StrUtil.isEmpty(pwd) || StrUtil.isEmpty(newPwd)) {
			return new WarnProtocol();
		}
		return new SuccessProtocol(webUserService.updatePwd(token, pwd, newPwd));
	}

	@RequestMapping(value = "/phone/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePhone(@RequestHeader(AUTHORIZATION) String token, String newPhone) {
		return new SuccessProtocol(webUserService.updatePhone(token, newPhone));
	}

	@RequestMapping(value = "/email/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateEmail(@RequestHeader(AUTHORIZATION) String token, String newEmail) {
		return new SuccessProtocol(webUserService.updateEmail(token, newEmail));
	}
}
