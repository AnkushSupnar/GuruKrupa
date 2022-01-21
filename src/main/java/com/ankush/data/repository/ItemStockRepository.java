package com.ankush.data.repository;
import java.util.List;
import com.ankush.data.entities.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {
    List<ItemStock> findByOrderByIdAsc();

    List<ItemStock> findByItem_Itemname(String itemname);

    List<ItemStock> findByItem_Hsn(Long hsn);

    List<ItemStock> findByItem_HsnAndItem_Itemname(Long hsn, String itemname);

    List<String> findByItem_ItemnameOrderByItem_ItemnameAsc(String itemname);

    ItemStock findByItem_id(Long id);












}