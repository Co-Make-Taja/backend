package bw.lambdaschool.comake.models;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryid;

    @Column(nullable = false)
    private String name;


}
