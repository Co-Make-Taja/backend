package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.CoMakeApplication;
import bw.lambdaschool.comake.models.User;
import bw.lambdaschool.comake.models.UserRoles;
import bw.lambdaschool.comake.exceptions.ResourceNotFoundException;
import bw.lambdaschool.comake.models.Role;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoMakeApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void B_findUserById()
    {
        TestCase.assertEquals("admin", userService.findUserById(4)
                .getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void BA_findUserByIdNotFound()
    {
        TestCase.assertEquals("admin", userService.findUserById(10)
                .getUsername());
    }

    @Test
    public void C_findAll()
    {
        assertEquals(5, userService.findAll()
                .size());
    }

    @Test
    public void D_delete()
    {
        userService.delete(13);
        assertEquals(4, userService.findAll()
                .size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void DA_notFoundDelete()
    {
        userService.delete(100);
        assertEquals(4, userService.findAll()
                .size());
    }

    @Test
    public void E_findByUsername()
    {
        TestCase.assertEquals("admin", userService.findByName("admin")
                .getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void AA_findByUsernameNotfound()
    {
        TestCase.assertEquals("admin", userService.findByName("turtle")
                .getUsername());
    }

    @Test
    public void AB_findByNameContaining()
    {
        assertEquals(4, userService.findByNameContaining("a")
                .size());
    }

    @Test
    public void F_save()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("tiger", "(123)456-1111", "ILuvMath!", "tiger@school.lambda");
        u2.getRoles().add(new UserRoles(u2, r2));

        User saveU2 = userService.save(u2);

        System.out.println("*** DATA ***");
        System.out.println(saveU2);
        System.out.println("*** DATA ***");

        assertEquals("tiger@school.lambda", u2.getPrimaryemail());
    }

    @Transactional
    @WithUserDetails("cinnamon")
    @Test
    public void G_update()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("cinnamon","(123)456-5555", "password", "cinnamon@school.lambda");
        u2.getRoles().add(new UserRoles(u2, r2));

        User updatedu2 = userService.update(u2, 7);

        System.out.println("*** DATA ***");
        System.out.println(updatedu2);
        System.out.println("*** DATA ***");

        assertEquals("cinnamon@school.lambda", u2.getPrimaryemail());
    }

    @Transactional
    @WithUserDetails("cinnamon")
    @Test(expected = ResourceNotFoundException.class)
    public void GB_updateNotCurrentUserNorAdmin()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("cinnamon", "(123)456-2222", "password", "cinnamon@school.lambda");
        u2.getRoles().add(new UserRoles(u2, r2));

        User updatedu2 = userService.update(u2, 8);

        System.out.println("*** DATA ***");
        System.out.println(updatedu2);
        System.out.println("*** DATA ***");

        assertEquals("cinnamon@school.lambda", u2.getPrimaryemail());
    }
}