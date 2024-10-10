package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.dto.CategoryDTO;
import fptu.mobile_shop.MobileShop.entity.Category;

import java.util.List;

public interface CategoryService {
     List<Category> getAll();

     Category save(Category category);

     Category parseCategoryDtoToCategory(CategoryDTO categoryDTO);

}
