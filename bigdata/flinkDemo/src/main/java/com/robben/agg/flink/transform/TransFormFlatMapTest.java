package com.robben.agg.flink.transform;

import com.robben.agg.flink.source.MyEvent;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.ArrayList;

//扁平映射
public class TransFormFlatMapTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从集合中读取数据(也可以放对象)
        ArrayList<MyEvent> list = new ArrayList<MyEvent>(){{
            this.add(new MyEvent("a","b",1L));
            this.add(new MyEvent("a2","b2",2L));
        }};
        DataStreamSource<MyEvent> ds = env.fromCollection(list);

        SingleOutputStreamOperator<String> result = ds.flatMap(new MyFlatMap());
        SingleOutputStreamOperator<String> result2 = ds.flatMap((MyEvent a, Collector<String> b) -> {
            if (a.user.equals("a")) {
                b.collect(a.url);
            } else if (a.user.equals("a2")) {
                b.collect(a.user);
                b.collect(a.url);
            }
        }).returns(Types.STRING);
        //还可以返回更加复杂的类型
//        }).returns(new TypeHint<String>() {});


        //fff 是sink的定义
        result2.print("fff");

        env.execute();

    }


    private static class MyFlatMap implements FlatMapFunction<MyEvent,String> {
        @Override
        public void flatMap(MyEvent myEvent, Collector<String> out) throws Exception {
            out.collect(myEvent.user);
            out.collect(myEvent.timestamp.toString());
        }
    }


}
