package com.example.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "/account/register","/api/**", "/css/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/account/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username,password,enabled " //mydb에 셀렉트 순서
                        + "from user " // 한칸 여백을 줘야함
                        + "where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur inner join user u on ur.user_id=u.id  "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.username = ?");
    }
    
    /* Authentication 로그인
    * Authroization 권한
    * 
    * @ OneToOne  
    * ex ) user-user_detail
    * @ OneToMany
    * ex ) user-board -하나의 사용자가 여러개의 게시글
    * @ ManyToOne
    * ex) board-user -여러개의 게시글은 하나의 사용자에 의해서
    * @ ManyToMany
    * ex) user-role(권한) -하나의 사용자는 여러개의 권한을 가질수 있음. 하나의 권한은 여러개의 사용자에 의해서 설정될 수 있음
    * 
    * */

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //스프링에서 제공하는 암호화
    }
}