package com.allmsi.test.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.allmsi.sys.model.protocol.SuccessProtocol;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.util.StrUtil;
import com.allmsi.test.model.MethodVo;
import com.allmsi.test.model.UrlTree;

@Controller
@RequestMapping("/test")
public class TestController {

	@Resource
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	private final List<String> EXCLUDE_LIST = Arrays.asList("/login", "/logout");

	@RequestMapping(value = "/controller/url", method = RequestMethod.GET)
	@ResponseBody
	public Object url(String url) {
		List<UrlTree> urlList = new ArrayList<>();

		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			UrlTree urlTree = new UrlTree();
			RequestMappingInfo info = m.getKey();
			PatternsRequestCondition p = info.getPatternsCondition();
			for (String tempUrl : p.getPatterns()) {
				if ((StrUtil.isEmpty(url) || tempUrl.indexOf(url) > -1) && !EXCLUDE_LIST.contains(tempUrl)) {
					urlTree.setLabel(tempUrl);
					urlList.add(urlTree);
				}
			}
		}
		return new SuccessProtocol(urlList);
	}

	@RequestMapping(value = "/controller/method", method = RequestMethod.GET)
	@ResponseBody
	public Object method(String url) {
		if (StrUtil.isEmpty(url)) {
			return new WarnProtocol();
		}
		MethodVo methodResult = new MethodVo();
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			for (String tempUrl : p.getPatterns()) {
				if (url.equals(tempUrl)) {
					methodResult.setUrl(url);
					break;
				}
			}
			if (StrUtil.notEmpty(methodResult.getUrl())) {
				methodResult.setClassName(method.getMethod().getDeclaringClass().getName()); // 类名
				methodResult.setMethod(method.getMethod().getName()); // 方法名
				Parameter[] parameters = method.getMethod().getParameters();
				List<String> parameterList = new ArrayList<>();
				for (Parameter parameter : parameters) {
					String typeName = parameter.getType().getSimpleName();
					if ("String".equals(typeName) || "Integer".equals(typeName)) {
						parameterList.add(parameter.getName());
					} else {
						Method[] methods = parameter.getType().getMethods();
						for (Method tempMethod : methods) {
							String methodsName = tempMethod.getName();
							if (methodsName.startsWith("set")) {
								for (Parameter tempParameter : tempMethod.getParameters()) {
									parameterList.add(tempParameter.getName());
								}
							}
						}
					}
				}
				methodResult.setParameters(parameterList);
				RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
				String type = methodsCondition.toString();
				if (type != null && type.startsWith("[") && type.endsWith("]")) {
					type = type.substring(1, type.length() - 1);
					methodResult.setType(type); // 方法名
				}
				break;
			}
		}
		return new SuccessProtocol(methodResult);
	}

}
