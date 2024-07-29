package com.example.shop.respository;

import com.example.shop.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {
    /*void setOrderId(long id);*/
    @Query("SELECT u FROM OrderDetails u WHERE u.order.id= :orderId")
    List<OrderDetails> findOrdersByOrderId(@Param("orderId") Long orderId);
}
