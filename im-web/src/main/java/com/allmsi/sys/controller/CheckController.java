package com.allmsi.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.service.CheckService;
import com.allmsi.sys.util.StrUtil;

/**
 * 校验信息入口
 * 
 * @author sunnannan
 *
 */
@Controller
@RequestMapping("/check")
public class CheckController extends BaseController{

	@Resource
	private CheckService checkService;

	@RequestMapping(value = "/phone", method = RequestMethod.GET)
	@ResponseBody
	public Object checkPhone(String phone) {
		if (StrUtil.isEmpty(phone)) {
			return new WarnProtocol();
		}
		return (StrUtil.isEmpty(checkService.checkPhone(phone))) ? new SuccessProtocol(false) : new SuccessProtocol(true);
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	@ResponseBody
	public Object checkEmail(String email) {
		if (StrUtil.isEmpty(email)) {
			return new WarnProtocol();
		}
		return (StrUtil.isEmpty(checkService.checkEmail(email))) ? new SuccessProtocol(false) : new SuccessProtocol(true);
	}
}
