package bw.lambdaschool.comake.services;

import bw.lambdaschool.comake.exceptions.ResourceNotFoundException;
import bw.lambdaschool.comake.models.Category;
import bw.lambdaschool.comake.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
