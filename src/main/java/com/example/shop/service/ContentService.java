package com.example.shop.service;

import com.example.shop.model.Category;
import com.example.shop.model.Contents;

import java.util.List;

public interface ContentService {
    Contents saveContent(Contents content);
    List<Contents> getAllContent();
    Contents getContentById(long id);
    Contents updateContent(Contents content, long id);
    void deleteContent(long id);
}
