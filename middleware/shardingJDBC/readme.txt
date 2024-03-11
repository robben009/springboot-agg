目前启动是报错的
需要把springboot的版本降到3.0.3,然后看情况引入依赖snakeyaml

目前没法接口nacos获取配置,只能通过SPI机制实现ShardingSphereURLProvider接口来获取nacos的配置
参考链接 https://blog.csdn.net/qq_19677511/article/details/135887921
https://blog.csdn.net/xsgnzb/article/details/133642928