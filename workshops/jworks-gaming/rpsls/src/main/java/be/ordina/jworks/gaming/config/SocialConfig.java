package be.ordina.jworks.gaming.config;

import be.ordina.jworks.gaming.security.SecurityImplicitConnectionSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource,
                connectionFactoryLocator,
                Encryptors.noOpText());
        repository.setConnectionSignUp(new SecurityImplicitConnectionSignUp(userDetailsManager()));
        return repository;
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setEnableAuthorities(true);
        return manager;
    }
}
