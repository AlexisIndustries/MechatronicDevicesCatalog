package org.example.mechatronic.model;

import jakarta.persistence.*;
import lombok.*;


@Table
@Entity
@Getter
@Setter
public class MechatronicDevice {
    @Id
    @GeneratedValue
    private Long deviceId;
    private String name; // Название устройств
    private String description; // Описание устройства
    private double price; // Цена устройства

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_data_id")
    private ImageData imageData;

    public MechatronicDevice(  String name, String description, double price, ImageData imageData ){
        this.price = price;
        this.description = description;
        this.name = name;
        this.imageData = imageData;
    }

    public MechatronicDevice() {

    }
}
