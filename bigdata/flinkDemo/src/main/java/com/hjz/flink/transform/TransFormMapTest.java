package com.hjz.flink.transform;

import com.hjz.flink.source.MyEvent;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

public class TransFormMapTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从集合中读取数据(也可以放对象)
        ArrayList<MyEvent> list = new ArrayList<MyEvent>(){{
            this.add(new MyEvent("a","b",1L));
            this.add(new MyEvent("a2","b2",2L));
        }};
        DataStreamSource<MyEvent> ds = env.fromCollection(list);

        //进行转换计算,提取user字段
        //1:使用自定义类
        SingleOutputStreamOperator<String> result = ds.map(new MyMapper());

        //2:使用匿名类,直接new一个MapFunction
        SingleOutputStreamOperator<String> result2 = ds.map(new MapFunction<MyEvent, String>() {
            @Override
            public String map(MyEvent myEvent) throws Exception {
                return myEvent.user;
            }
        });

        //3:使用lambda表达式
        SingleOutputStreamOperator<String> result3 = ds.map(data -> data.user);


        result.print();

        env.execute();

    }


    //自定义mapFunction
    public static class MyMapper implements MapFunction<MyEvent,String> {

        @Override
        public String map(MyEvent myEvent) throws Exception {
            return myEvent.user;
        }
    }


}
