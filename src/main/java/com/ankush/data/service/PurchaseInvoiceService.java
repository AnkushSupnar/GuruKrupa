package com.ankush.data.service;

import java.time.LocalDate;
import java.util.List;

import com.ankush.data.entities.PurchaseInvoice;
import com.ankush.data.repository.PurchaseInvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PurchaseInvoiceService {
    @Autowired
    private PurchaseInvoiceRepository repository;

    public List<PurchaseInvoice>getAllInvoice(){return repository.findAll();}
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
    public List<PurchaseInvoice>getByDate(LocalDate date){return repository.findByDate(date);}
    public List<PurchaseInvoice>getByInvoiceno(String invoiceno){return repository.findByInvoiceno(invoiceno);}
    public PurchaseInvoice getById(Long id){return repository.findById(id).orElse(null);}
}
