package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.Vo.QuestionVo;
import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/v1/questions")
@Slf4j
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @GetMapping("/my")
    //@AuthenticationPrincipal 能够将当前用户的详情信息赋值给紧随的参数
    public PageInfo<Question> myQuestion(@AuthenticationPrincipal UserDetails user,Integer pageNum){
        if(pageNum==null) pageNum=1;
        Integer pageSize=8;
        PageInfo<Question> questions=questionService.getMyQuestions(user.getUsername(),pageNum,pageSize);
        return questions;
    }

    //编写新增的问题的控制层方法
    @PostMapping
    public String createQuestion(@Validated QuestionVo questionVo, BindingResult result,@AuthenticationPrincipal UserDetails user){
        log.debug("接收到问题内容:{}",questionVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
        }
        //调用业务逻辑层方法
        try {
            questionService.saveQuestion(questionVo,user.getUsername());
            return "问题已发布";
        }catch (ServiceException e){
            log.error("发布失败",e);
            return e.getMessage();
        }
    }

    //查询讲师首页的任务列表的控制器
    @GetMapping("/teacher")
        //指定登录用户必须包含ROLE_TEACHER
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public PageInfo teacher(@AuthenticationPrincipal UserDetails user,Integer pageNum){
        if (pageNum==null) pageNum=1;
        Integer pageSize=8;
        PageInfo<Question> pageInfo=questionService.getTeacherQuestions(user.getUsername(),pageNum,pageSize);
        return pageInfo;
    }

    @GetMapping("/{id}")
    public Question question(@PathVariable Integer id){
        if(id==null)
            throw new ServiceException("ID不能为空!");
        Question question=questionService.getQuestionById(id);
        return question;
    }
}
