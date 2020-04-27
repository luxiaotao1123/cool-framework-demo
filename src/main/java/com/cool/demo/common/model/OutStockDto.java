package com.cool.demo.common.model;

import com.cool.demo.manager.entity.outStockDto;

import java.util.List;

public class OutStockDto {
    private int index = 1; //当前页面页码

    private int size = 1; // 总野鼠

    private List<outStockDto> stockDtos;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<outStockDto> getStockDtos() {
        return stockDtos;
    }

    public void setStockDtos(List<outStockDto> stockDtos) {
        this.stockDtos = stockDtos;
    }
}
