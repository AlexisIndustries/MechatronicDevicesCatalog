package org.example.mechatronic.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.mechatronic.dto.CategoryItemDto;
import org.example.mechatronic.model.Category;
import org.example.mechatronic.model.MechatronicDevice;
import org.example.mechatronic.repositories.CategoryRepository;
import org.example.mechatronic.repositories.MechatronicDeviceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class DevicesAndCategoriesController {
    private MechatronicDeviceRepository deviceRepository;
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategoryPost(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:categories";
    }

    @GetMapping("/addItemsForCategory")
    public String addItemsForCategory(Model model) {
        //ArrayList<Category> categories = (ArrayList<Category>) categoryRepository.findAll();
        model.addAttribute("category", new CategoryItemDto("", "", "", 0));
        model.addAttribute("categories", categoryRepository.findAll());
        return "addItemsForCategory";
    }

    @PostMapping("/addItemsForCategory")
    public String addItemsForCategoryPost(@ModelAttribute CategoryItemDto categoryItemDto) {
        Category category = categoryRepository.findCategoryByCategoryName(categoryItemDto.getCategoryName());
        List<MechatronicDevice> devices = category.getCategoryItems();
        devices.add(new MechatronicDevice(categoryItemDto.getItemName(), categoryItemDto.getDescription(), categoryItemDto.getPrice()));
        category.setCategoryItems(devices);
        categoryRepository.save(category);
        return "redirect:categories";
    }

    @RequestMapping(value = "/deleteCategory/{categoryId}", method = RequestMethod.GET)
    @Transactional
    public String deleteCategory(@PathVariable(name = "categoryId") String categoryId) {
        categoryRepository.deleteCategoryByCategoryId(Long.parseLong(categoryId));
        return "redirect:/categories";
    }

    @RequestMapping(value = "/deleteDeviceInCategory/{deviceId}", method = RequestMethod.GET)
    @Transactional
    public String deleteDeviceInCategory(@PathVariable(name = "deviceId") String deviceId) {
        deviceRepository.deleteMechatronicDeviceByDeviceId(Long.parseLong(deviceId));
        return "redirect:/categories";
    }

    @RequestMapping(value = "/viewDevice/{deviceId}", method = RequestMethod.GET)
    public String viewDevice(@PathVariable(name = "deviceId") String deviceId, Model model) {
        MechatronicDevice device = deviceRepository.findByDeviceId(Long.parseLong(deviceId));
        Category category = categoryRepository.findCategoryByCategoryItemsContains(device);
        model.addAttribute("device", device);
        model.addAttribute("categoryName", category.getCategoryName());
        return "deviceDescription";
    }
}
