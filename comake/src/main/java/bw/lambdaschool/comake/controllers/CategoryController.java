package bw.lambdaschool.comake.controllers;

import bw.lambdaschool.comake.models.Category;
import bw.lambdaschool.comake.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController
{
    @Autowired
    CategoryService categoryService;

    //categories/categories
    @GetMapping(value = "/categories", produces = {"application/json"})
    public ResponseEntity<?> listAllCategories(HttpServletRequest request)
    {
        Set<Category> categoryList = categoryService.findAll();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

}
