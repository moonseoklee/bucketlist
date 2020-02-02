package com.example.login.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.login.domain.Role;
import com.example.login.domain.RoleRepository;
import com.example.login.domain.Users;
import com.example.login.domain.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        users.setEnabled(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        users.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(users);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users users = userRepository.findByEmail(email);
        if(users != null) {
            List<GrantedAuthority> authorities = getUserAuthority(users.getRoles());
            return buildUserForAuthentication(users, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    private UserDetails buildUserForAuthentication(Users users, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPassword(), authorities);
    }
}
