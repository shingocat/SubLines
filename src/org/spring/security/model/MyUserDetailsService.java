package org.spring.security.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.strasa.middleware.manager.UserManagerImpl;
import org.strasa.middleware.model.DbUser;
import org.zkoss.zk.ui.select.annotation.VariableResolver;


/**
 * A custom UserDetailsService implementation to adapt to extended User class.<br>
 * 
 * If you have your own user credential model and don't want to change it even a bit, 
 * then instead of swapping the UserDetailService of default AuthenticationProvider, 
 * you have to swap the AuthenticationProvider itself, and you'll have to deal with 
 * password hashing and salting by your own.<br>
 * 
 * 
 * @author Ian YT Tsai (zanyking)
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Service
public class MyUserDetailsService implements UserDetailsService {

	@SuppressWarnings("deprecation")
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDetails user=null;
		UserManagerImpl userManagerImp= new UserManagerImpl();
		DbUser dbUser = userManagerImp.getUserByName(username);
		
		if(!(dbUser==null)){
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
			authList.add(new GrantedAuthorityImpl(dbUser.getRole()));
	
			try {
				user = new User(dbUser.getUsername(), dbUser.getPassword(), enabled, accountNonExpired,
						credentialsNonExpired, accountNonLocked, authList);
				return user;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			
			System.out.println(user);
//			System.out.println(">>> cannot find user: "+username);
			//                        throw new UsernameNotFoundException("cannot found user: "+username);
		}
		return null;

	}



}
