package com.devsmile.sbmultidb.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    // зробити мапу для /tag/{categoty}
    // там зроти if на вхідний параметр
    
    @GetMapping
    public String homePage() throws Exception {
        return crawCategory();
    }
    
    private String crawCategory() throws Exception {
//        Map<URL, String> links = new HashMap<URL, String>();
        URL ainUA = new URL("https://ain.ua");
        String htmlRes = "<h1>Select category:</h1>";

        Document document = Jsoup.connect(ainUA.toString()).get();

        Elements elements = document.select("a[href]");
        // зберігати посилення в сеті
        // потім відкривати посилання та витягувати title з сторінки
        for (Element el : elements) {
            if (!el.equals(null)) {
                String href = el.attr("href");
                if (href.length() > 5)
                    if (href.substring(1, 4).equals("tag")) {
                        String text = el.text();
                        if (!text.equals("Смотреть все") && text.length() > 0)
                            htmlRes += String.format("<h3>%s:  <a href=\"%s\">%s</a></h3>", text, href, href);
                    }
            }
        }

        return htmlRes;
    }

}
