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
    public ResponseEntity<?> getCommentsIssueById(@PathVariable Long issueid)
    {
        Issue thisIssue = issueService.findIssueById(issueid);
        Set<Comment> commentList = thisIssue.getComments();
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }


    //issues/issue/:id/upvote
    @GetMapping(value = "/issue/{issueid}/upvote")
    public ResponseEntity<?> getUpvoteForIssue(@PathVariable Long issueid)
    {
        Issue thisIssue = issueService.findIssueById(issueid);
        int count = thisIssue.getUpvote();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //issues/issue:id/upvote
    @PatchMapping(value = "/issue/{issueid}/upvote", consumes = {"application/json"})
    public ResponseEntity<?> incrementUpvote(@RequestBody Issue updateIssue, @PathVariable long issueid)
    {
        issueService.update(updateIssue, issueid);
        return new ResponseEntity<>("Upvoted!", HttpStatus.OK);
    }


    //issues/issue
    @PostMapping(value = "/issue", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewIssue(@Valid @RequestBody Issue newIssue) throws URISyntaxException
    {

        newIssue.setUser(helperFunctions.getCurrentUser());
        newIssue.setIssueid(0);
        newIssue.setCategory(newIssue.getCategory());
        newIssue.setTitle(newIssue.getTitle());
        newIssue.setImage(newIssue.getImage());
        newIssue.setDescription(newIssue.getDescription());


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

    //issues/issue/:id/comment/:id
    @PutMapping(value = "/issue/{issueid}/comment/{commentid}", consumes = {"application/json"})
    public ResponseEntity<?> updateComment(@Valid @RequestBody Comment updateComment, @PathVariable long issueid, @PathVariable long commentid) throws URISyntaxException
    {
        Issue thisIssue = issueService.findIssueById(issueid);
        updateComment.setUser(helperFunctions.getCurrentUser());

        updateComment.setCommentid(commentid);
        updateComment.setIssue(thisIssue);
        updateComment.setComment(updateComment.getComment());

        updateComment = commentService.save(updateComment);

        thisIssue.getComments().add(updateComment);


        return new ResponseEntity<>("Comment Updated!",
                HttpStatus.ACCEPTED);
    }

    // using fullupdate since FE will setup forms with auto populate
    //issues/issue/:id
    @PutMapping(value = "/issue/{issueid}", consumes = {"application/json"})
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