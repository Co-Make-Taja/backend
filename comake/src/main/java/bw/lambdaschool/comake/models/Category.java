package bw.lambdaschool.comake.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity allowing interaction with the categories table.
 */
@Entity
@Table(name = "categories")
public class Category extends Auditable
{
    /**
     * The primary key (long) of the categories table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryid;

    /**
     * The categoryname (String) of the category. Cannot be null.
     */
    @Column(nullable = false)
    private String categoryname;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = "category", allowSetters = true)
    private Set<Issue> issues = new HashSet<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public Category()
    {
    }

    /**
     *
     * @param categoryname The category name (String) of category
     */
    public Category(String categoryname)
    {
        this.categoryname = categoryname;
    }

    public long getCategoryid()
    {
        return categoryid;
    }

    public void setCategoryid(long categoryid)
    {
        this.categoryid = categoryid;
    }

    public String getCategoryname()
    {
        return categoryname;
    }

    public void setCategoryname(String categoryname)
    {
        this.categoryname = categoryname;
    }

    public Set<Issue> getIssues()
    {
        return issues;
    }

    public void setIssues(Set<Issue> issues)
    {
        this.issues = issues;
    }
}
