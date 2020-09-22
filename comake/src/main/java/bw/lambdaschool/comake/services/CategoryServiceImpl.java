package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.exceptions.ResourceFoundException;
import bw.lambdaschool.comake.exceptions.ResourceNotFoundException;
import bw.lambdaschool.comake.models.Category;
import bw.lambdaschool.comake.models.Role;
import bw.lambdaschool.comake.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(long categoryid)
    {
        return categoryRepository.findById(categoryid)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryid + " Not Found!"));
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        categoryRepository.deleteAll();
    }

    @Transactional
    @Override
    public Category save(Category category)
    {
        if (category.getIssues().size() > 0)
        {
            throw new ResourceFoundException("Issues are not added through categories.");
        }

        Category newCategory = new Category();

        newCategory.setCategoryname(category.getCategoryname());

        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> findAll()
    {
        List<Category> list = new ArrayList<>();
        categoryRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

}
