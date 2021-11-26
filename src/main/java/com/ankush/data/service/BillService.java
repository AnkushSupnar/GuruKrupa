package com.ankush.data.service;

import com.ankush.data.entities.Bill;
import com.ankush.data.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository repository;

    public Long getNewBillNo()
    {
        if(repository.getNewBillNo()==null)
            return Long.valueOf(1);

        return repository.getNewBillNo();
    }
    public int saveBill(Bill bill)
    {
        if(bill.getId()==null)
        {
            repository.save(bill);
            return 1;
        }
        else {
            repository.save(bill);
            return 2;
        }
    }
}
