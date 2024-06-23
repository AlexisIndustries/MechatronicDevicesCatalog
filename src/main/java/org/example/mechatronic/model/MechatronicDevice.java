package org.example.mechatronic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    public MechatronicDevice(  String name, String description, double price ){
        this.price = price;
        this.description = description;
        this.name = name;
    }

    public MechatronicDevice() {

    }

}
