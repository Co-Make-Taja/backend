package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.models.Category;

import java.util.Set;


public interface CategoryService
{

    Category findCategoryById(long categoryid);

    void deleteAll();

    Category save(Category category);

    Set<Category> findAll();
}
