package com.allmsi.sys.config;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.allmsi.cache.Cache;
import com.allmsi.cache.CacheFactory;
import com.allmsi.cache.CacheKeyPrefix;
import com.allmsi.sys.model.protocol.WarnProtocol;
import com.allmsi.sys.service.WebResService;
import com.google.gson.Gson;

/**
 * Login connector 拦截非法访问的链接和不存在的用户访问请求
 * 
 * @author sunnannan
 *
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private CacheFactory cacheFactory;

	@Resource
	private WebResService webResService;

	/**
	 * 获取缓存对象
	 * 
	 * @return
	 */
	public Cache getCache() {
		return cacheFactory.getCache();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String result = new Gson().toJson(new WarnProtocol(0));
		String token = getAuthorization(request);
		
		String key = CacheKeyPrefix.TOKEN.getValue() + token;
		if (getCache().isExists(key)) {
			getCache().expire(key, 30 * 60);
			if (isVisit(token, request.getServletPath())) {
				return true;
			} else {
				result = new Gson().toJson(new WarnProtocol(403));
				return true;
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		return false;
	}

	 //得到指定请求消息头的值。如果没有该头，返回null
    private String getAuthorization(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
	/**
	 * 判断resUrl链接请求是否允许
	 * 
	 * @param token
	 * @param resUrl
	 * @return
	 */
	public boolean isVisit(String token, String resUrl) {
		return webResService.isVisit(token, resUrl);
	}
}
