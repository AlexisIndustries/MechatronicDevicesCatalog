package org.example.mechatronic.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mechatronic.dto.SearchDto;
import org.example.mechatronic.model.Category;
import org.example.mechatronic.model.MechatronicDevice;
import org.example.mechatronic.repositories.CategoryRepository;
import org.example.mechatronic.repositories.MechatronicDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Controller
public class SearchController {
    private final MechatronicDeviceRepository deviceRepository;
    private final CategoryRepository categoryRepository;
    private final MechatronicDeviceRepository mechatronicDeviceRepository;

    public SearchController(MechatronicDeviceRepository deviceRepository, CategoryRepository categoryRepository, MechatronicDeviceRepository mechatronicDeviceRepository) {
        this.deviceRepository = deviceRepository;
        this.categoryRepository = categoryRepository;
        this.mechatronicDeviceRepository = mechatronicDeviceRepository;
    }

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("devices", new HashSet<MechatronicDevice>());
        return "search";
    }

    @PostMapping("/search")
    public String showSearchFormPost(Model model, SearchDto searchDto) {
        HashSet<MechatronicDevice> devices = new HashSet<>();

        if (!searchDto.isFilterCategoryName() && !searchDto.isFilterDeviceName() && !searchDto.isFilterPrice()) {
            if (!Objects.equals(searchDto.getSearchText(), "")) {
                devices.addAll(mechatronicDeviceRepository.findAll().stream().filter(b ->
                        b.getName().contains(searchDto.getSearchText())
                ).toList());
                List<Category> categories = categoryRepository.findAll().stream().filter(b -> b.getCategoryName().contains(searchDto.getSearchText())).toList();
                for (Category category : categories) {
                    devices.addAll(category.getCategoryItems());
                }
            }
            else {
                devices.addAll(mechatronicDeviceRepository.findAll());
            }
        }
        else if (searchDto.isFilterPrice()) {
            devices.addAll(mechatronicDeviceRepository.findAll()
                    .stream().filter(b -> b.getPrice() >= Double.parseDouble(searchDto.getSearchText())).toList());
        }
        else if (searchDto.isFilterCategoryName()) {
            List<Category> categories = categoryRepository.findAll().stream().filter(b -> b.getCategoryName().contains(searchDto.getSearchText())).toList();
            for (Category category : categories) {
                devices.addAll(category.getCategoryItems());
            }
        }
        else {
            devices.addAll(mechatronicDeviceRepository.findAll().stream().filter(b ->
                    b.getName().contains(searchDto.getSearchText())
            ).toList());
        }

        model.addAttribute("searchDto", searchDto);
        model.addAttribute("devices", devices);

        return "search";
    }
}
