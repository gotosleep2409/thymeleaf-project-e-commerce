package com.example.shop.controller.admin;

import com.example.shop.StorageService;
import com.example.shop.model.News;
import com.example.shop.model.Product;
import com.example.shop.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/news")
public class NewsController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private NewsService newsService;
    @GetMapping("addNews")
    public String addNews(Model model){
        model.addAttribute("news",new News());
        return "admin/news/add";
    }
    @PostMapping("addNews")
    public String addNews(@ModelAttribute("News") News news, @RequestParam("imageNews")MultipartFile file, @RequestParam("detail") MultipartFile detail){
        this.storageService.store(file);
        String detailNews = detail.getName();
        LocalDate date = LocalDate.now();
        String fileName = file.getOriginalFilename();
        news.setImage(fileName);
        news.setDate(date);
        news.setDetail(detailNews);
        newsService.saveNews(news);
        return "redirect:/admin/news";
    }
}
