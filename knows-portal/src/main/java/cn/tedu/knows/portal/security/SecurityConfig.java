package cn.tedu.knows.portal.security;

import cn.tedu.knows.portal.service.impl.UserDeatilServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDeatilServiceImpl userDeatilService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //参数auth就是管理spring-security进行登录
        //一旦设置代码
        //super.configure(auth);
//        auth.inMemoryAuthentication()
//                .withUser("jerry")
//                .password("{bcrypt}$2a$10$1z2TUa1UDtOpUAUgEd0gd.uGHhvrj3p/UR0oCQJOET.KsEIGyYN/.")
//                .authorities("add");
        //super.configure(auth);
        //这里的配置就是设置登录页面点击登录
        //spring-security自动获得这个方法的返回值。
        auth.userDetailsService(userDeatilService);
    }

    //设置授权范围的方法

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.csrf().disable() //禁用防跨域功能
                    .authorizeRequests() //开始进行授权攻击功能
                    .antMatchers(
                                        "/js/*",
                                        "/css/*",
                                        "/img/**",
                                        "/bower_components/**",
                                        "/login.html",
                                        "/register.html",
                                        "/register"
                                ).permitAll()//
                                .anyRequest()//其他路径
                                .authenticated()//需要登录
                                .and().formLogin()//如果登录使用表单验证
                                .loginPage("/login.html")
                                .loginProcessingUrl("/login")//配置提交登录的路径
                                .failureUrl("/login.html?error")//当登录失败后，跳转的页面
                                .defaultSuccessUrl("/index_student.html")//登录成功时默认显示的页面
                                .and().logout()//开始设置登出李佳
                                    //登出路径
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/logout");
    }
}
