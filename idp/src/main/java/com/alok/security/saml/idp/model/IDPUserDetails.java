package com.alok.security.saml.idp.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.saml.saml2.attribute.Attribute;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IDPUserDetails implements UserDetails {

    private List<Attribute> samlAttributesToSendToSP;
    private String username;
    private String role;
    private List<SimpleGrantedAuthority> authorities;

    public IDPUserDetails(String username, String role, List<Attribute> attributes) {
        this.username = username;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        this.samlAttributesToSendToSP = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {

        // Prior to Spring Security 5.0 the default PasswordEncoder was NoOpPasswordEncoder
        // which required plain text passwords. In Spring Security 5, the default is
        // DelegatingPasswordEncoder, which required Password Storage Format.
        // Solution 1 – Add password storage format, for plain text, add {noop}

        return "{noop}password"; // no password encoder
       // return "password";
    }

    @Override
    public String getUsername() {
        return username;
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

    public List<Attribute> getSamlAttributesToSendToSP() {

        return samlAttributesToSendToSP;
    }
}
