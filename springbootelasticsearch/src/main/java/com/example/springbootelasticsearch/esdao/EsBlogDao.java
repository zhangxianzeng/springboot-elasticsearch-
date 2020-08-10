package com.example.springbootelasticsearch.esdao;

import com.example.springbootelasticsearch.espojo.ESBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogDao extends ElasticsearchRepository<ESBlog,Integer> {
}
