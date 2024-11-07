package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import fptu.mobile_shop.MobileShop.repository.BlogCategoryRepository;
import fptu.mobile_shop.MobileShop.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

    @Autowired
    private BlogCategoryRepository blogCategoryRepository;

    @Override
    public List<BlogCategory> getAllActiveCategories() {
        return blogCategoryRepository.findByStatusTrue(); // Lấy tất cả category đang hoạt động (status = TRUE)
    }

    @Override
    public BlogCategory getCategoryById(Long categoryId) {
        return blogCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
    }

    @Override
    public BlogCategory createCategory(BlogCategory category) {
        return blogCategoryRepository.save(category);
    }

    @Override
    public BlogCategory updateCategory(Long categoryId, BlogCategory categoryDetails) {
        BlogCategory existingCategory = getCategoryById(categoryId);
        existingCategory.setCategoryName(categoryDetails.getCategoryName());
        existingCategory.setStatus(categoryDetails.getStatus());
        return blogCategoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        blogCategoryRepository.deleteById(categoryId);
    }
    public String getCategoryNameById(Long categoryId) {
        BlogCategory category = blogCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
        return category.getCategoryName(); // Giả sử bạn có phương thức getName() trong BlogCategory
    }
}
