package com.ankush.data.repository;

import com.ankush.data.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("select max(id)+1 from Bill")
    Long getNewBillNo();
}