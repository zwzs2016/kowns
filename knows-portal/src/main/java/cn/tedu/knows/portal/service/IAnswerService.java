package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.Vo.AnswerVo;
import cn.tedu.knows.portal.model.Answer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface IAnswerService extends IService<Answer> {
    //新增回答的方法
    Answer saveAnswer(AnswerVo answerVo,String username);

    //按问题id查询回答的方法
    List<Answer> getAnswerByQuestionId(Integer questionId);

}
