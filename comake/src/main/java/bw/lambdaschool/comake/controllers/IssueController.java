package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.Comment;
import bw.lambdaschool.comake.models.Issue;
import bw.lambdaschool.comake.services.CommentService;
import bw.lambdaschool.comake.services.HelperFunctions;
import bw.lambdaschool.comake.services.IssueService;
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
import java.util.Set;

@RestController
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    IssueService issueService;

    @Autowired
    HelperFunctions helperFunctions;

    @Autowired
    CommentService commentService;

    //issues/issues
    @GetMapping(value = "/issues", produces = {"application/json"})
    public ResponseEntity<?> listAllIssues(HttpServletRequest request) {
        Set<Issue> issueList = issueService.findAll();
        return new ResponseEntity<>(issueList, HttpStatus.OK);

    }

    //issues/issue/:id
    @GetMapping(value = "/issue/{issueid}", produces = {"application/json"})
    public ResponseEntity<?> getIssueById(HttpServletRequest request, @PathVariable Long issueid) {
        Issue thisIssue = issueService.findIssueById(issueid);
        return new ResponseEntity<>(thisIssue, HttpStatus.OK);
    }

    //issues/issue/:id/comments
    @GetMapping(value = "/issue/{issueid}/comments")
    public ResponseEntity<?> getCommentsIssueById(HttpServletRequest request, @PathVariable Long issueid) {
        Issue thisIssue = issueService.findIssueById(issueid);
        Set<Comment> commentList = thisIssue.getComments();
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    //issues/issue/:id/comments/:id
    @GetMapping(value = "/issue/{issueid}/comment/{commentid}")
    public ResponseEntity<?> getIssueCommentById(HttpServletRequest request, @PathVariable Long issueid, @PathVariable Long commentid) {
        Issue thisIssue = issueService.findIssueById(issueid);
        Set<Comment> thisList = thisIssue.getComments();
        Comment thisComment = commentService.findCommentById(commentid);
        return new ResponseEntity<>(thisComment, HttpStatus.OK);
    }

    //issues/issue
    @PostMapping(value = "/issue", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewIssue(@Valid @RequestBody Issue newIssue) throws URISyntaxException
    {


        newIssue.setUser(helperFunctions.getCurrentUser());
        newIssue.setIssueid(0);
        newIssue = issueService.save(newIssue);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newIssueURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{issueid}")
                .buildAndExpand(newIssue.getIssueid()).toUri();
        responseHeaders.setLocation(newIssueURI);
        return new ResponseEntity<>("Your Issue is Successfully Created!", responseHeaders, HttpStatus.CREATED);
    }


    //issues/issue/:id/comments
    @PostMapping(value = "/issue/{issueid}/comments", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addCommentToIssue(@Valid @RequestBody Comment newComment, @PathVariable long issueid) throws URISyntaxException
    {
        Issue thisIssue = issueService.findIssueById(issueid);
        newComment.setUser(helperFunctions.getCurrentUser());

        newComment.setCommentid(0);
        newComment.setIssue(thisIssue);
        newComment.setComment(newComment.getComment());

        newComment = commentService.save(newComment);
System.out.println(newComment);
        thisIssue.getComments().add(newComment);



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
    // using fullupdate since FE will setup forms with auto populate
    //issues/issue/:id
    @PutMapping(value = "/issue/{issueid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> fullUpdateIssue(@Valid @RequestBody Issue updateIssue, @PathVariable long issueid)
    {
        updateIssue.setUser(helperFunctions.getCurrentUser()); // set user to current user
        updateIssue.setIssueid(issueid);
        issueService.save(updateIssue);

        return new ResponseEntity<>("Issue Successfully Updated!", HttpStatus.ACCEPTED);
    }

    //issues/issue/{issueid}
    @DeleteMapping(value = "/issue/{issueid}")
    public ResponseEntity<?> deleteIssueById(@PathVariable long issueid)
    {
        issueService.delete(issueid);
        return new ResponseEntity<>("Issue Deleted!", HttpStatus.OK);
    }
}