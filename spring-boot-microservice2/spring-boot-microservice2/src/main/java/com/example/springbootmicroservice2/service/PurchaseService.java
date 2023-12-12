package com.example.springbootmicroservice2.service;

import com.example.springbootmicroservice2.model.Purchase;

import java.util.List;

public interface PurchaseService
{
    Purchase savePurchase(Purchase purchase);

    List<Purchase> findAllPurchasesOfUser(Long userId);
}
