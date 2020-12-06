package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.CoMakeApplication;
import bw.lambdaschool.comake.models.Category;
import bw.lambdaschool.comake.models.Issue;
import bw.lambdaschool.comake.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoMakeApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IssueServiceImplTest
{
    @Autowired
    private IssueService issueService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);


        Set<Issue> myList = issueService.findAll();
        for (Issue b : myList)
        {
            System.out.println(b.getIssueid() + " " + b.getTitle());
        }

        //  Issue List
        //        24 Caught in the Act!!!
        //        25 Looking for doll specialist
        //        26 Newborn care specialist


        List<User> userList = userService.findAll();
        for (User u : userList)
        {
            System.out.println(u.getUserid() + " " + u.getUsername());
        }

        //User List
        //        17 christian
        //        18 nelson
        //        19 rhea
        //        20 matty
        //        21 max
        //        22 sam
        //        23 taja
        //        27 aaron


        Set<Category> categoriesList = categoryService.findAll();
        for (Category c : categoriesList)
        {
            System.out.println(c.getCategoryid() + " " + c.getCategoryname());
        }

        // Category List
        //        8 General
        //        9 Holiday
        //        6 Crime & Safety
        //        16 Yard and Lawn
        //        5 Community Activities
        //        7 Flooding
        //        14 School & Education
        //        12 Recommendation
        //        28 General
        //        11 Pets
        //        10 Lost & Found
        //        4 Announcement
        //        13 Road Closure & Transportation
        //        15 Utilities
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAll()
    {
        assertEquals(3, issueService.findAll().size());
    }

    @Test
    public void findIssueById()
    {
        assertEquals("Caught in the Act!!! ",issueService.findIssueById((long) 24).getTitle());
    }

    @Test
    public void save()
    {
        //user
        User thisUser = new User("Aaron", "3126549785", "password", "aaron@email.com");
        thisUser = userService.save(thisUser);

        //category
        Category thisCategory = new Category("General");
        thisCategory = categoryService.save(thisCategory);

        //new issue
        Issue thisIssue = new Issue("Test Issue", "This is a test issue for testing test.", null, thisCategory, thisUser);

        Issue newIssue = issueService.save(thisIssue);
        assertNotNull(newIssue);
        assertEquals("Test Issue", newIssue.getTitle());
        assertEquals("aaron", newIssue.getUser().getUsername());


    }

    @Test
    public void y_delete()
    {
        issueService.delete(26);
        assertEquals(3, issueService.findAll().size());
    }

    @Test
    public void z_deleteAll()
    {
        issueService.deleteAll();
        assertEquals(0, issueService.findAll().size());
    }

    @Test
    public void update()
    {
        //user
        User updateUser = userService.findUserById(27);


        //category
        Category updateCategory = categoryService.findCategoryById(4);


        //new issue
        Issue updateIssue = issueService.findIssueById((long)29);

        updateIssue.setTitle("Edited Test Issue");
        updateIssue.setDescription("This is Edited test issue for testing test.");
        updateIssue.setImage(null);
        updateIssue.setCategory(updateCategory);
        updateIssue.setUser(updateUser);

        Issue updatedIssue = issueService.save(updateIssue);

        assertNotNull(updatedIssue);
        assertEquals("Edited Test Issue", updatedIssue.getTitle());
        assertEquals("Announcement", updatedIssue.getCategory().getCategoryname());
    }
}