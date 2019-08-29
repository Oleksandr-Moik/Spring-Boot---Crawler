package com.devsmile.sbmultidb.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devsmile.sbmultidb.service.WebCrawlerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MainController {

//    private Logger log  = LoggerFactory.getLogger(MainController.class);
    
    
    private final WebCrawlerService webCrawlerService;
    
    @GetMapping(value = "/")
    public Map<String, String> getCategoties() throws Exception {
        log.info("Call getCategories");
        
        Map<String, String> result = webCrawlerService.getCategoties();
        
        log.info("Result: {}", result);
        return result;
    }
    
    @GetMapping(value = "/tag/{category}")
    public Map<String, String> getArticlesOnFirstPage(
            @PathVariable String category) throws Exception{
        log.info("Call getArticlesOnFirstPage");

        Map<String, String> result = webCrawlerService.getArticles(category, 1);
        log.info("Result: {}", result);
        return result;
    }
    
    @GetMapping(value = "/tag/{category}/{pageNumber}")
    public Map<String, String> getArticlesOnPageByNum(
            @PathVariable String category,
            @PathVariable Integer pageNumber) throws Exception{
        log.info("Call getArticlesOnPageByNum,category {}, page: {}",category, pageNumber);
        
        Map<String, String> result = webCrawlerService.getArticles(category, pageNumber);
        log.info("Result: {}", result);
        
        return result;
    }
}
