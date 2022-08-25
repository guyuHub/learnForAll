package com.example.learnForAll.tableapi.tableapi;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guyu
 * @desc
 * @date 2022/8/25-3:35 下午
 **/
@Data
public class QueryResult {
    //商品名称
    private String goodsName;
    //商品价格
    private Integer priceSum;
}
