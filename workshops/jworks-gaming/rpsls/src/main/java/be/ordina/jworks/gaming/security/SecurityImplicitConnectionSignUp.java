package be.ordina.jworks.gaming.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import java.util.Collections;

public class SecurityImplicitConnectionSignUp implements ConnectionSignUp {

    public UserDetailsManager userDetailsManager;

    public SecurityImplicitConnectionSignUp(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public String execute(Connection<?> connection) {
        String providerUserId = connection.getKey().getProviderUserId();
        User newUser = new User(providerUserId, "", Collections.singletonList(new SimpleGrantedAuthority("USER")));
        userDetailsManager.createUser(newUser);
        return providerUserId;
    }
}
