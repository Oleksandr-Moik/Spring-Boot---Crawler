package com.devsmile.sbmultidb.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WebCrawlerService {

    @Value("${url}")
    private final String MAIN_URL;
    private final String ARTICLE_PAGINATION_URL_PATTERN = "%s/tag/%s/page/%d";
    
    public Map<String, String> getCategoties() throws Exception {
         Map<String, String> linksMap = new HashMap<>();
        
        Document document = Jsoup.connect(MAIN_URL).get();
        Elements elem = document.select("div[class=tags-block web-view]").select("a[class=look-all]");

        for (Element el : elem) {
            String url = el.attr("href");
            if (url.contains("tag"))
                linksMap.put(getTitle(url), url);
        }
        return linksMap;
    }

    private String getTitle(String url) throws Exception {
        return Jsoup.connect(MAIN_URL + url).get().title(); 
    }

    public Map<String, String> getArticles(String category, Integer page) throws Exception {
        Map<String, String> linksMap = new HashMap<>();
        
        String url = String.format(ARTICLE_PAGINATION_URL_PATTERN, MAIN_URL, category, page);
        Document document = Jsoup.connect(url).get();
        Elements articleLinks = document.select("a[class^=\"post-link\"]");
        
        articleLinks.forEach(a ->linksMap.put(a.text(), a.attr("abs:href")));
        
        return linksMap;
    }
}
