package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.exceptions.ResourceNotFoundException;
import bw.lambdaschool.comake.models.Comment;
import bw.lambdaschool.comake.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("commentService")
public class CommentServiceImpl implements CommentService
{
    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findAll()
    {
        List<Comment> list = new ArrayList<>();
        commentRepository.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Comment save(Comment comment)
    {
        Comment newComment = new Comment();

        if (comment.getCommentid() != 0)
        {
            commentRepository.findById(comment.getCommentid())
                    .orElseThrow(() -> new ResourceNotFoundException("Comment id " + comment.getCommentid() + " not found!"));
            newComment.setCommentid(comment.getCommentid());
        }

        newComment.setComment(comment.getComment());
        newComment.setIssue(comment.getIssue());
        newComment.setUser(comment.getUser());

        return commentRepository.save(newComment);
    }

    @Override
    public void delete(long id)
    {
        if (commentRepository.findById(id).isPresent())
        {
            commentRepository.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Comment with id " + id + " Not Found!");
        }
    }

    @Override
    public Comment findCommentById(Long id)
    {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " Not Found!"));
    }

    @Override
    public void deleteAll()
    {
        commentRepository.deleteAll();
    }
}
