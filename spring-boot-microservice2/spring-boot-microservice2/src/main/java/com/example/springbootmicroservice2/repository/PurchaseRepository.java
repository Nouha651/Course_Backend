package com.example.springbootmicroservice2.repository;

import com.example.springbootmicroservice2.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>
{
    //findBy+fieldName

    List<Purchase> findAllByUserId(Long userId);
}
