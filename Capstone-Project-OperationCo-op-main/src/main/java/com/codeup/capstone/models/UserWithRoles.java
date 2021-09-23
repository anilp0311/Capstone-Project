package com.codeup.capstone.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserWithRoles extends User implements UserDetails {

    // Copy Constructor - for a User object
    public UserWithRoles(User user) { // takes an input argument of a 'User'
        super(user); // this returns a copy of the superclass, which is 'User'

    }

    // We also want to override the getAuthorities() method from the UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = "";
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
        // no roles will be returned. none.
    }

    @Override
    public String getUsername() {
        return null;
    }

    // Override the UserDetails methods, and have them available here, with our pre-determined values
    @Override
    public boolean isAccountNonExpired() {
        // If we actually wanted to check to see if an account is expired, we could check a DateTime difference between
        // today, and the User.registrationDate (assuming that property exists in our User object)
        // Date now = Date();
        // if ((now - user.registrationDate) > 365) {
        //     return false;
        // }
        return true;
    }
    // If Spring Security wants to check  for account expiration, just say it's not expired, because we haven't
    // written in any functionality to handle and expired account

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // Just say that all passwords can last forever, without ever needing to be changed.
    //  (JUST FOR THIS APP! BECAUSE WE DON'T NEED IT! YOU'LL STILL USE THIS FOR REAL, IN THE REAL WORLD!)

    @Override
    public boolean isEnabled() {
        return true;
    }

}