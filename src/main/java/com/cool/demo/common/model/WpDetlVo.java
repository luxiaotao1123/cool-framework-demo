package com.cool.demo.common.model;

import lombok.Data;

/**
 * Created by vincent on 2020/7/20
 */
@Data
public class WpDetlVo {

    private Long wpDetlId;

    private Long memberId;

    private Integer idx;

    private String date;

    private Integer type;

    private String title;

    private String content;

    private String status;

}
