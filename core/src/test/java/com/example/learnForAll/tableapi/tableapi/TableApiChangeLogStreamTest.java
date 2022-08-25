package com.example.learnForAll.tableapi.tableapi;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.lit;

/**
 * @author guyu
 * @desc
 * @date 2022/8/25-3:46 下午
 **/
public class TableApiChangeLogStreamTest {
    @Test
    public void test() throws Exception {

        //todo 创建执行环境
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置task并行度1
        senv.setParallelism(1);

        //todo 数据流获取
        DataStreamSource<Row> inputDS = senv.fromElements(
                Row.ofKind(RowKind.INSERT,"HuangZhen","2k"),
                Row.ofKind(RowKind.INSERT,"ZhangZhen","4k"),
                Row.ofKind(RowKind.INSERT,"LiuZhen","3k"),
                Row.ofKind(RowKind.UPDATE_BEFORE,"HuangZhen","2k"),
                Row.ofKind(RowKind.UPDATE_AFTER,"HuangZhen","6k"),
                Row.ofKind(RowKind.DELETE,"LiuZhen","2k")
        );

        //todo 数据流处理，解析socket传入的流数据转为相应pojo

                ;

        //todo 设置表环境
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(senv);
        //todo 把流转换成动态表
        DataType timeStamp3 = DataTypes.TIMESTAMP(3).bridgedTo(Timestamp.class);
        Table sensorTable = tableEnv.fromChangelogStream(inputDS, Schema.newBuilder()
                .column("f0", "STRING")
                .column("f1", "STRING")
                .build());

        //todo 输出表scheme
        System.out.println("------------------------------");
        System.out.println(sensorTable.getSchema());

        //todo 使用TableAPI对动态表操作
        Table resultTable = sensorTable
                .select($("f0").as("name"), $("f1").as("salary"));

        //todo 将动态表转换成流
        DataStream<Row> resultDS = tableEnv.toChangelogStream(resultTable);
        //todo 打印
        resultDS.print();
        //todo 执行
        senv.execute();
    }

}
