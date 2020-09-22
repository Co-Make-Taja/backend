package bw.lambdaschool.comake;

import bw.lambdaschool.comake.models.*;
import bw.lambdaschool.comake.services.*;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    /**
     * Connects the category service to this process
     */
    @Autowired
    CategoryService categoryService;

    /**
     * Connects the issue service to this process
     */
    @Autowired
    IssueService issueService;

    @Autowired
    HelperFunctions helperFunctions;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        categoryService.deleteAll();
        issueService.deleteAll();


        // roles
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // categories
        Category c1 = new Category("Announcement");
        Category c2 = new Category("Community Activities");
        Category c3 = new Category("Crime & Safety");
        Category c4 = new Category("Flooding");
        Category c5 = new Category("General");
        Category c6 = new Category("Holiday");
        Category c7 = new Category("Lost & Found");
        Category c8 = new Category("Pets");
        Category c9 = new Category("Recommendation");
        Category c10 = new Category("Road Closure & Transportation");
        Category c11 = new Category("School & Education");
        Category c12 = new Category("Utilities");
        Category c13 = new Category("Yard and Lawn");

        c1 = categoryService.save(c1);
        c2 = categoryService.save(c2);
        c3 = categoryService.save(c3);
        c4 = categoryService.save(c4);
        c5 = categoryService.save(c5);
        c6 = categoryService.save(c6);
        c7 = categoryService.save(c7);
        c8 = categoryService.save(c8);
        c9 = categoryService.save(c9);
        c10 = categoryService.save(c10);
        c11 = categoryService.save(c11);
        c12 = categoryService.save(c12);
        c13 = categoryService.save(c13);


        // admin, data, user christian
        User u1 = new User("christian", "(123)456-1111",
                           "password123",
                           "christian@lambda.com");
        u1.getRoles()
                .add(new UserRoles(u1, r1));
        u1.getRoles()
                .add(new UserRoles(u1, r2));
        u1.getRoles()
                .add(new UserRoles(u1, r3));

        u1 = userService.save(u1);

        // admin, data, user nelson
        User u2 = new User("nelson", "(123)456-2222",
                           "pass123",
                           "nelson@lambda.com");
        u2.getRoles()
                .add(new UserRoles(u2, r1));
        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getRoles()
                .add(new UserRoles(u2, r3));

        u2 = userService.save(u2);

        // admin, data, user rhea
        User u3 = new User("rhea", "(123)456-3333",
                "pass123",
                "rhea@lambda.com");

        u3.getRoles()
                .add(new UserRoles(u3, r2));
        u3.getRoles()
                .add(new UserRoles(u3, r3));

        u3 = userService.save(u3);

        // user matty
        User u4 = new User("matty",
                             "(123)456-4444",
                           "pass123",
                           "matty@lambda.com");
        u4.getRoles()
                .add(new UserRoles(u4, r2));

        u4 = userService.save(u4);

        // user max
        User u5 = new User("max", "(123)456-5555",
                           "pass123",
                           "max@lambda.com");
        u5.getRoles()
                .add(new UserRoles(u5, r2));
        u5 = userService.save(u5);

        // user sam
        User u6 = new User("sam", "(123)456-6666",
                           "pass123",
                           "sam@lambda.com");
        u6.getRoles()
                .add(new UserRoles(u6, r2));
        u6 = userService.save(u6);

        // admin, data, user taja
        User u7 = new User("taja", "(123)456-7777",
                "pass123",
                "taja@lambda.com");
        u7.getRoles()
                .add(new UserRoles(u1, r1));
        u7.getRoles()
                .add(new UserRoles(u1, r2));
        u7.getRoles()
                .add(new UserRoles(u1, r3));

        u7 = userService.save(u7);

        // issues
        // String title, String description, String image, Category category
        Issue i1 = new Issue("Caught in the Act!!! ","Hello Neighbors, " +
                "Beware!!!  There are car break-Ins going on in our neighborhood. " +
                "Two of our vehicles (2014 Ford Focus & 2015 Toyota Tacoma Truck) were broken into (and several items stolen) at about 6:30am this morning!  Broad daylight!!! Our camera caught the man in the act!   Arcadia Police has been notified.  They came quickly, took report & pictures. Our Video was handed over to them. " +
                "We should all stay vigilant and please remove anything that’s private or valuable from your vehicles.  You just never know where they’ll strike next.", "https://th.bing.com/th/id/OIP.ANep9JYS6GHoaO2piR49zgHaEI?w=332&h=185&c=7&o=5&dpr=1.25&pid=1.7", c3, u1);

        Issue i2 = new Issue("Looking for doll specialist", "Hello! My mother recently passed away, leaving behind a large collection of porcelain dolls. While planning to keep a few, not quite sure of what to do with the rest. Would love to connect with a local specialist on this if anyone has a recommendation!", "https://i.ebayimg.com/images/g/ejUAAOSwOrNfFJTW/s-l640.jpg", c5, u1);

        Issue i3 = new Issue("Newborn care specialist.", "Does anyone know a good newborn care specialist (a nanny for newborn) in our neighborhood?", null, c5, u7);

        i1 = issueService.save(i1);
        i2 = issueService.save(i2);
        i3 = issueService.save(i3);

        u7.getIssues().add(i3);



        if (false)
        {
            // using JavaFaker create a bunch of regular users
            // https://www.baeldung.com/java-faker
            // https://www.baeldung.com/regular-expressions-java

            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                                                                        new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User(nameFaker.name()
                                            .username(), nameFaker.phoneNumber().cellPhone(),
                                    "password",
                                    nameFaker.internet()
                                            .emailAddress());
                fakeUser.getRoles()
                        .add(new UserRoles(fakeUser, r2));
                userService.save(fakeUser);
            }
        }
    }
}