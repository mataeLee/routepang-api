package kr.sm.itaewon.travelmaker.manager;

import kr.sm.itaewon.travelmaker.model.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LinkManager {



    public Link LinkApi(String param) {

        Link link = new Link();
        link.setLinkUrl(param);

        try{
            if(param.indexOf("https://") == -1)
                param = "https://" + param;

            Document rawData = Jsoup.connect(param).get();

            String[] values = param.split("/");

            String base_url = "https://" + values[2];

            Elements icons = rawData.select("link[href~=.*\\.(ico|png)]");
            Elements imgs = rawData.select("meta[property=og:image]");

            String favImage = null;
            if (icons.size() == 0){
                icons = rawData.select("meta[itemprop=image]");

                if (icons.size() > 0){
                    favImage = icons.first().attr("content");
                }
            }
            else{
                favImage = icons.attr("href");
            }

            if(favImage == null)
                favImage = "No_Content";
            else if(favImage.indexOf("https://") == -1)
                favImage = base_url + favImage;

            String main_img;
            if(imgs.size() == 0){
                main_img = "No_Content";
            }else{
                main_img = imgs.first().attr("abs:content");
            }

            link.setSummary(rawData.title());
            link.setFavicon(favImage);
            link.setImage(main_img);

        }catch (IOException e){
            link.setLinkUrl(null);
        }
        return link;
    }
}
