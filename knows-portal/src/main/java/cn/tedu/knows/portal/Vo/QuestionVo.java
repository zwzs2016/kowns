package cn.tedu.knows.portal.Vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Accessors(chain =true)
public class QuestionVo {
    @NotBlank(message = "标题不能为空")
    @Pattern(regexp = "^.{3,50}",message="标题要求3到50个字符")
    private String title;

    @NotEmpty(message = "至少选择一个标签")
    private String[] tagNames={};//保证不是空

    @NotEmpty(message = "至少选择一个讲师")
    private String[] teacherNickNames={};

    @NotBlank(message = "问题不能为空")
    private String content;
}
