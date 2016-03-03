package be.ordina.jworks.gaming.config;

import be.ordina.jworks.gaming.security.SimpleSocialUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
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
        web.ignoring().antMatchers("/index.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //noinspection unchecked
        http
                .authorizeRequests()
                .antMatchers("/**", "/spring/auth/**", "/spring/signin/**")
                .permitAll()
                .antMatchers("/spring/**")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/spring/signin")
                .loginProcessingUrl("/spring/signin/authenticate")
                .failureUrl("/spring/signin?param.error=bad_credentials")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/spring/signout")
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .and()
                .apply((SecurityConfigurerAdapter) new SpringSocialConfigurer());
    }

    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }
}
