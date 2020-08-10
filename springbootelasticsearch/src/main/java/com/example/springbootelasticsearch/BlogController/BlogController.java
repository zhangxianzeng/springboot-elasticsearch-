package com.example.springbootelasticsearch.BlogController;

import com.example.springbootelasticsearch.esdao.EsBlogDao;
import com.example.springbootelasticsearch.espojo.ESBlog;
import com.example.springbootelasticsearch.esservice.ESBlogservice;
import com.example.springbootelasticsearch.mysqlpojo.MysqlBlog;
import com.example.springbootelasticsearch.mysqlservice.MysqlService;
import com.example.springbootelasticsearch.pojvo.Blogvo;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    ESBlogservice esBlogservice;
    @Autowired
    MysqlService mysqlService;
@Autowired
    EsBlogDao esBlogDao;
    @PostMapping("/search")
    public Object search(@RequestBody Blogvo blogvo){
        HashMap<String,Object> map=new HashMap<>();
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        String type=blogvo.getType();
        if(type.equalsIgnoreCase("mysql")){
         List<MysqlBlog> mysqlBlogs= mysqlService.queryblog(blogvo.getKeyword());
         map.put("list",mysqlBlogs);
        }else if(type.equalsIgnoreCase("es")){
            BoolQueryBuilder builder= QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",blogvo.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content",blogvo.getKeyword()));
            Page<ESBlog> search= (Page<ESBlog>) esBlogDao.search(builder);
            List<ESBlog> content = search.getContent();
            map.put("list",content);

        }else {
            return "I not";
        }
        stopWatch.stop();
         long totalTimeMillis = stopWatch.getTotalTimeMillis();
         map.put("duration",totalTimeMillis);
        return map;
    }


}
