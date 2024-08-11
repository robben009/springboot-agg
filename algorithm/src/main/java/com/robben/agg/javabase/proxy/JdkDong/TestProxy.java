package com.robben.agg.javabase.proxy.JdkDong;

// jdk的动态代理  https://blog.csdn.net/flyfeifei66/article/details/81481222
public class TestProxy {
    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件
        //生成在项目目录下的classpath:com\sun\proxy下,或者可以下载个Everything，搜Proxy0就看到了
//        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        Star ldh = new LiuDeHua();

        StarProxy proxy = new StarProxy();
        proxy.setTarget(ldh);

        Object obj = proxy.CreatProxyedObj();
        Star star = (Star)obj;

        System.out.println(star.dance("lalal"));

    }
}
