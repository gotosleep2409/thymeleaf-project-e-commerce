package com.example.shop.controller.admin;

import com.example.shop.model.Category;
import com.example.shop.model.Contents;
import com.example.shop.respository.ContentRepository;
import com.example.shop.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/contents")
public class ContentController {
    @Autowired
    private ContentService contentService;
    private ContentRepository contentRepository;

    public ContentController(ContentService contentService, ContentRepository contentRepository) {
        this.contentService = contentService;
        this.contentRepository = contentRepository;
    }
    @GetMapping("")
    public String getAllContent(Model model){
        List<Contents> contents = contentRepository.findAll();
        model.addAttribute("contents", contents);
        return "admin/content/index";
    }
    @GetMapping("addContent")
    public String addContent(Model model){
        model.addAttribute("content", new Contents());
        return "admin/content/addContent";
    }
    @PostMapping("/addContent")
    public String addContent(@ModelAttribute("content") Contents content){
        contentService.saveContent(content);
        return "redirect:/admin/contents";
    }
    @GetMapping("/updateContent/{id}")
    public String updateContent(@PathVariable(value = "id") long id, Model model){
        Contents contents = contentService.getContentById(id);
        model.addAttribute("content", contents);
        return "admin/content/updateContent";
    }
    @GetMapping("/deleteContent/{id}")
    public String deleteCategory(@PathVariable(value = "id") long id){
        contentService.deleteContent(id);
        return "redirect:/admin/contents";
    }

    @PostMapping("/saveContent")
    public String saveCategory(@ModelAttribute("content") Contents contents) {
        contentService.saveContent(contents);
        return "redirect:/admin/contents";
    }

}
