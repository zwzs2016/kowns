package cn.tedu.knows.faq.mapper;

import cn.tedu.knows.commons.model.Permission;
import cn.tedu.knows.commons.model.Role;
import cn.tedu.knows.commons.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author tedu.cn
* @since 2021-06-25
*/
    @Repository
    public interface UserMapper extends BaseMapper<User> {
        //根据用户名获得用户信息
        @Select("select * from user where username=#{username}")
        User findUserByUsername(String username);

        //根据用户id查询用户权限
        @Select("SELECT p.id,p.`name`from user u left JOIN user_role ur on u.id=ur.user_id left JOIN role r on r.id=ur.role_id left JOIN role_permission rp on r.id=rp.role_id LEFt JOIN permission p on p.id=rp.permission_id WHERE u.id=#{id}")
        List<Permission> findUserPermissionById(Integer id);

//        @Select("select id,username,nickname from user where username=#{username}")
//        UserVo findUserVoByUsername(String username);

        @Select("SELECT r.id,r.name" +
                " from user u left JOIN user_role ur on u.id=ur.user_id" +
                " left JOIN role r on r.id=ur.role_id" +
                " where u.id=#{id}")
        List<Role> findUserRoleById(Integer id);
    }
