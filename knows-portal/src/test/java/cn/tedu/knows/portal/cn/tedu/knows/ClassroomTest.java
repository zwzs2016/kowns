package cn.tedu.knows.portal.cn.tedu.knows;

import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.model.Classroom;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassroomTest {
    @Autowired
    ClassroomMapper classroomMapper;

    @Test
    void query(){
        //创建一个QueryWrapper对象
        QueryWrapper<Classroom> query=new QueryWrapper<>();
        //QueryWrapper是另外一种设置查询条件的方式
        query.eq("invite_code","JSD1912-876840");
        //运行查询，查询结果最多只能查询一行的结果
        Classroom classroom = classroomMapper.selectOne(query);
        System.out.println(classroom);

        //gt> lt< ge>= le<= ne!=
    }
}
