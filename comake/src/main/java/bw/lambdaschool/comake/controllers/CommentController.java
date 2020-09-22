package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.Comment;
import bw.lambdaschool.comake.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController
{
    @Autowired
    CommentService commentService;

    // comments/comments
    @GetMapping(value = "/comments", produces = {"application/json"})
    public ResponseEntity<?> listAllComments(HttpServletRequest request)
    {
        List<Comment> commentList = commentService.findAll();
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

}
