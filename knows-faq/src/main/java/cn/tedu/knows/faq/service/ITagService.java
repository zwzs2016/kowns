package cn.tedu.knows.faq.service;

import cn.tedu.knows.commons.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface ITagService extends IService<Tag> {
    List<Tag> getTags();
    //获取所有标签的Map
    Map<String,Tag> getTagMap();
}
