package com.example.learnForAll.tableapi.tableapi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author guyu
 * @desc:
 * @date 2022/8/26 0:16
 */
@Data
public class CtData {
    //用户
    public String userName;
    //点击时间
    private Timestamp clickTime;
    //点击url
    private Integer clickNum;
}
