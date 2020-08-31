package com.cool.demo.manager.excel;

import com.alibaba.excel.write.handler.WriteHandler;
import org.apache.poi.ss.usermodel.*;

/**
 * Created by vincent on 2020/8/31
 */
public class PointWriterHandler implements WriteHandler {



    public void cell(int i, Cell cell) {
        Sheet sheet = cell.getSheet();
        Workbook workbook = sheet.getWorkbook();
        //获取点位sheet
        Sheet sheet1 = workbook.getSheet("点位");
        if (sheet1 != null) {
            //对第一行设置样式
            if (cell.getRowIndex() == 0) {
                cell.setCellStyle(getCellStyle(cell));
            }
        }
    }

    private CellStyle getCellStyle(Cell cell) {
        //设置样式
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        //自动换行
        cellStyle.setWrapText(false);
        // 水平对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        //字体大小
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        return cellStyle;
    }

}
