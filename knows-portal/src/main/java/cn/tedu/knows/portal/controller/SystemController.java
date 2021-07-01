package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.Vo.RegisterVo;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SystemController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    //@Validated验证 BindingResult 验证结果和错误信息结果
    public String registerStudent(@Validated RegisterVo registerVo, BindingResult result){
        log.debug("收到学生注册信息:{}",registerVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            return msg;
        }
        try{
            userService.registerStudent(registerVo);
        }catch (SecurityException e){
            //异常信息
            log.error("注册失败",e);
            return e.getMessage();
        }
        return "注册完成!";
    }
}
