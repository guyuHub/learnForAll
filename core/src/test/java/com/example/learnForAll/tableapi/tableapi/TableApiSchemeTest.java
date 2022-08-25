package com.example.learnForAll.tableapi.tableapi;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.AbstractDataType;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;

import static org.apache.flink.table.api.Expressions.$;
import static org.apache.flink.table.api.Expressions.lit;

/**
 * @author guyu
 * @desc
 * @date 2022/8/25-3:46 下午
 **/
public class TableApiSchemeTest {
    @Test
    public void test() throws Exception {

        //todo 创建执行环境
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置task并行度1
        senv.setParallelism(1);

        //todo 数据流获取
        DataStreamSource<String> inputDS = senv.socketTextStream("localhost", 9999);

        //todo 数据流处理，解析socket传入的流数据转为相应pojo
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                ;

        //todo 设置表环境
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(senv);
        //todo 把流转换成动态表
        DataType timeStamp3 = DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class);
        Table sensorTable = tableEnv.fromDataStream(sensorDS, Schema.newBuilder()
                .column("buyTime", timeStamp3)
                .column("goodsId", "BIGINT")
                .column("goodsName", "STRING")
                .column("price", "INT")
                .watermark("buyTime", "buyTime - INTERVAL '10' HOUR")
                .build());

        //todo 输出表scheme
        System.out.println("------------------------------");
        System.out.println(sensorTable.getSchema());

        //todo 使用TableAPI对动态表操作
        Table resultTable = sensorTable
                .window(Tumble.over(lit(10).hour()).on($("buyTime")).as("w"))
                .groupBy($("w"), $("goodsName"))
                .select($("goodsName"), $("price").count().as("priceSum"));

        //todo 将动态表转换成流
        DataStream<Row> resultDS = tableEnv.toDataStream(resultTable);
        //todo 打印
        resultDS.print();
        //todo 执行
        senv.execute();
    }

}
