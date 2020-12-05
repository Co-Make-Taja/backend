package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.*;
import bw.lambdaschool.comake.services.CommentService;
import bw.lambdaschool.comake.services.IssueService;
import bw.lambdaschool.comake.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IssueControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;
    private List<Issue> issueList = new ArrayList<>();
    private Set<Issue> issueSet = new HashSet<>();

    @MockBean
    private UserService userService;
    private  List<User> userList = new ArrayList<>();

    @MockBean
    private CommentService commentService;
    private List<Comment> commentList = new ArrayList<>();
    private Set<Comment> commentSet = new HashSet<>();


    @Before
    public void setUp() throws Exception
    {
        // roles
        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        // categories
        Category c1 = new Category("Announcement");
        c1.setCategoryid(1);
        Category c2 = new Category("Community Activities");
        c2.setCategoryid(2);
        Category c3 = new Category("Crime & Safety");
        c3.setCategoryid(3);
        Category c4 = new Category("Flooding");
        c4.setCategoryid(4);
        Category c5 = new Category("General");
        c5.setCategoryid(5);
        Category c6 = new Category("Holiday");
        c6.setCategoryid(6);
        Category c7 = new Category("Lost & Found");
        c7.setCategoryid(7);
        Category c8 = new Category("Pets");
        c8.setCategoryid(8);
        Category c9 = new Category("Recommendation");
        c9.setCategoryid(9);
        Category c10 = new Category("Road Closure & Transportation");
        c10.setCategoryid(10);
        Category c11 = new Category("School & Education");
        c11.setCategoryid(11);
        Category c12 = new Category("Utilities");
        c12.setCategoryid(12);
        Category c13 = new Category("Yard and Lawn");
        c13.setCategoryid(13);




        // admin, data, user christian
        User u1 = new User("christian", "(123)456-1111", "password123", "christian@lambda.com");
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));

        u1.setUserid(101);
        userList.add(u1);

        // admin, data, user nelson
        User u2 = new User("nelson", "(123)456-2222", "pass123", "nelson@lambda.com");
        u2.getRoles().add(new UserRoles(u2, r1));
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));

        u2.setUserid(102);
        userList.add(u2);

        // admin, data, user rhea
        User u3 = new User("rhea", "(123)456-3333", "pass123", "rhea@lambda.com");

        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getRoles().add(new UserRoles(u3, r3));

        u3.setUserid(103);
        userList.add(u3);

        // user matty
        User u4 = new User("matty", "(123)456-4444", "pass123", "matty@lambda.com");
        u4.getRoles().add(new UserRoles(u4, r2));

        u4.setUserid(104);
        userList.add(u4);

        // user max
        User u5 = new User("max", "(123)456-5555", "pass123", "max@lambda.com");
        u5.getRoles().add(new UserRoles(u5, r2));

        u5.setUserid(105);
        userList.add(u5);

        // user sam
        User u6 = new User("sam", "(123)456-6666", "pass123", "sam@lambda.com");
        u6.getRoles().add(new UserRoles(u6, r2));

        u6.setUserid(106);
        userList.add(u6);

        // admin, data, user taja
        User u7 = new User("taja", "(123)456-7777", "pass123", "taja@lambda.com");
        u7.getRoles().add(new UserRoles(u7, r1));
        u7.getRoles().add(new UserRoles(u7, r2));
        u7.getRoles().add(new UserRoles(u7, r3));

        u7.setUserid(107);
        userList.add(u7);

        // issues
        // String title, String description, String image, Category category
        Issue i1 = new Issue("Caught in the Act!!! ","Hello Neighbors, " +
                "Beware!!!  There are car break-Ins going on in our neighborhood. " +
                "Two of our vehicles (2014 Ford Focus & 2015 Toyota Tacoma Truck) were broken into (and several items stolen) at about 6:30am this morning!  Broad daylight!!! Our camera caught the man in the act!   Arcadia Police has been notified.  They came quickly, took report & pictures. Our Video was handed over to them. " +
                "We should all stay vigilant and please remove anything thats private or valuable from your vehicles.  You just never know where theyll strike next.", "https://th.bing.com/th/id/OIP.ANep9JYS6GHoaO2piR49zgHaEI?w=332&h=185&c=7&o=5&dpr=1.25&pid=1.7", c3, u1);

        Issue i2 = new Issue("Looking for doll specialist", "Hello! My mother recently passed away, leaving behind a large collection of porcelain dolls. While planning to keep a few, not quite sure of what to do with the rest. Would love to connect with a local specialist on this if anyone has a recommendation!", "https://i.ebayimg.com/images/g/ejUAAOSwOrNfFJTW/s-l640.jpg", c5, u1);

        Issue i3 = new Issue("Newborn care specialist.", "Does anyone know a good newborn care specialist (a nanny for newborn) in our neighborhood?", null, c5, u7);

        i1.setIssueid(201);
        issueList.add(i1);
        issueSet.add(i1);
        i2.setIssueid(202);
        issueList.add(i2);
        issueSet.add(i2);
        i3.setIssueid(203);
        issueList.add(i3);
        issueSet.add(i3);

        // comments

        Comment co1 = new Comment("this is a comment for Newborn", i3, u1);
        co1.setCommentid(301);
        commentList.add(co1);
        commentSet.add(co1);

        Comment co2 = new Comment("this is a comment for doll", i2, u1);
        co2.setCommentid(302);
        commentList.add(co2);
        commentSet.add(co2);

        // setup security in a test environment
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        // setup security in a test environment
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_listAllIssues()  throws Exception
    {
        String apiUrl = "/issues/issues";

        Mockito.when(issueService.findAll()).thenReturn(issueSet);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb).andReturn(); // this could throw an exception
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(issueSet);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getIssueById() throws Exception
    {
        String apiUrl = "/issues/issue/203";

        Mockito.when(issueService.findIssueById((long)203)).thenReturn(issueList.get(2));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(issueList.get(2));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }



    @Test
    public void getUpvoteForIssue() {
    }

    @Test
    public void incrementUpvote() {
    }

    @Test
    public void addNewIssue()
    {

    }

    @Test
    public void addCommentToIssue() {
    }

    @Test
    public void updateComment() {
    }

    @Test
    public void fullUpdateIssue() {
    }

    @Test
    public void deleteIssueById() {
    }
}