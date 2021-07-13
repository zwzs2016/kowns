package cn.tedu.knows.portal.Vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AnswerVo {
    @NotBlank(message = "问题id不能为空")
    private Integer questionId;
    @NotBlank(message = "回答不能为空")
    private String content;
}
