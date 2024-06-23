package org.example.mechatronic.repositories;

import org.example.mechatronic.model.Category;
import org.example.mechatronic.model.MechatronicDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface  CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryName(String name);
    void deleteCategoryByCategoryId(Long categoryId);
    Category findCategoryByCategoryItemsContains(MechatronicDevice device);
}
