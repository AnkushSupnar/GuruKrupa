package com.ankush.data.repository;

import com.ankush.data.entities.RawMetalTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RowMetalTransaction extends JpaRepository<RawMetalTransaction,Integer> {
    
}
