package com.example.springbootelasticsearch.mysqlservice;

import com.example.springbootelasticsearch.mysqlpojo.MysqlBlog;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface MysqlService {
    List<MysqlBlog> findall();
    public List<MysqlBlog> queryAll();
    public List<MysqlBlog> queryblog(String keyword);
}
