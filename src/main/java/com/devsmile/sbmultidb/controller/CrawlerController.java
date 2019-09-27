
package com.devsmile.sbmultidb.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsmile.sbmultidb.service.CrawlerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CrawlerController {

    private final CrawlerService crawlerService;

    @GetMapping
    public Map<String, String> getPage() throws Exception {
        return crawlerService.getCategorys();
    }
    
    @GetMapping("/students")
    public Map<String, String> getFromStudents()throws Exception{
        return crawlerService.getNews();
    }
    
    
}
