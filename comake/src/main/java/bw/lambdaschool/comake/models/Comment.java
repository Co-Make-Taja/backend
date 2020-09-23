package bw.lambdaschool.comake.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentid;

    @Column(nullable = false, length = 10000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "issueid", nullable = false)
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private User user;


    public Comment()
    {
    }

//    public Comment(String comment)
//    {
//        this.comment = comment;
//    }

    public Comment(String comment, Issue issue, User user)
    {
        this.comment = comment;
        this.issue = issue;
        this.user = user;
    }

    public long getCommentid()
    {
        return commentid;
    }

    public void setCommentid(long commentid)
    {
        this.commentid = commentid;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Issue getIssue()
    {
        return issue;
    }

    public void setIssue(Issue issue)
    {
        this.issue = issue;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
