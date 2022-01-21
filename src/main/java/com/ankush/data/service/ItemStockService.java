package com.ankush.data.service;

import com.ankush.data.entities.ItemStock;
import com.ankush.data.repository.ItemStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemStockService {
    @Autowired
    private ItemStockRepository repository;

   public List<ItemStock> getAllItemStock(){
        return repository.findByOrderByIdAsc();
    }

    public List<ItemStock> findByItem_Itemname(String itemname){
        return repository.findByItem_Itemname(itemname);
    }

    public List<ItemStock> findByItem_Hsn(Long hsn){
    return repository.findByItem_Hsn(hsn);
    }
    public List<ItemStock>findByHsnAndItemname(Long hsn,String name)
    {
        return  repository.findByItem_HsnAndItem_Itemname(hsn,name);
    }
    public List<String>getItemNames()
    {
        return repository.findAll().stream().map(e->e.getItem().getItemname()).collect(Collectors.toList());
    }
    public List<String>getItemNamesByHsn(Long hsn)
    {
      return repository.findByItem_Hsn(hsn).stream().map(e->e.getItem().getItemname()).collect(Collectors.toList());
    }
    public ItemStock getItemStockByItemId(Long id)
    {
        return repository.findByItem_id(id);
    }
    public int saveItemStock(ItemStock stock)    
    {
        ItemStock oldStock = getItemStockByItemId(stock.getItem().getId());
        if(oldStock==null)
        {
            repository.save(stock);
            return 1;
        }
        else{
            oldStock.setPurchaserate(stock.getPurchaserate());
            oldStock.setQuantity(oldStock.getQuantity()+stock.getQuantity());
            oldStock.setWeight(oldStock.getWeight()+stock.getWeight());
            repository.save(oldStock);
            return 2;
        }
    }
}
