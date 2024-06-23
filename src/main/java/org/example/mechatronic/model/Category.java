package org.example.mechatronic.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Table
@Entity
public class Category implements Serializable {
    @NonNull
    private String categoryName;
    @NonNull
    @Id
    @GeneratedValue
    private Long categoryId;
    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<MechatronicDevice> categoryItems = new ArrayList<>();

    public Category() {

    }

    public MechatronicDevice getDevice(String name) {
        for (MechatronicDevice device : categoryItems) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }
}
