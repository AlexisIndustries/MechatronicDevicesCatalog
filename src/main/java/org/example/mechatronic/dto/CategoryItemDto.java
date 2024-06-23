package org.example.mechatronic.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CategoryItemDto {
    private String categoryName;
    private String itemName;
    private String description;
    private int price;
}
