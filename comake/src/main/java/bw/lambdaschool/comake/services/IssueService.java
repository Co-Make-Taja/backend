package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.models.Issue;

import java.util.List;

public interface IssueService
{
    List<Issue> findAll();

    Issue findIssueById(Long issueid);

    Issue save(Issue newIssue);

    void delete(long issueid);

    void deleteAll();
}
