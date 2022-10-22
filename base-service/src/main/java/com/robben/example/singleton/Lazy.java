package com.robben.example.singleton;

import java.io.*;

/**
 * Description： 有性能问题
 * Author: robben
 * Date: 2021/4/16 11:54
 */
public class Lazy implements Serializable {
    private Lazy() {
    }

    private static Lazy lazy;

    public static synchronized Lazy getLazy() {
        if(lazy == null){
            lazy = new Lazy();
        }
        return lazy;
    }

    //序列化也会破坏单例模式
    public static void main(String[] args) throws Exception {
        Lazy lazy = Lazy.getLazy();

        ObjectOutputStream lazy1 = new ObjectOutputStream(new FileOutputStream("lazy"));
        lazy1.writeObject(lazy);

        ObjectInputStream lazy2 = new ObjectInputStream(new FileInputStream("lazy"));
        //本质是在readObject中使用了反射
        Lazy o = (Lazy)lazy2.readObject();

        System.out.println(lazy == o);
    }

}
