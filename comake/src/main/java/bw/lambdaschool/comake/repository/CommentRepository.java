package bw.lambdaschool.comake.repository;

import bw.lambdaschool.comake.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>
{
}
