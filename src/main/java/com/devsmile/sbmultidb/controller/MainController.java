package com.devsmile.sbmultidb.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devsmile.sbmultidb.crawler.WebCrawler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MainController {

    private WebCrawler webCrawler;
    
    @GetMapping(value = "/")
    public Map<String, String> getCategoties() throws Exception {
        return webCrawler.getCategoties();
    }
    
    @GetMapping(value = "/tag/{categoty}")
    public Map<String, String> getArticles(
            @PathVariable(value = "categoty")String category){
        webCrawler.setCategory(category);
        return webCrawler.getArticles();
    }
}
