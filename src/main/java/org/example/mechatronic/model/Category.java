package org.example.mechatronic.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Category implements Serializable {
    private String categoryName;
    @Id
    @GeneratedValue
    private Long categoryId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<MechatronicDevice> categoryItems;
//    public MechatronicDevice getDevice(String name) {
//        for (MechatronicDevice device : categoryItems) {
//            if (device.getName().equals(name)) {
//                return device;
//            }
//        }
//        return null;
//    }
}
