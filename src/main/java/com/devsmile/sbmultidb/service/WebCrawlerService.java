package com.devsmile.sbmultidb.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebCrawlerService {

    @Value("${url}")
    private String LINK_AIN;

    private Map<String, String> linksMap = new HashMap<>();
    private Set<String> pageLinksSET = new HashSet<>();
    private String currentTagCategory;

    public Map<String, String> getCategoties() throws Exception {
        Document document = Jsoup.connect(LINK_AIN).get();
        Elements elem = document.select("div[class=tags-block web-view]").select("a[class=look-all]");
        linksMap.clear();
        for (Element el : elem) {
            String url = el.attr("href");
            if (url.contains("tag"))
                if (linksMap.put(getTitle(url), url) != null) {
                    System.out.println("Category: " + getTitle(url));
                    System.out.println("http://localhost:8080" + url);
                }
        }

        return linksMap;
    }

    private String getTitle(String url) throws Exception {
        if (!url.contains("ain.ua"))
            return Jsoup.connect(LINK_AIN + url).get().title();
        else
            return Jsoup.connect(url).get().title();
    }

    public Map<String, String> getArticles(int page) throws Exception {
        // getPagesLinks(LINK_AIN + currentTagCategory);
        linksMap.clear();
        // for(String link:pageLinksSET) {
        String link = LINK_AIN + currentTagCategory + "/page/" + page;
        Document document = Jsoup.connect(link).get();
        Elements articleLinks = document.select("a[class^=\"post-link\"]");
        for (Element article : articleLinks) {
            linksMap.put(article.text(), article.attr("abs:href"));
            System.out.println(article.text().length() + "  " + article.attr("abs:href"));
        }
        // }
        return linksMap;
    }

    public void setCategory(String category) {
        this.currentTagCategory = "/tag/" + category;
    }

    private void getPagesLinks(String url) throws Exception {
        if (!pageLinksSET.contains(url)) {
            Document document = Jsoup.connect(url).get();
            Elements pageLinks = document.select("a[href^=\"" + LINK_AIN + currentTagCategory + "/page/\"]");
            for (Element link : pageLinks) {
                if (pageLinksSET.add(url))
                    System.out.println(url);
                getPagesLinks(link.attr("abs:href"));
            }
        }
    }

}
