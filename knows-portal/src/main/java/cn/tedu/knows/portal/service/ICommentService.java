package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.Vo.CommentVo;
import cn.tedu.knows.portal.model.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.awt.*;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface ICommentService extends IService<Comment> {

    //新增评论的方法
    Comment saveComment(CommentVo commentVo, String username);

    boolean removeCommentById(Integer Id,String username);

    //按评论id修改评论内容的方法
    Comment updateComment(Integer commentId,CommentVo commentVo,
                          String username);
}
