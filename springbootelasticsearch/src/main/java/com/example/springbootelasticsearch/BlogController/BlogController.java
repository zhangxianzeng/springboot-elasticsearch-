package com.example.springbootelasticsearch.BlogController;

import com.example.springbootelasticsearch.esdao.EsBlogDao;
import com.example.springbootelasticsearch.espojo.ESBlog;
import com.example.springbootelasticsearch.espojo.ESBlog1;
import com.example.springbootelasticsearch.esservice.ESBlogservice;
import com.example.springbootelasticsearch.mysqlpojo.MysqlBlog;
import com.example.springbootelasticsearch.mysqlservice.MysqlService;
import com.example.springbootelasticsearch.pojvo.Blogvo;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    ESBlogservice esBlogservice;
    @Autowired
    MysqlService mysqlService;
@Autowired
    EsBlogDao esBlogDao;
    @Autowired
    ElasticsearchTemplate template;

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
            //matchQuery先将搜索词进行分词然后再进行查询只要里面包含任意一个词都可以被查出
            builder.should(QueryBuilders.matchQuery("title",blogvo.getKeyword()));
            builder.should(QueryBuilders.matchQuery("content",blogvo.getKeyword()));

            //matchPhraseQuery搜索词不进行分词，精准匹配搜索词，如何不一致则无法进行匹配
//            builder.should(QueryBuilders.matchPhraseQuery("title",blogvo.getKeyword()));
//            builder.should(QueryBuilders.matchPhraseQuery("content",blogvo.getKeyword()));

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


    /**
     * 获取相关搜索、最多返回9条
     * @param key
     * @return
     */
    @PostMapping("/searchsuggest")
    public Object getSearchSuggest(String key) {
        CompletionSuggestionBuilder suggestion = SuggestBuilders
                .completionSuggestion("title.suggest").prefix(key).size(20).skipDuplicates(true);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("doc", suggestion);
        //ESBlog1.class用于确定是哪一个index
        SearchResponse response = template.suggest(suggestBuilder, ESBlog1.class);
        Suggest suggest = response.getSuggest();

        Set<String> keywords = null;
        if (suggest != null) {
            keywords = new HashSet<>();
            List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries
                    = suggest.getSuggestion("doc").getEntries();

            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry : entries) {
                for (Suggest.Suggestion.Entry.Option option: entry.getOptions()) {
                    /** 最多返回9个推荐, 每个长度最大为20 */
                    String keyword = option.getText().string();
                    if (!StringUtils.isEmpty(keyword) && keyword.length() <= 20) {
                        /** 去除输入字段 */
                       // if (keyword.equals(key)) continue;
                        keywords.add(keyword);
                        if (keywords.size() >= 9) {
                            break;
                        }
                    }
                }
            }
        }
        return keywords;
    }
//@PostMapping("/searchsuggest1")
//    public String[] getSuggestion(String text){
//        //构造搜索建议语句
//        SuggestionBuilder completionSuggestionFuzzyBuilder = SuggestBuilders.completionSuggestion("title.suggest").prefix(text, Fuzziness.AUTO);
//
//        //根据
//        final SearchResponse suggestResponse = template.suggest(new SuggestBuilder().addSuggestion("doc",completionSuggestionFuzzyBuilder), ESBlog.class);
//        CompletionSuggestion completionSuggestion = suggestResponse.getSuggest().getSuggestion("doc");
//        List<CompletionSuggestion.Entry.Option> options = completionSuggestion.getEntries().get(0).getOptions();
////        System.err.println(options);
////        System.out.println(options.size());
////        System.out.println(options.get(0).getText().string());
//
//        List<String> suggestList = new ArrayList<>();
//        options.forEach(item ->{ suggestList.add(item.getText().toString()); });
//
//        return suggestList.toArray(new String[suggestList.size()]);
//    }

}
