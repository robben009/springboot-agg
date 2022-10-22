package com.robben.starter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/6/18 11:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloService {
    private String msg;
    private String name;

    public void say() {
        System.out.println(name + ":" + msg);
    }

}
