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

}
