package org.example.mechatronic.repositories;

import org.example.mechatronic.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface  CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryName(String name);
}
