package com.cool.demo.common.model;

import lombok.Data;

import java.util.List;

/**
 * Created by vincent on 2020/7/20
 */
@Data
public class WpVo {

    private Long wpId;

    private Integer year;

    private Integer month;

    private Integer weekIdx;

    private List<Members> members;

    private Short status;

    private String status$;

    private String startTime;

    private String endTime;

    @Data
    public static class Members {

        private String memberName;

        private Short status;

    }

}
