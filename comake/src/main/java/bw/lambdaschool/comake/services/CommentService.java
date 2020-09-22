package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.models.Comment;

import java.util.List;

public interface CommentService
{
    List<Comment> findAll();
}
