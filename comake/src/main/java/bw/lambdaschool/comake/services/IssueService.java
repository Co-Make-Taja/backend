package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.models.Issue;

import java.util.Set;

public interface IssueService
{
    Set<Issue> findAll();

    Issue findIssueById(Long issueid);

    Issue save(Issue newIssue);

    void delete(long issueid);

    void deleteAll();

    Issue update(Issue issue, long issueid);
}
