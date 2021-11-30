package com.ankush.data.repository;


import com.ankush.data.entities.Mode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeRepository extends JpaRepository<Mode, Long> {

    @Query("select max(id)+1 from Mode")
    Long getNewModeNo();

    Mode getByModeno(String modeno);
}