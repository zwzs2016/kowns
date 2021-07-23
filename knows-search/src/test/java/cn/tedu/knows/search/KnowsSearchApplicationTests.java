package cn.tedu.knows.search;

import cn.tedu.knows.search.repository.StudentRepository;
import cn.tedu.knows.search.vo.Student;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

@SpringBootTest
class KnowsSearchApplicationTests {
    @Resource
    StudentRepository studentRepository;

    @Test
    void contextLoads() {
        Student student=new Student()
                .setId(1l)
                .setName("11")
                .setAddress("ss")
                .setAge(21);
        studentRepository.save(student);
        System.out.println("ok");
    }


    //查询1
    @Test
    void  find1(){
        Iterable<Student> students=studentRepository
                .queryItemsByTitleMatches("武汉");
        for(Student student: students){
            System.out.println(student);
        }
    }

    //查询3:排序
    @Test
    void find3(){
        Iterable<Student> students=studentRepository.queryItemsByAddressMatchesOrderByAgeAsc("杭州");
        students.forEach(item -> System.out.println(item));
    }

    //分页
    @Test
    void page(){
        //Page是包含查询结果和分页信息的对象
        //PageRequest.of是一个方法,
        // 参数是分页查询的页码和每页条数,注意页码从0开始
//        Pageable是PageRequest.of方法的返回值类型
        Page<Student> page=studentRepository
                .queryItemsByAddressMatchesOrderByAgeAsc("北京", PageRequest.of(0,2));
//        for(Student student:page){
//            System.out.println(student);
//        }
//        page.forEach(item -> System.out.println(item))
    }
}
