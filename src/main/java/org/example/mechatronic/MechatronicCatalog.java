package org.example.mechatronic;

import org.example.mechatronic.model.Category;
import org.example.mechatronic.model.MechatronicDevice;

import java.util.ArrayList;

// Класс, реализующий каталог мехатронных устройств
public class MechatronicCatalog {
    private ArrayList<Category> categories; // Список, хранящий устройства

    // Конструктор, создающий пустой каталог
    public MechatronicCatalog() {
        categories = new ArrayList<>();
    }

    // Метод, добавляющий новое устройство в каталог
    public void addCategory(Category category) {
        categories.add(category);
    }

    // Метод, удаляющий устройство из каталога по названию
    public void removeCategory(String name) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryName().equals(name)) {
                categories.remove(i);
                break;
            }
        }
    }

    // Метод, возвращающий устройство из каталога по названию
    public Category getCategory(String name) {
        for (Category category : categories) {
            if (category.getCategoryName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    // Метод, выводящий информацию о каталоге на экран
    public void printCatalog() {
        System.out.println("Каталог мехатронных устройств:");
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    public void printDevicesInCategory(Category category) {
        System.out.println("Мехатронные устройства в категории:");
        for (MechatronicDevice device : category.getCategoryItems()) {
            System.out.println(device);
        }
    }
}
