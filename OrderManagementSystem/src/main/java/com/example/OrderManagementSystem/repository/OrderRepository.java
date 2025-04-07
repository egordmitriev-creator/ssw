package com.example.OrderManagementSystem.repository;

import com.example.OrderManagementSystem.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN o.customer c " +
            "LEFT JOIN o.payment p " +
            "WHERE (:#{#street == null} = true OR c.address.street LIKE %:#{#street}%) " +
            "AND (:#{#city == null} = true OR c.address.city LIKE %:#{#city}%) " +
            "AND (:#{#zipcode == null} = true OR c.address.zipcode = :zipcode) " +
            "AND (:#{#startDate == null} = true OR o.date >= :startDate) " +
            "AND (:#{#endDate == null} = true OR o.date <= :endDate) " +
            "AND (:#{#paymentType == null} = true OR TYPE(p) = :paymentType) " +
            "AND (:#{#customerName == null} = true OR LOWER(c.name) LIKE LOWER(CONCAT('%', :customerName, '%'))) " +
            "AND (:#{#orderStatus == null} = true OR o.status = :orderStatus)")
    List<Order> findOrdersByCriteria(
            @Param("street") String street,
            @Param("city") String city,
            @Param("zipcode") String zipcode,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("paymentType") Class<?> paymentType,
            @Param("customerName") String customerName,
            @Param("orderStatus") String orderStatus);
}