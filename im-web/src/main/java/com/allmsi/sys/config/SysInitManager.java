package com.allmsi.sys.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.allmsi.sys.service.WebMenuService;
import com.allmsi.sys.service.WebResService;

/**
 * 系统启动时执行的操作
 * @author sunnannan
 *
 */
@Component
public class SysInitManager {

	@Resource
	private WebMenuService webMenuService;
	
	@Resource
	private WebResService webResService;

	/**
	 * 构造方法执行后，初始化，
	 */
	@PostConstruct
	public void init() {
		webMenuService.setMenuCache();
		webResService.setResCache();
	}

}
