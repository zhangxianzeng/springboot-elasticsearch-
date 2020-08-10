package com.example.springbootelasticsearch.esservice;

import com.example.springbootelasticsearch.espojo.ESBlog;

public interface ESBlogservice {
    Iterable<ESBlog> findalles();
}
