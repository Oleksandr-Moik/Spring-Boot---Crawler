
package com.devsmile.sbmultidb.controller;

import java.util.Map;
import java.util.Random;

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

        return crawlerService.getLinks(category, page);
    }
    
    @GetMapping("/sort/{count}")
    public String bubbleSort(@PathVariable Integer count) {
        String result="";
        
        int n=count;
        int mas[] = new int[n];
        result += "Input array: ";
        Random rand=new Random();
        for (int i = 0; i < n; i++)
        {
          mas[i] = rand.nextInt(20);
          result+= mas[i] + " ";
        }
        int k=0;
        for (int i = 0; i < n; i++) {
          for (int j = i+1; j < n; j++) {
            if (mas[i] > mas[j]) {
              k=mas[i];
              mas[i]=mas[j];
              mas[j]=k;
            }
            k++;
          }
        }
//        result+="<br>k"+k;
        result+="<br>Sorted array: ";
        for (int i = 0; i < n; i++)
        {
            result+=mas[i] + " ";
        }
        
        return result;
    }
}
