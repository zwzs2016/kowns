package cn.tedu.knows.portal.cn.tedu.knows;

import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.model.Tag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class TagTest {
    @Autowired
    TagMapper tagMapper;

    @Autowired
    ClassroomMapper classroomMapper;

    @Test
    void insert(){
        Tag tag=new Tag();
        tag.setId(0);
        tag.setName("11");
        tag.setCreatetime(LocalDateTime.now());
        tag.setCreateby("123");
        tagMapper.insert(tag);
    }

    @Test
    void delete(){
        tagMapper.deleteById(25);
    }

    @Test
    void selectAll(){
        QueryWrapper<Object> query = new QueryWrapper<>();
        query.select("id","name","createby","createtime");
        List<Tag> tags = tagMapper.selectList(null);
        System.out.println(tags);
    }

}
