package com.ankush.data.service;

import com.ankush.data.entities.Bank;
import com.ankush.data.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    @Autowired
    BankRepository repository;

    public Bank getById(int id)
    {
        return repository.getById(id);
    }
    public List<Bank>getAllBank()
    {
        return repository.findAll();
    }
    public int save(Bank bank)
    {
        if(bank.getId()==null){
            repository.save(bank);
            return 1;
        }
        else
        {
            repository.save(bank);
            return 2;
        }
    }
}
