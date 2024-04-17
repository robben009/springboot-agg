package com.hjz.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class BatchWordCount {

    public static void main(String[] args) throws Exception {
        //1:创建环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //2:从文件中读取数据
        DataSource<String> ds = env.readTextFile("flink/input/words.txt");

        //3:将每行数据分词换成二元组类型
        FlatMapOperator<String, Tuple2<String, Long>> wordAndOneTuple = ds.flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
            String[] words = line.split(",");
            for (String word : words) {
                out.collect(Tuple2.of(word, 1L));
            }
        }).returns(Types.TUPLE(Types.STRING, Types.LONG));

        //4:按照单词分组  0是索引
        UnsortedGrouping<Tuple2<String, Long>> wordAndOneTupleGroup = wordAndOneTuple.groupBy(0);

        //5:分组内聚合 1也是索引
        AggregateOperator<Tuple2<String, Long>> sum = wordAndOneTupleGroup.sum(1);
        sum.print();

    }

}




