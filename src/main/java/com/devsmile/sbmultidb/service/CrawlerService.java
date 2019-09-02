package com.devsmile.sbmultidb.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            page = Optional.ofNullable(page).orElse(1).intValue();
            return getLinksOnPage(String.format(ARTICLE_PAGINATION_URL_PATTERN, mainUrl, category, page),
                                  "a[class^=\"post-link\"]");
        }
    }

    private Map<String, String> getLinksOnPage(String url, String selectPattern) throws Exception {
        Document document = Jsoup.connect(url).get();
        Elements links = document.select(selectPattern).select("a");

        Map<String, String> linksMap = links.stream()
            .filter(a -> (a.attr("class").isEmpty() && a.attr("href").startsWith("/tag/"))
                    || a.className().equals("post-link with-labels"))
            .collect(Collectors.toMap(e -> e.attr("href"), e -> e.text()));

        log.info("Result linksMap: {}", linksMap);
        return linksMap;
    }
}
