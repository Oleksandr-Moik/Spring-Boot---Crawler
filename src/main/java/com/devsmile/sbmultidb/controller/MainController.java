package com.devsmile.sbmultidb.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
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
    
    private HashSet<String> links;
    private String aLink="https://ain.ua/";
    
    @GetMapping(value = "/")
    private String crawCategory() throws Exception {
//        Map<URL, String> links = new HashMap<URL, String>();
        String htmlRes = "";
        

        Document document = Jsoup.connect(aLink).get();

        // кравлити не всю сторінку, а блок(-и) з порібним вмістом
        // знайти спільні назви класів блоків

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
    
//    @GetMapping(value = "/tag/{category}")
//    public 
}
