package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.Vo.AnswerVo;
import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.service.IAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/v1/answer")
@Slf4j
public class AnswerController {
    @Autowired
    private IAnswerService answerService;

    //新增一个回答
    @PostMapping
    @PreAuthorize("hsaRole('TEACHER')")
    public Answer addAnswer(@Validated AnswerVo answerVo, BindingResult result, @AuthenticationPrincipal UserDetails user){
        log.debug("收到信息:{}",answerVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Answer answer=answerService.saveAnswer(answerVo,user.getUsername());
        return answer;
    }

    //按问题id查询所有回答的控制层方法

    @GetMapping("/question/{id}")
    public List<Answer> getQuestionAnswers(@PathVariable Integer id){
        if(id==null){
            throw new ServiceException("问题id不能为空!");
        }
        List<Answer> answers=answerService.getAnswerByQuestionId(id);
        return answers;
    }

}
