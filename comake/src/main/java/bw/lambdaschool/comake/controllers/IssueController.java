package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.Issue;
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
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController
{
    @Autowired
    IssueService issueService;

    //issues/issues
    @GetMapping(value = "/issues", produces = {"application/json"})
    public ResponseEntity<?> listAllIssues(HttpServletRequest request)
    {
        List<Issue> issueList = issueService.findAll();
        return new ResponseEntity<>(issueList, HttpStatus.OK);

    }

    //issues/issue/:id
    @GetMapping(value = "/issue/{issueid}", produces = {"application/json"})
    public ResponseEntity<?> getIssueById(HttpServletRequest request, @PathVariable Long issueid)
    {
        Issue thisIssue = issueService.findIssueById(issueid);
        return new ResponseEntity<>(thisIssue, HttpStatus.OK);
    }

    //issues/issue/:id/comments
//    @GetMapping(value = "/issues/issue/{issueid}/comments")
//    public ResponseEntity<?> getCommentsIssueById(HttpServletRequest request, @PathVariable Long issueid)
//    {
//        Issue thisIssue = issueService.findIssueById(issueid);
//        return new ResponseEntity<>(thisIssue, HttpStatus.OK);
//    }

    //issues/issue
    @PostMapping(value = "/issue", consumes = {"application/json"})
    public ResponseEntity<?> addNewIssue(@Valid @RequestBody Issue newIssue) throws URISyntaxException
    {
        newIssue.setIssueid(0);
        newIssue = issueService.save(newIssue);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newIssueURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{issueid}")
                .buildAndExpand(newIssue.getIssueid()).toUri();
        responseHeaders.setLocation(newIssueURI);
        return new ResponseEntity<>("Your Issue is Successfully Created!", responseHeaders, HttpStatus.CREATED);
    }

    // using fullupdate since FE will setup forms with auto populate
    //issues/issue/:id
    @PutMapping(value = "/issue/{issueid}", consumes = {"application/json"})
    public ResponseEntity<?> fullUpdateIssue(@Valid @RequestBody Issue updateIssue, @PathVariable long issueid)
    {
        updateIssue.setIssueid(issueid);
        issueService.save(updateIssue);

        return new ResponseEntity<>("Issue Successfully Updated!", HttpStatus.ACCEPTED);
    }

    //issues/issue/{issueid}
    @DeleteMapping(value = "/issue{issueid}")
    public ResponseEntity<?> deleteIssueById(@PathVariable long issueid)
    {
        issueService.delete(issueid);
        return new ResponseEntity<>("Issue Deleted!", HttpStatus.OK);
    }
}