package com.example.learnForAll.tableapi.tableapi;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author guyu
 * @desc:
 * @date 2022/8/24 0:09
 */
@Data
public class GoodsData implements Serializable {
    //商品id
    private Long goodsId;
    //商品名称
    private String goodsName;
    //商品价格
    private Integer price;
    //购买时间
    private Date buyTime;

}
