package com.example.springbootelasticsearch.esservice;

import com.example.springbootelasticsearch.esdao.EsBlogDao;
import com.example.springbootelasticsearch.espojo.ESBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ESBlogServiceImpl implements ESBlogservice {
    @Autowired
    EsBlogDao esBlogDao;
    @Override
    public Iterable<ESBlog> findalles() {
        return esBlogDao.findAll();
    }
}
