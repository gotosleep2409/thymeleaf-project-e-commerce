package com.example.shop.serviceImpl;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.model.Delivery;
import com.example.shop.respository.DeliveryRepository;
import com.example.shop.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    public DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }
    @Override
    public Delivery getDeliveryById(long id) {
        return deliveryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Delivery","Id",id));
    }

    @Override
    public List<Delivery> getAllDelivery() {
        return deliveryRepository.findAll();
    }
}
