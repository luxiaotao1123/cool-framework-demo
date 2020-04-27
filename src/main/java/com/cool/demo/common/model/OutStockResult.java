package com.cool.demo.common.model;

import com.cool.demo.manager.entity.outStockDto;

import java.util.ArrayList;
import java.util.List;

public class OutStockResult {

    private List<OutStockDto> outStockDtos =new ArrayList<OutStockDto>();

    public List<OutStockDto> getOutStockDtos() {
        return outStockDtos;
    }

    public void setOutStockDtos(List<OutStockDto> outStockDtos) {
        this.outStockDtos = outStockDtos;
    }
}
