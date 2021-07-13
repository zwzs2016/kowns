package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.*;
import cn.tedu.knows.portal.service.IQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class KnowsPortalApplicationTests {

    @Autowired(required = false)
    TagMapper tagMapper;

    @Autowired(required = false)
    UserMapper uMapper;

    @Resource
    IQuestionService questionService;

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
    }
    @Test
    public void count(){
        int count=questionService.countQuestionsByUserId(11);
        System.out.println(count);
    }

    @Autowired
    QuestionMapper questionMapper;

    @Test
    public void te(){
        List<Question> list=questionMapper.findTeacherQuestions(3);
        list.forEach(question -> System.out.println(question));
    }

}
