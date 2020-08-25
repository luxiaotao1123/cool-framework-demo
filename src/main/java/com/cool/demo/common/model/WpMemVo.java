package com.cool.demo.common.model;

import lombok.Data;

import java.util.List;

/**
 * Created by vincent on 2020/7/20
 */
@Data
public class WpMemVo {

    private Long wpId;

    private String wpDesc;

    private String leader;

    private String startTime;

    private String endTime;

    private List<WpMemDto> dtoList;

    @Data
    public static class WpMemDto {

        private Long wpId;

        private Long memberId;

        private String memberName;

        private Short status;

        private String status$;

        private String dept;

        // 权限：1-编辑  2-查看
        private Integer limit;

    }

}
