package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.models.Category;
import bw.lambdaschool.comake.models.Role;

import java.util.List;


public interface CategoryService
{

    Category findCategoryById(long categoryid);

    void deleteAll();

    Category save(Category category);

    List<Category> findAll();
}
