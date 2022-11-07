package com.smartTech.RestApi.security;

import com.smartTech.RestApi.Filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor



public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;







@Override

    protected void  configure (AuthenticationManagerBuilder auth )throws Exception{
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }


@Override

    protected  void configure( HttpSecurity http)throws Exception {
 CustomAuthenticationFilter customAuthenticationFilter=new CustomAuthenticationFilter(authenticationManagerBean());
  customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
    http.authorizeRequests().antMatchers("/api/login/**").permitAll();
    http.authorizeRequests().antMatchers("/api/v1/**").permitAll();
    http.authorizeRequests().antMatchers("/api/users/**").permitAll();
    http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("ROLES_USER");
    http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAnyAuthority("ROLES_ADMIN");
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);



}

@Bean
 @Override
 public AuthenticationManager authenticationManagerBean () throws Exception {
    return super.authenticationManagerBean();
}



}
