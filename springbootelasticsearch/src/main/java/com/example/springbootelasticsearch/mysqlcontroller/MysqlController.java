package com.example.springbootelasticsearch.mysqlcontroller;

import com.example.springbootelasticsearch.mysqlpojo.MysqlBlog;
import com.example.springbootelasticsearch.mysqlservice.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/mysql")
public class MysqlController {
    @Autowired
    MysqlService mysqlService;
    @RequestMapping("/findall")
    @ResponseBody
    public List<MysqlBlog> findall(){
        return mysqlService.findall();
    }

    @GetMapping("/blogs")
    @ResponseBody
    public Object queryAll(){
        return mysqlService.queryAll();
    }
}
