package com.robben.agg.flink.transform;

import com.robben.agg.flink.source.MyEvent;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

public class TransFormFilterTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //从集合中读取数据(也可以放对象)
        ArrayList<MyEvent> list = new ArrayList<MyEvent>(){{
            this.add(new MyEvent("a","b",1L));
            this.add(new MyEvent("a2","b2",2L));
        }};
        DataStreamSource<MyEvent> ds = env.fromCollection(list);

        SingleOutputStreamOperator<MyEvent> result = ds.filter(new Myfilter());

        SingleOutputStreamOperator<MyEvent> result2 = ds.filter(a -> a.user.equals("a") ? true : false);

        result2.print();
        env.execute();

    }


    //自定义filter
    private static class Myfilter implements FilterFunction<MyEvent> {
        @Override
        public boolean filter(MyEvent myEvent) {
            if(myEvent.user.equals("a")){
                return true;
            }
            return false;
        }
    }
}
