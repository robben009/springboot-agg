package com.robben.agg.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 有界流处理
 */

public class BoundedStreamWordCount {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> ds = env.readTextFile("flink/input/words.txt");
        //也可以使用socket读取
        DataStreamSource<String> ds2 = env.socketTextStream("hostname", 7777);

        SingleOutputStreamOperator<Tuple2<String, Long>> wordAndOneTuple = ds.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            String[] words = line.split(",");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));


        KeyedStream<Tuple2<String, Long>, String> group = wordAndOneTuple.keyBy(data -> data.f0);
        SingleOutputStreamOperator<Tuple2<String, Long>> sum = group.sum(1);
        sum.print();

        //启动执行
        env.execute();
    }

}
