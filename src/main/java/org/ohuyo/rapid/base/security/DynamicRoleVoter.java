package org.ohuyo.rapid.base.security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

public class DynamicRoleVoter implements AccessDecisionVoter<FilterInvocation>,InitializingBean,BeanFactoryAware {

	private PathMatcher pathMatcher = new AntPathMatcher();
	
	private IUrlAuthService authService;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (clazz.isAssignableFrom(FilterInvocation.class)) {
			return true;
		}
		return false;
	}

	@Override
	public int vote(Authentication authentication, FilterInvocation invo,
			Collection<ConfigAttribute> attributes) {
		int result = ACCESS_ABSTAIN;
		if (supports(invo.getClass()) == false)
			return result;
		String url = invo.getRequestUrl();// 当前请求的URL
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if (authService == null){
			logger.warn("系统中还未配置urlAuthService...");
			return result;
		}
		Map<String, Set<String>> urlAuths = authService.getUrlAuthorities();
		Set<String> keySet = urlAuths.keySet();
		for (String key : keySet) {
			boolean matched = pathMatcher.match(key, url);
			if (!matched)
				continue;
			Set<String> mappedAuths = urlAuths.get(key);
			if (contain(authorities, mappedAuths)) {
				result = ACCESS_GRANTED;
				break;
			}else{
				result = ACCESS_DENIED;
				break;
			}
		}
		return result;
	}

	protected boolean contain(Collection<? extends GrantedAuthority> authorities,
			Set<String> mappedAuths) {
		if (CollectionUtils.isEmpty(mappedAuths)
				|| CollectionUtils.isEmpty(authorities))
			return false;
		for (GrantedAuthority item : authorities) {
			if (mappedAuths.contains(item.getAuthority()))
				return true;
		}
		return false;
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.authService == null){
			try{
				this.authService = beanFactory.getBean(IUrlAuthService.class);
			}catch(Exception e){
				logger.warn("查询urlAuthService服务出错:" + e.getMessage());
			}
		}
	}
	
	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
