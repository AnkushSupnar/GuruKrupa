package com.ankush.data.service;

import com.ankush.data.entities.Mode;
import com.ankush.data.entities.ModeTransaction;
import com.ankush.data.entities.RawMetal;
import com.ankush.data.repository.ModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeService {
    @Autowired
    private ModeRepository repository;
    
    public Long getNewModeNo(){
        if(repository.getNewModeNo()==null)
            return Long.valueOf(1);
        return repository.getNewModeNo();
    }
    public int saveMode(Mode mode)
    {
        if(mode.getId()==null)
        {
            repository.save(mode);
           
            return 1;
        }else{
            repository.save(mode);
                   
            return 2;
        }
    }
    public Mode getModeByModeNo(String modeno){
        return repository.getByModeno(modeno);
    }
}
