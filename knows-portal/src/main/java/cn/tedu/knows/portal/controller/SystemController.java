package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.Vo.RegisterVo;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController {
    @Autowired
    private IUserService userService;

    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;

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

    //文件上传的方法
    @PostMapping("/upload/file")
    public String uploadFile(MultipartFile imageFile){
        //根据日期获得一个日期路径
        //2021/07/02
        String path= DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                .format(LocalDate.now());
        File folder=new File(resourcePath,path);
        folder.mkdirs();
        //开始创建文件名
        String fileName=imageFile.getOriginalFilename();
        //根据原始文件名，截取出文件的扩展名
        //abc.png
        //0123456
        String ext=fileName.substring(fileName.lastIndexOf("."));
        //在生成一个随机的文件名
        String name= UUID.randomUUID().toString()+ext;
        //将文件的位置在上面准备的文件夹中
        File file=new File(folder,name);
        //将文件保存到上面file指定的位置
        try {
            imageFile.transferTo(file);
            log.debug("保存的实际路径为:{}",file.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url=resourceHost+"/"+path+"/"+name;
        return url;
    }
}
