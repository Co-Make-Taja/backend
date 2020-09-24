package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.Comment;
import bw.lambdaschool.comake.services.CommentService;
import bw.lambdaschool.comake.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController
{
    @Autowired
    CommentService commentService;

    @Autowired
    HelperFunctions helperFunctions;

    // comments/comments
    @GetMapping(value = "/comments", produces = {"application/json"})
    public ResponseEntity<?> listAllComments(HttpServletRequest request)
    {
        List<Comment> commentList = commentService.findAll();
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    //comments/comment/:id
    @GetMapping(value = "/comment/{id}", produces = {"application/json"})
    public ResponseEntity<?> getCommentById(HttpServletRequest request, @PathVariable Long id)
    {
        Comment thisComment = commentService.findCommentById(id);
        return new ResponseEntity<>(thisComment, HttpStatus.OK);
    }


    // comments/comment/:id
    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long id)
    {
        commentService.delete(id);
        return new ResponseEntity<>("Comment Successfully Deleted!", HttpStatus.OK);
    }

}
