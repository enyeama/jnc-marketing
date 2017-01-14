package com.sap.jnc.marketing.service.security.portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.infrastructure.shared.constant.MessageId;
import com.sap.jnc.marketing.infrastructure.shared.constant.WebAttributes;
import com.sap.jnc.marketing.persistence.config.auditor.ModificatoryAuditorUser;
import com.sap.jnc.marketing.persistence.model.Role;
import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.persistence.model.WechatAccount;
import com.sap.jnc.marketing.persistence.repository.UserRepository;
import com.sap.jnc.marketing.service.security.IdentityService;
import com.sap.jnc.marketing.service.security.IdmUser;
import com.sap.jnc.marketing.service.wechat.WechatUserService;

@Service("identityService")
public class PortalIdentityService implements IdentityService, ApplicationEventPublisherAware {

	private static Logger LOGGER = LoggerFactory.getLogger(PortalIdentityService.class);

	private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private ApplicationEventPublisher publisher;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private WechatUserService userService;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public PortalUser loadUserByOpenId(String openId) {
		if (StringUtils.isBlank(openId)) {
			return null;
		}
		User user = this.userRepository.findOneByOpenId(openId);
		if (user == null) {
			user = new User();
			final WechatAccount wechatAccount = new WechatAccount();
			wechatAccount.setOpenId(openId);
			user.setWechatAccount(wechatAccount);
		}
		return new PortalUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public ModificatoryAuditorUser loadModificatoryAuditorUserByOpenId(String openId) {
		final User user = this.userRepository.findOneByOpenId(openId);
		if ((user == null) || (user.getLoginAccount() == null)) {
			throw new UsernameNotFoundException("Can't find specific user by name");
		}
		final List<Role> roles = user.getRoles();
		if (CollectionUtils.isEmpty(roles)) {
			throw new UsernameNotFoundException("Specific user doesn't have any privileges");
		}
		// Return
		return new ModificatoryAuditorUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// this method should not return anything because we don't use username password to
		// perform authentication.
		final List<User> users = this.userService.findByUsername(username);
		if ((users == null) || users.isEmpty()) {
			throw new UsernameNotFoundException(this.messageSource.getMessage(MessageId.NORMAL_NOT_FOUND, new Object[] {}, null));
		}
		return new PortalUser(users.iterator().next(), PortalUser.UserRole.ROLE_USER);
	}

	@Override
	public void setUserAuthenticationInfo(HttpServletRequest request, IdmUser user) {
		// HttpSession session = request.getSession();
		final Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		((UsernamePasswordAuthenticationToken) authentication).setDetails(this.authenticationDetailsSource.buildDetails(request));
		LOGGER.info("Manually logged in with {}", authentication.getPrincipal());
		final SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
		// session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
		// publish login success event manually
		this.publisher.publishEvent(new AuthenticationSuccessEvent(authentication));
	}

	public String isRedirectNeeded(HttpSession session) {
		return (String) session.getAttribute(WebAttributes.REQUEST_URL_WITH_NO_OPEN_ID);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
