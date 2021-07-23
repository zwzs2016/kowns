package cn.tedu.knows.search.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Accessors(chain = true)
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Student {
    @Id
    private Long id;

    @Field( type= FieldType.Text,
            analyzer = "ik_smart",
            searchAnalyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Keyword)
    private Integer age;

    @Field( type= FieldType.Text,
            analyzer = "ik_smart",
            searchAnalyzer = "ik_smart")
    private String address;
}
