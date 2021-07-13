package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.Role;
import cn.tedu.knows.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserDeatilServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.findUserByUsername(username);
        if(user==null){
            //用户不存在
            return null;
        }
        List<Permission> permissions=userMapper.findUserPermissionById(user.getId());
        String[] auth=new String[permissions.size()];
        int i=0;
        for(Permission p:permissions){
            auth[i++]=p.getName();
        }
        //根据iD
        List<Role> roles=userMapper.findUserRoleById(user.getId());
        auth= Arrays.copyOf(auth,auth.length+roles.size());
        for(Role r : roles){
            auth[i++]=r.getName();
        }

        UserDetails u= org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(auth)
                .accountLocked(user.getLocked()==1)
                .disabled(user.getEnabled()==0)
                .build();
        return u;
    }
    //UserDateils是spring-security提供的接口


}
