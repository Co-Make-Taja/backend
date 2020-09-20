package bw.lambdaschool.comake.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "issues")
public class Issue extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long issueid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String description;

    @Column
    private String image;


    /**
     * The categoryid of the category assigned to this issue is what is stored in the database.
     * This is the entire category object!
     * <p>
     * Forms a Many to One relationship between issues and categories.
     * A category can have many issues.
     */
    @ManyToOne
    @JoinColumn(name = "categoryid")
    @JsonIgnoreProperties(value = "issues", allowSetters = true)
    private Category category;

    /**
     * The default controller is required by JPA
     */
    public Issue()
    {

    }
}
