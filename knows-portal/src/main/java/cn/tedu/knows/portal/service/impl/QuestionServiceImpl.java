package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.Vo.QuestionVo;
import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.QuestionTagMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.mapper.UserQuestionMapper;
import cn.tedu.knows.portal.model.*;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.ITagService;
import cn.tedu.knows.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Autowired
    private QuestionTagMapper questionTagMapper;

    @Autowired
    private UserQuestionMapper userQuestionMapper;

    @Autowired
    public IUserService userService;

    @Override
    public PageInfo<Question> getMyQuestions(String username,Integer pageNum,Integer pageSize) {
        //1.通过用户名查询用户信息
        //2.根据
        User user=userMapper.findUserByUsername(username);
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_id",user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        //查询之前
        PageHelper.startPage(pageNum,pageSize);
        List<Question> list=questionMapper.selectList(query);
        for (Question question:list) {
            List<Tag> tags = tagName2Tags(question.getTagNames());
            question.setTags(tags);
        }
        log.debug("查询当前用户数量:{}",list.size());
        return new PageInfo<>(list);
    }


    @Transactional
    @Override
    public void saveQuestion(QuestionVo questionVo, String username) {
        //根据用户查询用户信息
        User user=userMapper.findUserByUsername(username);
        //根据学生选中的标签构建tag_names列的值
        StringBuilder builder=new StringBuilder();
        //{"java基础","javaSE","面试题"}
        for(String tagName:questionVo.getTagNames()){
            builder.append(tagName).append(",");
        }
        String tagNames = builder.deleteCharAt(builder.length() - 1).toString();
        //实例化Question对象并赋值
        Question question=new Question()
                .setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setCreatetime(LocalDateTime.now())
                .setStatus(0)
                .setPageViews(0)
                .setPublicStatus(0)
                .setDeleteStatus(0)
                .setTagNames(tagNames);
        //执行新增Question
        int num=questionMapper.insert(question);
        if(num!=1){
            throw new ServiceException("服务器忙!");
        }
        //新增Question和tag的关系
        Map<String,Tag> tagMap=tagService.getTagMap();
        for(String tagName:questionVo.getTagNames()){
            Tag t=tagMap.get(tagName);
            QuestionTag questionTag=new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(t.getId());
            num=questionTagMapper.insert(questionTag);
            if(num!=1){
                throw new ServiceException("服务器忙");
            }
            log.debug("新增问题和标签的关系:{}",questionTag);
        }
        //新增User讲师和Question的关系
        Map<String,User> teacherMap=userService.getTeachersMap();
        UserQuestion userQuestion=null;
        for(String nickName:questionVo.getTeacherNickNames()){
            User teacher=teacherMap.get(nickName);
            userQuestion=new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(teacher.getId())
                    .setCreatetime(LocalDateTime.now());

        }
        num=userQuestionMapper.insert(userQuestion);
        if(num!=1){
            throw new ServiceException("服务器忙");
        }
        log.debug("新增了讲师和问题的关系:{}",userQuestion);

    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_id",userId);
        query.eq("delete_status",0);
        Integer count=questionMapper.selectCount(query);
        return count;
    }

    @Override
    public PageInfo<Question> getTeacherQuestions(String username, Integer pageNum, Integer pageSize) {
        User user=userMapper.findUserByUsername(username);
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions=questionMapper.findTeacherQuestions(user.getId());
        for (Question question:questions) {
            List<Tag> tags = tagName2Tags(question.getTagNames());
            question.setTags(tags);
        }
        log.debug("查询当前用户数量:{}",questions.size());
        return new PageInfo<>(questions);
    }

    @Override
    public Question getQuestionById(Integer id) {
        Question question=questionMapper.selectById(id);
        //为question对象tags赋值
        List<Tag> tags=tagName2Tags(question.getTagNames());
        question.setTags(tags);
        return question;
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
