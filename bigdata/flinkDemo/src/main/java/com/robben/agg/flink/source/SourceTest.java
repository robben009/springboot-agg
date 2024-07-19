package com.robben.agg.flink.source;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.ArrayList;
import java.util.Properties;

public class SourceTest {
    public static void main(String[] args) throws Exception{
        //1:创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //从文件中读取数据
        DataStreamSource<String> ds = env.readTextFile("flink/input/clicks.txt");

        //从集合中读取数据(也可以放对象)
        ArrayList<Integer> list = new ArrayList<Integer>(){{
            this.add(1);
            this.add(2);
            this.add(3);
        }};
        DataStreamSource<Integer> ds2 = env.fromCollection(list);

        //直接取元素信息
        DataStreamSource<String> ds4 = env.fromElements("adf");

        //也可以使用socket读取
        DataStreamSource<String> ds5 = env.socketTextStream("hostname", 7777);

        //通用的数据源--kafka
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "212.64.18.166:9092");
        properties.setProperty("group.id", "consumer-group");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");

        DataStreamSource<String> ds6 = env.addSource(new FlinkKafkaConsumer<>("aaa", new SimpleStringSchema(), properties));

        //自定义source---可以把ClickSource单独抽出作为一个类文件
        DataStreamSource<MyEvent> ds7 = env.addSource(new ClickSource());

        //自定义source2---可以设置并行度
        DataStreamSource<Integer> ds8 = env.addSource(new MyParallelSource()).setParallelism(2);

        ds7.print();
        env.execute();
    }


    public static class ClickSource implements SourceFunction<MyEvent> {
        private Boolean flag = true;

        @Override
        public void run(SourceContext ctx) throws Exception {
            while (flag) {
                String user = System.currentTimeMillis() + "user";
                String url = System.currentTimeMillis() + "url";
                Long time = System.currentTimeMillis();

                ctx.collect(new MyEvent(user,url,time));
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            flag = false;
        }

    }


    private static class MyParallelSource implements ParallelSourceFunction<Integer>{
        private Boolean flag = true;
        @Override
        public void run(SourceContext ctx) throws Exception {
            while (flag) {
                ctx.collect(1);
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            flag = false;
        }
    }


}
