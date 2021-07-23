package cn.tedu.knows.search.repository;

import cn.tedu.knows.search.vo.Item;
import cn.tedu.knows.search.vo.Student;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StudentRepository extends ElasticsearchRepository<Student,Long> {
    //根据title查询匹配的Item
    Iterable<Student> queryItemsByTitleMatches(String address);

    //查询多个结果返回值为Iterable 必须根据要求的语法定义方法名
    //参数根据需要的条件项传入

    List<Student> queryItemsByTitleMatchesAndBrandMatches(
            String address);
    //根据address查询根据价格升序排序
    List<Student> queryItemsByAddressMatchesOrderByAgeAsc(
            String address);

//    //分页查询
    Page<Student> queryItemsByAddressMatchesOrderByAgeAsc(
            String address, Pageable pageable);
}
