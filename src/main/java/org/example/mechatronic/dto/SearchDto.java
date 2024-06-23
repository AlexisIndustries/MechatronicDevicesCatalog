package org.example.mechatronic.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class SearchDto {
    private String searchText;
    private boolean filterDeviceName;
    private boolean filterCategoryName;
    private boolean filterPrice;

    public SearchDto(String searchText, boolean filterDeviceName, boolean filterCategoryName, boolean filterPrice) {
        this.searchText = searchText;
        this.filterDeviceName = filterDeviceName;
        this.filterCategoryName = filterCategoryName;
        this.filterPrice = filterPrice;
    }

    public SearchDto() {
    }
}
