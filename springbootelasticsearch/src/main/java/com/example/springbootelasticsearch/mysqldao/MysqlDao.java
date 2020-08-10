package com.example.springbootelasticsearch.mysqldao;

import com.example.springbootelasticsearch.mysqlpojo.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MysqlDao extends JpaRepository<MysqlBlog,Integer> {
    @Query("select e from  MysqlBlog e order by e.createTime desc")
    public List<MysqlBlog> queryAll();
    @Query("select e from  MysqlBlog e where e.title like concat('%',:keyword,'%') or e.content like concat('%',:keyword,'%') ")
    public List<MysqlBlog> queryblog(@Param("keyword") String keyword);
}
