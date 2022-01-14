package com.ankush.data.repository;

import com.ankush.data.entities.RawMetal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RawMetalRepository extends JpaRepository<RawMetal,Integer> {
 
    RawMetal findByMetalAndPurity(String metal,String purity);
}
