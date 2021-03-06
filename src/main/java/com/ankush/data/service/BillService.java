package com.ankush.data.service;

import com.ankush.data.entities.Bill;
import com.ankush.data.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public List<Bill>getAllBills(){
        return repository.findAll();
    }
    public List<Bill> getBillByDate(LocalDate date){
        return repository.getByDate(date);
    }
    public Bill getBillByBillno(String billno){
        return repository.getByBillno(billno);
    }
    public List<Bill>getBillByCustomer(Long customerid){
        return repository.getByCustomerId(customerid);
    }
    public Bill getById(Long id)
    {
        return repository.getById(id);
    }

}
