package com.robben.agg.javabase.securityManager;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class PolicyTest {

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("java.lang.Object");
            Object o = clazz.newInstance();
            Method m = o.getClass().getDeclaredMethod("toString");
            m.invoke(o);
            System.out.println("reflect access ok");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        try {
            String userdir = System.getProperty("user.home");
            System.out.println("property read ok");
            // File f = new File(userdir + "/conf/x.properties");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        try {
            ServerSocket s = new ServerSocket(8889);
            System.out.println("create socket server ok");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        try {
            Socket s2 = new Socket("localhost", 8889);
            s2.close();
            System.out.println("create socket client ok");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        File file = new File("C:\\Users\\aaa\\Desktop");
        try {
            read(file);
            System.out.println("file read ok");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

        try {
            write(file);
            System.out.println("file write ok");
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    private static void read(File file) throws Throwable {
        InputStream in = null;
        BufferedReader reader = null;
        try {
            in = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(in));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                // System.out.println("read-->" + temp);
            }
        } catch (Throwable e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void write(File file) throws Throwable {
        FileWriter fw = new FileWriter(file);
        for (int i = 0; i < 10; i++) {
            String temp = new java.util.Date() + " " + new java.util.Random().nextLong();
            // System.out.println("write-->" + temp);
            fw.write(temp + "\r\n");
        }
        fw.flush();
        fw.close();
    }
}
