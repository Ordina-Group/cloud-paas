package be.ordina.jworks.gaming.config;

import be.ordina.jworks.gaming.security.SimpleSocialUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.css", "/**/*.png", "/**/*.gif", "/**/*.jpg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/resources/**", "/auth/**", "/signin/**")
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin/authenticate")
                .failureUrl("/signin?param.error=bad_credentials")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/signout")
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .and()
                .apply(new SpringSocialConfigurer());
    }

    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }
}
