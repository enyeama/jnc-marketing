package com.sap.jnc.marketing.service.security.portal;

import com.sap.jnc.marketing.persistence.model.User;
import com.sap.jnc.marketing.service.security.IdmUser;
import com.sap.jnc.marketing.service.security.UserId;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PortalUser implements IdmUser {

    private static final long serialVersionUID = 1L;

    private User user;

    private Date lastLoginDate;

    private List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();

    public PortalUser(User user) {
        this.user = user;
    }

    public PortalUser(User user, UserRole userRole) {
        this.user = user;
        this.grantedAuthorityList.add(new SimpleGrantedAuthority(userRole.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        if (this.getUser().getLoginAccount() == null) {
            return "123456";
        }
        return this.getUser().getLoginAccount().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getWechatAccount().getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public PortalUserId getId() {
        return createId(user.getWechatAccount().getOpenId());
    }

    public String getOpenId() {
        return user.getWechatAccount().getOpenId();
    }

    public User getUser() {
        return user;
    }

    @Override
    public Date getRegisterDate() {
        return null;
    }

    public boolean hasRole(final String role) {
        for (GrantedAuthority authority : getAuthorities()) {
            if (authority.getAuthority().equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum UserRole {

        ROLE_USER("ROLE_USER"), ROLE_ANONYMOUS("ROLE_ANONYMOUS");

        private final String role;

        UserRole(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    public static class PortalUserId implements UserId {

        private final String value;

        private PortalUserId(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

    }

    public static PortalUserId createId(String id) {
        return new PortalUserId(id);
    }

}
