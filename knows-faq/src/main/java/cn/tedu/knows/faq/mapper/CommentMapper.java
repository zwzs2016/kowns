package cn.tedu.knows.faq.mapper;


import cn.tedu.knows.commons.model.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author tedu.cn
* @since 2021-06-25
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    @Update("update comment set content=#{content} where id=#{id}")
    Integer updateCommentContentById(@Param("content") String content,
                                     @Param("id") Integer id);
}
