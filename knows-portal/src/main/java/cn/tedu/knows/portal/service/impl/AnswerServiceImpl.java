package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.Vo.AnswerVo;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IAnswerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    @Transactional
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user=userMapper.findUserByUsername(username);
        Answer answer=new Answer()
                .setContent(answerVo.getContent())
                .setQuestId(answerVo.getQuestionId())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setLikeCount(0)
                .setAcceptStatus(0)
                .setCreatetime(LocalDateTime.now());
        int num=answerMapper.insert(answer);
        if(num!=1){
            throw new SecurityException("服务器忙!");
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswerByQuestionId(Integer questionId) {
        List<Answer> answers=answerMapper
                .findAnswersWithCommentsByQuestionId(questionId);
        //千万忘了返回!!!
        return answers;
    }

}
