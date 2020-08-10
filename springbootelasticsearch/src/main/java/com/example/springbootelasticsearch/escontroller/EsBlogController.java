package com.example.springbootelasticsearch.escontroller;

import com.example.springbootelasticsearch.espojo.ESBlog;
import com.example.springbootelasticsearch.esservice.ESBlogservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/es")
public class EsBlogController {
    @Autowired
    ESBlogservice esBlogservice;
    @RequestMapping("/findall")
    @ResponseBody
    public Iterable<ESBlog> findall(){
        return esBlogservice.findalles();
    }
}
