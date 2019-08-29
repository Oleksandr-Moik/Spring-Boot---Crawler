package com.devsmile.sbmultidb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devsmile.sbmultidb.service.WebCrawlerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MainController {

    @Autowired
    private WebCrawlerService webCrawlerService;
    
    @GetMapping(value = "/")
    public Map<String, String> getCategoties() throws Exception {
        return webCrawlerService.getCategoties();
    }
    
    @GetMapping(value = "/tag/{categoty}")
    public Map<String, String> getArticles(
            @PathVariable(value = "categoty")String category) throws Exception{
        webCrawlerService.setCategory(category);
        return webCrawlerService.getArticles(1);
    }
    
    @GetMapping(value = "/tag/{categoty}/{page}")
    public Map<String, String> getArticlesOnPage(
            @PathVariable(value = "categoty")String category,
            @PathVariable(value = "page")int page) throws Exception{
        webCrawlerService.setCategory(category);
        return webCrawlerService.getArticles(page);
    }
}
