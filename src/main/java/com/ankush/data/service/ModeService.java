package com.ankush.data.service;

import com.ankush.data.repository.ModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeService {
    @Autowired
    private ModeRepository repository;

}
