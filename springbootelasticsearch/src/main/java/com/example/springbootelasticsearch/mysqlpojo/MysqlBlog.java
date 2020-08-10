package com.example.springbootelasticsearch.mysqlpojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_blog")
@Data
public class MysqlBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private String title;
private String author;
@Column(columnDefinition = "mediumtext")
private  String content;
private Date createTime;
private  Date updateTime;
}
