package bw.lambdaschool.comake.repository;

import bw.lambdaschool.comake.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>
{
}
