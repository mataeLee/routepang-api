package kr.sm.itaewon.routepang.controller.android;

import kr.sm.itaewon.routepang.model.ArticleComment;
import kr.sm.itaewon.routepang.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private ArticleCommentService commentService;

    @PostMapping("/article/comment")
    public ResponseEntity<String> postArticleComment(@RequestBody ArticleComment comment){
        commentService.save(comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
