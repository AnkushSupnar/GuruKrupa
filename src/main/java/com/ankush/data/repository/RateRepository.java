package com.ankush.data.repository;

import com.ankush.data.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> getByDate(LocalDate date);
    @Query("from Rate where date=:date and purity=:purity")
    Rate getByDateAndPurity(@Param("date")LocalDate date,@Param("purity")String purity);

    @Query("from Rate where date=:date and metal=:metal")
    Rate getByDateAndMetal(@Param("date")LocalDate date,@Param("metal")String metal);

    @Query("from Rate where date=:date and lower(metal)=:metal and lower(purity)=:purity")
    Rate getByDateAndMetalAndPurity(@Param("date")LocalDate date,@Param("metal")String metal,@Param("purity")String purity);
}