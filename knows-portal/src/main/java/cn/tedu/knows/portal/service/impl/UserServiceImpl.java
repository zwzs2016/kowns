package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.Vo.RegisterVo;
import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.UserRoleMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.UserRole;
import cn.tedu.knows.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    //spring 容器中没有保存PasswordEncoder,我们
    private PasswordEncoder encoder=new BCryptPasswordEncoder();


    @Override
    public void registerStudent(RegisterVo registerVo) {
        //1.验证邀请码正确性
        QueryWrapper<Classroom> query=new QueryWrapper<>();
        query.eq("invite_code",registerVo.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(query);
        if(classroom==null){
            throw new ServiceException("邀请码不正确!");
        }
        //2.验证手机号可用
        User user=userMapper.findUserByUsername(registerVo.getPhone());
        if(user!=null){
            throw new ServiceException("手机号已经被注册!");
        }
        //3.实例User对象
        User u=new User();
        String pwd ="{bcrypt}"+ encoder.encode(registerVo.getPassword());
        u.setUsername(registerVo.getPhone())
                .setNickname(registerVo.getNickname())
                .setPassword(pwd)
                .setClassroomId(classroom.getId())
                .setCreatetime(LocalDateTime.now())
                .setEnabled(1)
                .setType(0)
                .setLocked(0);
        //6.执行新增
        int num=userMapper.insert(u);
        if(num!=1){
            throw new ServiceException("服务器忙，请稍后再试...");
        }
        UserRole userRole=new UserRole();
        userRole.setRoleId(2);
        userRole.setUserId(u.getId());
        num=userRoleMapper.insert(userRole);
        //7.新增用户角色用户表
    }
}
