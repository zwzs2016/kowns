package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.model.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KnowsPortalApplicationTests {

    @Autowired(required = false)
    TagMapper tagMapper;

    @Autowired(required = false)
    UserMapper uMapper;
    @Test
    void contextLoads() {
        //查询
        QueryWrapper<Classroom> query=new QueryWrapper<>();
//        Tag tag = tagMapper.selectAll();
        query.select();
    }

    @Test
    void test() {
        //查询
        User user=uMapper.findUserByUsername("tc2");
        System.out.println(user);
        List<Permission> ps=uMapper.findUserPermissionById(user.getId());
        for (Permission p:ps) {
            System.out.println(p);
        }
        //新增

    }

}
