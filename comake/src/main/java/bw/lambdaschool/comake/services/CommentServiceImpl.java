package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.models.Comment;
import bw.lambdaschool.comake.models.Issue;
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
}
