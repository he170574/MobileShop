package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.dto.CategoryDTO;
import fptu.mobile_shop.MobileShop.entity.Category;
import fptu.mobile_shop.MobileShop.repository.CategoryRepository;
import fptu.mobile_shop.MobileShop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category parseCategoryDtoToCategory(CategoryDTO categoryDTO){
        return Category.builder()
                .CategoryName(categoryDTO.getCategoryName())
                .DELETED(false)
                .build();
    }
}
