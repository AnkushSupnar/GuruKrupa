package com.ankush.data.service;

import com.ankush.data.entities.ItemStock;
import com.ankush.data.repository.ItemStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemStockService {
    @Autowired
    private ItemStockRepository repository;

    List<ItemStock> getAllItemStock(){
        return repository.findByOrderByIdAsc();
    }

    List<ItemStock> findByItem_Itemname(String itemname){
        return repository.findByItem_Itemname(itemname);
    }

    List<ItemStock> findByItem_Hsn(Long hsn){
    return repository.findByItem_Hsn(hsn);
    }
    List<ItemStock>findByHsnAndItemname(Long hsn,String name)
    {
        return  repository.findByItem_HsnAndItem_Itemname(hsn,name);
    }
}
