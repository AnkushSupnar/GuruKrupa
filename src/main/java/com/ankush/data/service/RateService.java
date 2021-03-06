package com.ankush.data.service;

import com.ankush.data.entities.Rate;
import com.ankush.data.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class RateService {
    @Autowired
    private RateRepository repository;

    public List<Rate> getRateByDate(LocalDate date)
    {
        return repository.getByDate(date);
    }
    public List<Rate>getAllRates()
    {
        return repository.findAll();
    }
    public Rate getRateByDateAndPurity(LocalDate date,String purity)
    {
        return repository.getByDateAndPurity(date,purity);
    }
    public Rate getByDateAndMetal(LocalDate date, String metal) {
        return repository.getByDateAndMetal(date,metal);
    }
    public Rate getByDateAndMetalAndPurity(LocalDate date,String metal,String purity)
    {
        return repository.getByDateAndMetalAndPurity(date,metal.toLowerCase(),purity.toLowerCase());
    }
    public int save(Rate rate)
    {
        Rate r = getByDateAndMetalAndPurity(rate.getDate(),rate.getMetal(),rate.getPurity());
        if(null!=r)
            rate.setId(r.getId());

        repository.save(rate);
        return 1;
    }
    public List<String>getPurityNames()
    {
        return repository.getPurityNames();
    }
    public List<String>getMetalNames()
    {
        return repository.getMetalNames();
    }
    public Rate getTodayRate(LocalDate date,String metal,String purity){
        if(repository.getTodayRate(date,metal,purity)==null)
            return Rate.builder()
                    .metal(metal)
                    .date(date)
                    .rate(0.0f)
                    .purity(purity)
                    .build();
        
        return repository.getTodayRate(date,metal,purity);
    }



}
