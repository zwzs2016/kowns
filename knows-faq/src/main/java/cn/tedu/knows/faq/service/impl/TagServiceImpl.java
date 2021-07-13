package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.mapper.TagMapper;
import cn.tedu.knows.faq.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    //声明
    //CopyOnwriteArrayList是一个线程安全的集合
    private List<Tag> tags = new CopyOnWriteArrayList<>();

    private Map<String ,Tag> tagMap=new ConcurrentHashMap<>();
    @Override
    public List<Tag> getTags() {
        //调用父类提供的全查方法
        //List<Tag> tags=this.list();
        // 1 2 3
        if (tags.isEmpty()){
            synchronized (tags){
                if(tags.isEmpty()){
                    tags.addAll(list());
                }
                for (Tag t:tags){
                    tagMap.put(t.getName(),t);
                    //以标签名称为key 标签对象为value保存在tagMap中
                }
            }
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getTagMap() {
        if(tagMap.isEmpty()){
            getTags();
        }
        return tagMap;
    }
}
