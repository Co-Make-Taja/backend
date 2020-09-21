package bw.lambdaschool.comake.repository;

import bw.lambdaschool.comake.models.Issue;
import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue, Long>
{

}
