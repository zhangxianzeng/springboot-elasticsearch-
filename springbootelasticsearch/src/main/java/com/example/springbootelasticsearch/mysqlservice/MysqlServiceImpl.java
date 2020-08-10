package com.example.springbootelasticsearch.mysqlservice;

import com.example.springbootelasticsearch.mysqldao.MysqlDao;
import com.example.springbootelasticsearch.mysqlpojo.MysqlBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MysqlServiceImpl implements MysqlService {
    @Autowired
    MysqlDao mysqlDao;
    @Override
    public List<MysqlBlog> findall() {
        return mysqlDao.findAll();
    }

    @Override
    public List<MysqlBlog> queryAll() {
        return mysqlDao.queryAll();
    }

    @Override
    public List<MysqlBlog> queryblog(String keyword) {
        return mysqlDao.queryblog(keyword);
    }
}
