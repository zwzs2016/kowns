package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ITagService tagService;

    @Override
    public List<Question> getMyQuestions(String username) {
        //1.通过用户名查询用户信息
        //2.根据
        User user=userMapper.findUserByUsername(username);
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_id",user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        //查询之前
        PageHelper.startPage(1,8);
        List<Question> list=questionMapper.selectList(query);
        for (Question question:list) {
            List<Tag> tags = tagName2Tags(question.getTagNames());
            question.setTags(tags);
        }
        log.debug("查询当前用户数量:{}",list.size());
        return list;
    }

    //编写一个方法,根据tag_names的值获得一个对应的List<Tag>集合
    private List<Tag> tagName2Tags(String tagName){
        //tagNames: "java基础,javeSE,面试题"
        String[] names=tagName.split(",");
        Map<String,Tag> tagMap=tagService.getTagMap();
        //声明List<Tag> 用于接收标签名称对应的标签对象
        List<Tag> tags=new ArrayList<>();
        //遍历数组,将数组元素对应的标签对象添加到tags中
        for (String name: names){
            tags.add(tagMap.get(name));
        }
        return tags;
    }
}
