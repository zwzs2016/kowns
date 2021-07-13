package cn.tedu.knows.sys.Vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;
    @NotBlank(message = "手机号不能为空")
    //@Pattern 表示下面属性要符合指定正则表达式
    //regexp执行正则表达式,message当属性行不符合正则表达式
    @Pattern(regexp = "^1\\d{10}$",message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^.{2,20}$",message = "昵称2到20个字符")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\w{6,20}$",message = "密码是6~20位数字字母'_'")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirm;
}
