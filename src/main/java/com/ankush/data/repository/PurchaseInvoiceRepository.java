package com.ankush.data.repository;

import java.time.LocalDate;
import java.util.List;

import com.ankush.data.entities.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Long> {

    List<PurchaseInvoice> findByDate(LocalDate date);
    List<PurchaseInvoice> findByInvoiceno(String invoiceno);
    
}