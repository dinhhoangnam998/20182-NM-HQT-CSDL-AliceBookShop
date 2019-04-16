package org.alice.bookshop.webconfig.security;

import java.util.HashSet;
import java.util.Set;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private UserJpa userJpa;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpa.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        if(user.getPrivilege() == 0) {
        	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        if(user.getPrivilege() == 1) {
        	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
