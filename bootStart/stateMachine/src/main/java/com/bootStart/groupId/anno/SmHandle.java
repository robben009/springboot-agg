package com.bootStart.groupId.anno;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SmHandle {

    /**
     *执行的业务key
     *
     * @return String
     */
    String key();

}
