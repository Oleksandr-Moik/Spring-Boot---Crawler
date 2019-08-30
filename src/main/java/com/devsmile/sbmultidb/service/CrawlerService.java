package com.devsmile.sbmultidb.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CrawlerService {

    @Value("${url}")
    private String mainUrl;
    private final String ARTICLE_PAGINATION_URL_PATTERN = "%s/tag/%s/page/%d";

    public Map<String, String> getPageLinks(String category, Integer page) throws Exception {
        Map<String, String> linksMap = new HashMap<>();

        String selectElements = (category == null) ? "div[class=tags-block web-view] div[class=block-title]"
                : "a[class^=\"post-link\"]";
        String connectUrl;
        
        if (category == null) {
            connectUrl = mainUrl;
        } else {
            if (page == null) {
                page = 1;
            }
            connectUrl = String.format(ARTICLE_PAGINATION_URL_PATTERN, mainUrl, category, page);
        }

        Document document = Jsoup.connect(connectUrl).get();
        Elements links = document.select(selectElements);

        if (category == null) {
            Elements unnecessaryLinks = links.select("a");
            links.clear();
            unnecessaryLinks.stream()
                .filter(a -> (a.attr("class").isEmpty() && a.attr("href").startsWith("/tag/")))
                .forEach(a -> links.add(a));
        }

        links.stream().forEach(a -> linksMap.put(a.attr("href"), a.text()));

        return linksMap;
    }
}
