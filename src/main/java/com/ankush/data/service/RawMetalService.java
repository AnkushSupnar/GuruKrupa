package com.ankush.data.service;

import java.util.List;

import com.ankush.data.entities.RawMetal;
import com.ankush.data.repository.RawMetalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RawMetalService {
    @Autowired
    private RawMetalRepository repository;

    public List<RawMetal> getAllRawMetals() {
        return repository.findAll();
    }

    public int saveRowMetal(RawMetal raw) {

        if(raw.getId()==null)
        {
            repository.save(raw);
            return 1;
        }
        else{
            raw.setWeight(repository.getById(raw.getId()).getWeight()+raw.getWeight());
            repository.save(raw);
            return 2;
        }
    }
    public RawMetal getByMetalAndPurity(String metal,String purity)
    {
        return repository.findByMetalAndPurit(metal, purity);
    }
    public int reduceWeight(RawMetal raw)
    {

        raw.setWeight(repository.getById(raw.getId()).getWeight()-raw.getWeight());
        repository.save(raw);
        return 1;
    }

}
