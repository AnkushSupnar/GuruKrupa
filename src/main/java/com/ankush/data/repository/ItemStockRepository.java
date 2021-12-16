package com.ankush.data.repository;

import com.ankush.data.entities.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {
    List<ItemStock> findByOrderByIdAsc();

    List<ItemStock> findByItem_Itemname(String itemname);

    List<ItemStock> findByItem_Hsn(Long hsn);

    List<ItemStock> findByItem_HsnAndItem_Itemname(Long hsn, String itemname);






}