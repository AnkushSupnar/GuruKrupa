package com.ankush.data.repository;

import com.ankush.data.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemname(String itemname);

    Item findByItemnameAndMetal(String itemname, String metal);

    @Query("select itemname from Item order by itemname")
    List<String>findAllItemNames();
    
    Item findByHsnAndItemnameAndMetalAndPurity(Long hsn,String itemname,String metal,String purity);



}