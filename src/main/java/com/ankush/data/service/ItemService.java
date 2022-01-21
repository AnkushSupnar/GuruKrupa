package com.ankush.data.service;

import com.ankush.data.entities.Item;
import com.ankush.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    public ItemRepository repository;
    public Item getById(Long id){return repository.getById(id);}
    public List<Item>getAllItems(){return repository.findAll();}
    public List<String>getAllItemNames(){return repository.findAllItemNames();}
    public Item getItemByHsnNameMetalPurity(Long hsn,String name,String metal,String purity)
    {
        return repository.findByHsnAndItemnameAndMetalAndPurity(hsn,name,metal,purity);
    }
    public int save(Item item)
    {
        Item oldItem = getItemByHsnNameMetalPurity(item.getHsn(), item.getItemname(), item.getMetal(), item.getPurity());
        if(null==oldItem)
        {
            repository.save(item);
            return 1;
        }
        else{
            item.setId(oldItem.getId());
            repository.save(item);
            System.out.println("From Service = >"+item.getId());
            return 2;
        }
    }

}
