package com.example.shop.respository;

import com.example.shop.model.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Contents,Long> {
}
