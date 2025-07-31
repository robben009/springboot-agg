# proto文件生成javabean

```angular2html
protoc --java_out=src/main/java src/main/proto/GameMsg.proto
```

其中的GameMsg.proto写明了包名和生成的类名,所以out输出的路径为java源路径即可，改命令需要在
/Users/hjz/openProjects/springboot-agg/nettyDemo/game 文件夹下去执行