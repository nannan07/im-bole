package com.allmsi.sys.config;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * PropertyConfig 读取配置文件的配置项对应的value值
 * @author sunnannan
 *
 */
public class PropertyConfig extends PropertyPlaceholderConfigurer {

	// 存取properties配置文件key-value结果
	private Properties props;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		this.props = props;
	}

	public String getProperty(String key) {
		return this.props.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return this.props.getProperty(key, defaultValue);
	}

	public Object setProperty(String key, String value) {
		return this.props.setProperty(key, value);
	}

	public List<String> getPropertyKeys(String keys) {
		List<String> keyList = new ArrayList<String>();
		Enumeration<?> en = props.propertyNames();
		while (en.hasMoreElements()) {
			String key = en.nextElement().toString();// key值
			if (key.startsWith(keys)) {
				keyList.add(key);
			}
		}
		return keyList;
	}
}
