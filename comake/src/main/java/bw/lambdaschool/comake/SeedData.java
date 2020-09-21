package bw.lambdaschool.comake;

import bw.lambdaschool.comake.services.RoleService;
import bw.lambdaschool.comake.services.UserService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import bw.lambdaschool.comake.models.Role;
import bw.lambdaschool.comake.models.User;
import bw.lambdaschool.comake.models.UserRoles;
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
public class SeedData
        implements CommandLineRunner
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
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

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

        userService.save(u1);

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

        userService.save(u2);

        // admin, data, user rhea
        User u3 = new User("rhea", "(123)456-3333",
                "pass123",
                "rhea@lambda.com");

        u3.getRoles()
                .add(new UserRoles(u3, r2));
        u3.getRoles()
                .add(new UserRoles(u3, r3));

        userService.save(u3);

        // user matty
        User u4 = new User("matty", "(123)456-4444",
                           "pass123",
                           "matty@lambda.com");
        u4.getRoles()
                .add(new UserRoles(u4, r2));

        userService.save(u4);

        // user max
        User u5 = new User("max", "(123)456-5555",
                           "pass123",
                           "max@lambda.com");
        u5.getRoles()
                .add(new UserRoles(u5, r2));
        userService.save(u5);

        // user sam
        User u6 = new User("sam", "(123)456-6666",
                           "pass123",
                           "sam@lambda.com");
        u6.getRoles()
                .add(new UserRoles(u6, r2));
        userService.save(u6);

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