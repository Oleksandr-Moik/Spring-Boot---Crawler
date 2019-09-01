package com.devsmile.sbmultidb.service;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlerService {

    @Value("${url}")
    private String mainUrl;
    private final String ARTICLE_PAGINATION_URL_PATTERN = "%s/tag/%s/page/%d";

    public Map<String, String> getLinks(String category, Integer page) throws Exception {
        if (category == null) {
            return getLinksOnPage(mainUrl, "div[class=tags-block web-view] div[class=block-title]");
        } else {
            return getLinksOnPage(String.format(ARTICLE_PAGINATION_URL_PATTERN, mainUrl, category, page),
                                "a[class^=\"post-link\"]");
        }
    }

    private Map<String, String> getLinksOnPage(String url, String selectQuery) throws Exception {
        Map<String, String> linksMap = new HashMap<>();

        Document document = Jsoup.connect(url).get();
        Elements links = document.select(selectQuery);

        if (selectQuery.startsWith("div")) {
            links = clearUnnecessaryLinks(links);
        }
        
        links.stream().forEach(a -> linksMap.put(a.attr("href"), a.text()));

        log.info("Result linksMap: {}",linksMap);
        return linksMap;
    }

    private static Elements clearUnnecessaryLinks(Elements links) {
        Elements result = new Elements();
        links.select("a")
            .stream()
            .filter(a -> (a.attr("class").isEmpty() && a.attr("href").startsWith("/tag/")))
            .forEach(a -> result.add(a));
        return result;
    }

}
