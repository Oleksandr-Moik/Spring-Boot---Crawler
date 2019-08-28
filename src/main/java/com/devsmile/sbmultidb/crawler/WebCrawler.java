package com.devsmile.sbmultidb.crawler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class WebCrawler {

    private static final String LINK_AIN = "https://ain.ua";

    private Map<String, String> categoriesMAP;
    private Map<String, String> articlesMAP;
    private HashSet<String> pageLinksSET;
    private String currentTagCategory;

    public WebCrawler() {
        categoriesMAP = new HashMap<String, String>();
        articlesMAP = new HashMap<String, String>();
        pageLinksSET = new HashSet<String>();
    }

    public Map<String, String> getCategoties() {
        try {
            Document document = Jsoup.connect(LINK_AIN).get();
            Elements elem = document.select("div[class=tags-block web-view]").select("a[class=look-all]");
            // Elements elem = document.select("div[class=tags-block web-view]
            // a[class=look-all]");
            for (Element el : elem) {
                String url = el.attr("href");
                if (categoriesMAP.put(getTitle(url),url) != null)
                    System.out.println("Category: " + getTitle(url) + " || " + url);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return categoriesMAP;
    }

    private String getTitle(String url) {
        try {
            if (!url.contains("ain.ua"))
                return Jsoup.connect(LINK_AIN + url).get().title();
            else
                return Jsoup.connect(url).get().title();
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, String> getArticles() {
        getPagesLinks(LINK_AIN + currentTagCategory);
        pageLinksSET.forEach(link -> {
            try {
                Document document = Jsoup.connect(link).get();
                Elements articleLinks = document.select("a[class^=\"post-link\"]");
                for (Element article : articleLinks) {
                    articlesMAP.put(article.text(), article.attr("abs:href"));
                    System.out.println(article.text().length() + "  " + article.attr("abs:href"));
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        });

        return articlesMAP;
    }

    public void setCategory(String category) {
        this.currentTagCategory = "/tag/" + category;
    }

    private void getPagesLinks(String url) {
        if (!pageLinksSET.contains(url)) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements pageLinks = document.select("a[href^=\"" + LINK_AIN + currentTagCategory + "/page/\"]");
                for (Element link : pageLinks) {
                    if (pageLinksSET.add(url)) {
                        System.out.println(url);
                    }
                    getPagesLinks(link.attr("abs:href"));
                }
            } catch (Exception e) {
                System.err.println("Bad url? for " + url + ": category not found " + e.getMessage());
            }
        }
    }

}
