package com.ankush.data.service;

import com.ankush.data.entities.PurchaseParty;
import com.ankush.data.repository.PurchasePartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collections;
import java.util.List;

@Service
public class PurchasePartyService {
    @Autowired
    private PurchasePartyRepository repository;
    public PurchaseParty getPartyById(int id){return repository.getById(id);}
    public List<PurchaseParty>getAllPurchaseParty(){
      return repository.findAll();
    }
    public PurchaseParty getPartyByName(String name){
        return repository.getByName(name);
    }
    public int savePurchaseParty(PurchaseParty party)
    {
        if(party.getId()==null)
        {
            repository.save(party);
            return 1;
        }
        else{
            repository.save(party);
            return 2;
        }
    }
}
