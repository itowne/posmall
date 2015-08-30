package org.ohuyo.rapid.base.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

public class AuthRoleVoter implements AccessDecisionVoter<FilterInvocation> {
	
	

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public int vote(Authentication authentication, FilterInvocation object,
			Collection<ConfigAttribute> attributes) {
		// TODO Auto-generated method stub

		for (ConfigAttribute configAttribute : attributes) {
			configAttribute.getAttribute();
		}
		return 0;
	}

}
