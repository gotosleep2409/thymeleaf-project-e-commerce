package com.example.shop.serviceImpl;

import com.example.shop.model.News;
import com.example.shop.respository.NewsRepository;
import com.example.shop.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News saveNews(News news) {
        return newsRepository.save(news);
    }
}
