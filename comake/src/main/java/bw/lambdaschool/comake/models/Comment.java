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
    @JoinColumn(name = "issueid")
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Issue issue;


    public Comment()
    {
    }

    public Comment(String comment)
    {
        this.comment = comment;
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
}
