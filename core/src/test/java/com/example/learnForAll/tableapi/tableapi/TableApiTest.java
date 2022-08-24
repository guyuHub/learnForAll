package com.example.learnForAll.tableapi.tableapi;

import com.ibm.icu.text.PluralRules;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

import static com.ibm.icu.text.PluralRules.Operand.w;
import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.lit;

/**
 * @author guyu
 * @desc
 * @date 2022/8/23-5:15 下午
 **/
public class TableApiTest {

    @Test
    public  void test() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //创建执行环境
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        senv.setParallelism(1);
        //数据流获取
        DataStreamSource<String> inputDS = senv.socketTextStream("localhost", 9999);
        //设置WatermarkStrategy
        WatermarkStrategy<GoodsData> wmStrategy =
                WatermarkStrategy
                        .<GoodsData>forBoundedOutOfOrderness(Duration.ofSeconds(10))
                        .withTimestampAssigner((event, timestamp) -> event.getBuyTime().getTime());
        //数据流处理
        SingleOutputStreamOperator<GoodsData> sensorDS = inputDS
                .map((MapFunction<String, GoodsData>) value -> {
                    String[] datas = value.split(",");
                    GoodsData goods = new GoodsData();
                    goods.setGoodsId(Long.parseLong(datas[0]));
                    goods.setGoodsName(datas[1]);
                    goods.setPrice(Integer.parseInt(datas[2]));
                    goods.setBuyTime(new Timestamp(simpleDateFormat.parse(datas[3]).getTime()));
                    return goods;
                })
                .assignTimestampsAndWatermarks(wmStrategy);

        //todo 设置表环境
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(senv);
        //todo 把流转换成动态表
        Table sensorTable = tableEnv.fromDataStream(sensorDS, $("goodsId"), $("goodsName"), $("price"), $("buyTime").rowtime());

        System.out.println(sensorTable.getSchema());

        System.out.println("-----------------------");

        //todo 使用TableAPI对动态表操作
        Table resultTable = sensorTable
                .window(Tumble.over(lit(5).second()).on($("buyTime")).as("w"))
                .groupBy($("w"), $("goodsName"))
                .select($("goodsName"), $("price").sum().as("priceSum"));

        //4.将动态表转换成流
        // 注意流的选择。撤回流，加上标记位  toRetractStream
        // DataStream<Tuple2<Boolean, Row>> resultDS = tableEnv.toRetractStream(resultTable, Row.class);
        DataStream<Row> resultDS = tableEnv.toDataStream(resultTable, Row.class);

        resultDS.print();
        senv.execute();
    }


}
