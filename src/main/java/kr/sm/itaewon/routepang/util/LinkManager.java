package kr.sm.itaewon.routepang.util;

import kr.sm.itaewon.routepang.model.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LinkManager { // LinkScraper

    public Link LinkApi(String param) {
        //TODO 찬영아 메소드 명을 동사형태로 바꾸자 그리고 시작은 소문자로...  - 정원용멘토님

        //TODO 굳이 미리 생성과 모델 셋팅을 할 필요가 없어 보임
        Link link = new Link();
        link.setLinkUrl(param);

        try{
            //TODO http로 시작하는 링크 생각해라
            //TODO param.startsWith()가 더 적합한듯 하다 Strgin class의 메소드를 이용하자  - 정원용멘토님
            //TODO java.net.URL Class 를 이용하여 getHost()같은 메소드를 이용하면 param과 baseURL을 쉽게 사용 가능할듯 - 정원용멘토님
            //TODO 원격 가져오는 부분, parsing 하는 부분 나누자

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
            else if(favImage.indexOf("https://") == -1) // http 일때 생각 하자
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
        return link;//null
    }
}