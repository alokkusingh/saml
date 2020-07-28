package com.alok.security.saml.idp.configuration;

import com.alok.security.saml.idp.model.IDPUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IDPInMemoryUserDetailsManager implements UserDetailsManager {

    private final Map<String, UserDetails> users = new HashMap<>();

    public IDPInMemoryUserDetailsManager(Collection<IDPUserDetails> users) {
        for (UserDetails user : users) {
            createUser(user);
        }
    }

    @Override
    public void createUser(UserDetails userDetails) {
        Assert.isTrue(!userExists(userDetails.getUsername()), "user already  exists");

        users.put(userDetails.getUsername().toLowerCase(), userDetails);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String username) {
        users.remove(username.toLowerCase());
    }

    @Override
    public void changePassword(String oldPwd, String newPwd) {

    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = users.get(username.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }
}
