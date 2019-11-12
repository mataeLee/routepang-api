package kr.sm.itaewon.routepang.service;

import kr.sm.itaewon.routepang.model.Link;
import kr.sm.itaewon.routepang.repo.LinkRepository;
import kr.sm.itaewon.routepang.util.LinkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    private LinkManager linkManager = new LinkManager();

    public Link findByLinkUrl(String linkUrl) {
        Link link = linkRepository.findByLinkUrl(linkUrl);

        return link;
    }

    public boolean save(Link link) {
        try {
            linkRepository.save(link);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Link save(String linkUrl){
        Link model = linkManager.LinkApi(linkUrl);
        linkRepository.save(model);
        return model;
    }
}
