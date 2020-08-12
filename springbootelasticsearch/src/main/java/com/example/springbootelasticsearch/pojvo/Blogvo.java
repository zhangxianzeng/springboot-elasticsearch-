package com.example.springbootelasticsearch.pojvo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class Blogvo {
    private String type;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String keyword;

}
