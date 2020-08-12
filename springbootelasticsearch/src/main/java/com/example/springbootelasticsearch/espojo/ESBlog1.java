package com.example.springbootelasticsearch.espojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
//相当于表
@Document(indexName = "blog1",type = "doc",useServerConfiguration = true,createIndex = false)
public class ESBlog1 {
    @Id
    private Integer id;
    //添加需要分词的字段用 @Field(type = FieldType.Text,analyzer = "ik_max_word")
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String author;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private  String content;
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private  Date updateTime;
}
