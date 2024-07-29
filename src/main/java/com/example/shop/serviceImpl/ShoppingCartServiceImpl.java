package com.example.shop.serviceImpl;

import com.example.shop.model.CartItem;
import com.example.shop.model.Product;
import com.example.shop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    Map<Integer, CartItem> maps = new HashMap<>();
    @Override
    public void add(CartItem item){
        CartItem cartItem = maps.get(item.getProductId());
        if(cartItem == null){
            maps.put((int) item.getProductId(), item);
        } else {
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }

    }
    @Override
    public void remove(int id){
        maps.remove(id);
    }
    @Override
    public CartItem update (int proID,int quantity){
        CartItem cartItem = maps.get(proID);
        cartItem.setQuantity(quantity);
        return cartItem;
    }

    @Override
    public void clear(){
        maps.clear();
    }
    @Override
    public Collection<CartItem> getAllItems(){
        return maps.values();
    }
    @Override
    public int getCount(){
        return maps.values().size();
    }
    @Override
    public double getAmount(){
        return maps.values().stream()
                .mapToDouble(item-> item.getQuantity()*item.getPrice())
                .sum();
    }
    @Override
    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        for (CartItem cartItem : maps.values()) {
            Product product = convertCartItemToProduct(cartItem);
            productList.add(product);
        }
        return productList;
    }
    private Product convertCartItemToProduct(CartItem cartItem) {
        Product product = new Product();
        product.setId(cartItem.getProductId());
        product.setName(cartItem.getName());
        product.setPrice(cartItem.getPrice());
        product.setQuantity(String.valueOf(cartItem.getQuantity()));
        return product;
    }
}
