package com.devsmile.sbmultidb.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.devsmile.sbmultidb.service.CrawlerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CrawlerController {

    private final CrawlerService crawlerService;

    @GetMapping({ "/", "/tag/{category}", "/tag/{category}/{page}" })
    public Map<String, String> getPage(@PathVariable(required = false) String category,
            @PathVariable(required = false) Integer page) throws Exception {
        log.info("Call getPage with params: category = {}, page = {}", category, page);

        if (page == null) {
            page = 1;
        }
        
        Map<String, String> result = crawlerService.get(category, page);

        log.info("Result: {}", result);
        return result;
    }
}
