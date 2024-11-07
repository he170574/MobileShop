package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.BlogCategory;
import java.util.List;

public interface BlogCategoryService {
    List<BlogCategory> getAllActiveCategories();
    BlogCategory getCategoryById(Long categoryId);
    BlogCategory createCategory(BlogCategory category);
    BlogCategory updateCategory(Long categoryId, BlogCategory categoryDetails);
    void deleteCategory(Long categoryId);
    String getCategoryNameById(Long categoryId);
}
