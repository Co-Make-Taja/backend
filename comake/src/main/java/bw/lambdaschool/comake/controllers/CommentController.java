package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.Comment;
import bw.lambdaschool.comake.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    // comments/comment
    @PostMapping(value = "comment", consumes = {"application/json"}, produces = {"application/json"})
    public  ResponseEntity<?> addNewComment(@Valid @RequestBody Comment newComment) throws URISyntaxException
    {
        newComment.setCommentid(0);
        newComment = commentService.save(newComment);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCommentURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{commentid}")
                .buildAndExpand(newComment.getCommentid())
                .toUri();
        responseHeaders.setLocation(newCommentURI);

        return new ResponseEntity<>("Comment Posted!",
                responseHeaders,
                HttpStatus.CREATED);
    }


    // comments/comment/:id
    @PutMapping(value = "/comment/{id}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> updateComment(@Valid @RequestBody Comment updatedComment, @PathVariable long id)
    {
        updatedComment.setCommentid(id);
        commentService.save(updatedComment);

        return new ResponseEntity<>("Successfully Updated", HttpStatus.ACCEPTED);
    }


    // comments/comment/:id
    @DeleteMapping(value = "/comment/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long id)
    {
        commentService.delete(id);
        return new ResponseEntity<>("Comment Successfully Deleted!", HttpStatus.OK);
    }

}
