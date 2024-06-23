package org.example.mechatronic.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

//@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CategoryItemDto {
    private String categoryName;
    private String itemId;
    private String itemName;
    private String description;
    private int price;
    private MultipartFile image;

    public CategoryItemDto(String categoryName, String itemName, String description, int price, MultipartFile image) {
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public CategoryItemDto() {
    }
}
