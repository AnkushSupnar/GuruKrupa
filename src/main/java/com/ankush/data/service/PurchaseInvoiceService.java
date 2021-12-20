package com.ankush.data.service;

import com.ankush.data.repository.PurchaseInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseInvoiceService {
    @Autowired
    private PurchaseInvoiceRepository repository;

}
