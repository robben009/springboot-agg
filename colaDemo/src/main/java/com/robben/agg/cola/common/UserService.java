package com.robben.agg.cola.common;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String updateName(String name){
        return name + "123123";
    }


}
