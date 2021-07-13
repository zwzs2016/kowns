package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.Vo.RegisterVo;
import cn.tedu.knows.portal.Vo.UserVo;
import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.UserRoleMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.UserRole;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

    private List<User> teachers=new CopyOnWriteArrayList<>();
    private Map<String,User> teacherMap=new ConcurrentHashMap<>();

    //声明计时器
    private Timer timer=new Timer();

    //初始化代码块
    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (teachers){
                    synchronized (teacherMap){
                        teachers.clear();
                        teacherMap.clear();
                    }
                }
                System.out.println("缓存已清除");
            }
        },30*60*1000,30*60*1000);

    }

    @Resource
    IQuestionService questionService;

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

    @Override
    public List<User> getTeachers() {
        if(teachers.isEmpty()){
            synchronized (teachers){
                if(teachers.isEmpty()){
                    QueryWrapper<User> query=new QueryWrapper<>();
                    query.eq("type",1);
                    //List<User> users=userMapper.selectList(query);
                    teachers=userMapper.selectList(query);
                    for (User user:teachers) {
                        teacherMap.put(user.getNickname(),user);
                    }
                }
            }
        }
        return teachers;
    }

    @Override
    public Map<String, User> getTeachersMap() {
        if(teacherMap.isEmpty()){
            getTeachers();
        }
        return teacherMap;
    }

    @Override
    public UserVo getCurrentUserVo(String username) {
        UserVo userVo=userMapper.findUserVoByUsername(username);
        int questions=questionService.countQuestionsByUserId(userVo.getId());
        userVo.setQuestions(questions);
        //将提问数赋值给userVo对象
        return userVo;
    }


}
