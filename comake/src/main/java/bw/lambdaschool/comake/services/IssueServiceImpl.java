package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.exceptions.ResourceNotFoundException;
import bw.lambdaschool.comake.models.Issue;
import bw.lambdaschool.comake.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service("issueService")
public class IssueServiceImpl implements IssueService
{
    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HelperFunctions helperFunctions;

    @Override
    public Set<Issue> findAll()
    {
        Set<Issue> list = new HashSet<>();
        issueRepository.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Issue findIssueById(Long issueid)
    {
        return issueRepository.findById(issueid)
                .orElseThrow(() -> new ResourceNotFoundException("Issue with id " + issueid + " Not Found!"));
    }

    @Transactional
    @Override
    public Issue save(Issue issue)
    {
        Issue newIssue = new Issue();

        if (issue.getIssueid() != 0)
        {
            issueRepository.findById(issue.getIssueid())
                    .orElseThrow(() -> new ResourceNotFoundException("Issue id " + issue.getIssueid() + " not found!"));
            newIssue.setIssueid(issue.getIssueid());
        }

        newIssue.setTitle(issue.getTitle());
        newIssue.setDescription(issue.getDescription());
        newIssue.setImage(issue.getImage());
        newIssue.setCategory(issue.getCategory());

        // setting the user to the issue created
        newIssue.setUser(issue.getUser());

        return issueRepository.save(newIssue);
    }

    @Transactional
    @Override
    public void delete(long issueid)
    {
        if (issueRepository.findById(issueid)
                .isPresent())
        {
            issueRepository.deleteById(issueid);
        } else
        {
            throw new ResourceNotFoundException("Issue with id " + issueid + " Not Found!");
        }
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        issueRepository.deleteAll();
    }


    @Transactional
    @Override
    public Issue update(Issue issue, long issueid)
    {
        Issue currentIssue = findIssueById(issueid);

        if (issueid != 0)
        {
                currentIssue.setUpvote(issue.getUpvote());

            return issueRepository.save(currentIssue);
        } else
        {
            // note we should never get to this line but is needed for the compiler
            // to recognize that this exception can be thrown
            throw new ResourceNotFoundException("Issue with id " + issueid + " not found!" );
        }
    }


}
