package org.hulan.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * 当前登录的operator
 */
public class CurrentOperator implements UserDetails{
	
	Operator operator = null;
	List<GrantedAuthority> grantedAuthorityList;
	
	public CurrentOperator(Operator operator) {
		if(operator == null){
			operator = Operator.EMPTY;
		}
		setOperator(operator);
		this.grantedAuthorityList = AuthorityUtils.createAuthorityList("ROLE"+getOperator().getRole());
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	private void setOperator(Operator operator) {
		this.operator = operator;
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorityList;
	}
	
	@Override
	public String getPassword() {
		return operator.getPassword();
	}
	
	@Override
	public String getUsername() {
		return operator.getUsername();
	}
	
}
