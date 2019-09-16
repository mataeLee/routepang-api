package kr.sm.itaewon.travelmaker.controller;

import kr.sm.itaewon.travelmaker.model.Link;
import kr.sm.itaewon.travelmaker.repo.LinkRepository;
import kr.sm.itaewon.travelmaker.util.LinkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkRepository repository;

    private LinkManager linkManager = new LinkManager();

    @RequestMapping("/")
    public ResponseEntity<Void> badRequest(){
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/postLink")
    public ResponseEntity<Link> postLink(@RequestBody String linkUrl){

        Link model = linkManager.LinkApi(linkUrl);

        if(model == null || model.getLinkUrl() == null){
            return new ResponseEntity<Link>(HttpStatus.BAD_REQUEST);
        }
        try {
            repository.save(model);

            return new ResponseEntity<Link>(model, HttpStatus.CREATED);
        }
        catch (Exception e){
            //TODO log
            e.printStackTrace();

            return new ResponseEntity<Link>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}