package com.example.demo.Config;

import com.example.demo.Models.AppRole;
import com.example.demo.Models.AppUser;
import com.example.demo.Repositories.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
@Transactional
@Service
public class SSUDS implements UserDetailsService {
    private AppUserRepository appUserRepository;

    public SSUDS(AppUserRepository userRepository) {
        this.appUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser thisUser = appUserRepository.findAppUsersByAppUsername(username);
       // System.out.println(thisUser.getAppUsername() + grantedAuthorities(thisUser) + " aaaaaaaaaaaaaaa");
        if(thisUser == null)
            throw new UsernameNotFoundException("Invalid username or password");


        return new User(thisUser.getAppUsername(),thisUser.getAppPassword(),grantedAuthorities(thisUser));
    }

    public Set <GrantedAuthority> grantedAuthorities(AppUser user)
    {
        Set <GrantedAuthority> userAuthorities = new HashSet<>();
        for(AppRole eachRole: user.getRoles())
        {
            userAuthorities.add(new SimpleGrantedAuthority(eachRole.getRoleName()));
        }
        return userAuthorities;
    }
}