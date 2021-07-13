package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.Vo.RegisterVo;
import cn.tedu.knows.portal.Vo.UserVo;
import cn.tedu.knows.portal.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface IUserService extends IService<User> {
    void registerStudent(RegisterVo registerVo);

    //查询所有讲师的方法
    List<User> getTeachers();

    //查询所有讲师的Map集合
    Map<String,User> getTeachersMap();

    //查询用户面板数据
    UserVo getCurrentUserVo(String username);
}
