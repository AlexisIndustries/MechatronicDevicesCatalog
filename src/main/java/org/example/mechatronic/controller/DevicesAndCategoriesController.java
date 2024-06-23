package org.example.mechatronic.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.mechatronic.dto.CategoryItemDto;
import org.example.mechatronic.model.Category;
import org.example.mechatronic.model.ImageData;
import org.example.mechatronic.model.MechatronicDevice;
import org.example.mechatronic.repositories.CategoryRepository;
import org.example.mechatronic.repositories.MechatronicDeviceRepository;
import org.example.mechatronic.utilities.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public String addCategoryPost(@ModelAttribute Category category, Model model) {
        if (categoryRepository.existsCategoryByCategoryName(category.getCategoryName())) {
            model.addAttribute("errors", "Категория уже существует");
            model.addAttribute("category", category);
                return "addCategory";
        }
        categoryRepository.save(category);
        return "redirect:categories";
    }

    @GetMapping("/addItemsForCategory")
    public String addItemsForCategory(Model model) {
        model.addAttribute("category", new CategoryItemDto("", "", "", 0, null));
        model.addAttribute("categories", categoryRepository.findAll());
        return "addItemsForCategory";
    }

    @PostMapping("/addItemsForCategory")
    public String addItemsForCategoryPost(@ModelAttribute CategoryItemDto categoryItemDto) throws IOException {
        Category category = categoryRepository.findCategoryByCategoryName(categoryItemDto.getCategoryName());
        List<MechatronicDevice> devices = category.getCategoryItems();
        MultipartFile file = categoryItemDto.getImage();
        ImageData imageData;
        if (Objects.equals(file.getOriginalFilename(), "")) {
            imageData = null;
        } else imageData = ImageData.builder().name(file.getOriginalFilename()).type(file.getContentType()).imageData(ImageUtil.compressImage(file.getBytes())).build();

        devices.add(new MechatronicDevice(categoryItemDto.getItemName(), categoryItemDto.getDescription(), categoryItemDto.getPrice(), imageData));
        category.setCategoryItems(devices);
        categoryRepository.save(category);
        return "redirect:/categories";
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
        if (device.getImageData() != null) {
            model.addAttribute("hasImage", true);
        } else model.addAttribute("hasImage", false);
        model.addAttribute("device", device);
        model.addAttribute("categoryName", category.getCategoryName());
        return "deviceDescription";
    }

    @RequestMapping(value = "/editDevice/{deviceId}", method = RequestMethod.GET)
    public String editDevice(@PathVariable(name = "deviceId") String deviceId, Model model) {
        MechatronicDevice device = deviceRepository.findByDeviceId(Long.parseLong(deviceId));
        Category category = categoryRepository.findCategoryByCategoryItemsContains(device);
//        model.addAttribute("device", device);
//        model.addAttribute("categoryName", category);
        CategoryItemDto categoryItemDto = new CategoryItemDto();
        categoryItemDto.setCategoryName(category.getCategoryName());
        categoryItemDto.setItemName(device.getName());
        categoryItemDto.setPrice((int) device.getPrice());
        categoryItemDto.setDescription(device.getDescription());
        categoryItemDto.setImage(null);
        categoryItemDto.setItemId(String.valueOf(device.getDeviceId()));
        model.addAttribute("category", categoryItemDto);
        return "deviceEdit";
    }

    @RequestMapping(value = "/editDevice", method = RequestMethod.POST)
    public String editDevice( @ModelAttribute CategoryItemDto categoryItemDto) throws IOException {
        //MechatronicDevice device = deviceRepository.findByDeviceId(updatedDevice.getDeviceId());
        //updatedDevice.setDeviceId(Long.valueOf(deviceId));
        MultipartFile file = categoryItemDto.getImage();
        ImageData imageData;
        if (Objects.equals(file.getOriginalFilename(), "")) {
            imageData = null;
        } else imageData = ImageData.builder().name(file.getOriginalFilename()).type(file.getContentType()).imageData(ImageUtil.compressImage(file.getBytes())).build();
        MechatronicDevice device = new MechatronicDevice(categoryItemDto.getItemName(), categoryItemDto.getDescription(), categoryItemDto.getPrice(), imageData);
        device.setDeviceId(Long.valueOf(categoryItemDto.getItemId()));
        deviceRepository.save(device);
        return "redirect:/viewDevice/" + device.getDeviceId();
    }
}
