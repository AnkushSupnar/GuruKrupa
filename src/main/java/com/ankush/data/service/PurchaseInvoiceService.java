package com.ankush.data.service;

import com.ankush.data.entities.PurchaseInvoice;
import com.ankush.data.repository.PurchaseInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseInvoiceService {
    @Autowired
    private PurchaseInvoiceRepository repository;

    public int save(PurchaseInvoice invoice)
    {
        if(invoice.getId()==null)
        {
            repository.save(invoice);
            return 1;
        }
        else{
            repository.save(invoice);
            return 2;
        }
    }
}
