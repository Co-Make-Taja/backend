package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.exceptions.ResourceNotFoundException;
import bw.lambdaschool.comake.models.Issue;
import bw.lambdaschool.comake.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public List<Issue> findAll()
    {
        List<Issue> list = new ArrayList<>();
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
        }

        newIssue.setTitle(issue.getTitle());
        newIssue.setDescription(issue.getDescription());
        newIssue.setImage(issue.getImage());
        if (issue.getCategory() != null)
        {
            newIssue.setCategory(categoryService.findCategoryById(issue.getCategory()
                    .getCategoryid()));
        }

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


}
