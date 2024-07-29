package com.example.shop.serviceImpl;

import com.example.shop.model.Contents;
import com.example.shop.respository.ContentRepository;
import com.example.shop.service.ContentService;
import org.springframework.stereotype.Service;
import com.example.shop.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    private ContentRepository contentRepository;

    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Contents saveContent(Contents content) {
        return contentRepository.save(content);
    }

    @Override
    public List<Contents> getAllContent() {
        return contentRepository.findAll();
    }

    @Override
    public Contents getContentById(long id) {
        return contentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Content","Id",id));
    }

    @Override
    public Contents updateContent(Contents content, long id) {
        return null;
    }

    @Override
    public void deleteContent(long id) {
        contentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Content","Id",id));
        contentRepository.deleteById(id);
    }
}
