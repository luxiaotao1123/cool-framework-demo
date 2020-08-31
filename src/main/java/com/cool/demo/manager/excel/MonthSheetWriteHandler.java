package com.cool.demo.manager.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Created by vincent on 2020/8/31
 */
public class MonthSheetWriteHandler implements SheetWriteHandler {
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);

        // 第一行
        Row row1 = sheet.createRow(0);
        row1.setHeight((short) 500);
        Cell cell = row1.createCell(0);
        cell.setCellValue("平湖市小太阳童车有限公司");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 0, 0, 7));
        Cell cell1 = row1.createCell(8);
        cell1.setCellValue("承认");
        Cell cell2 = row1.createCell(9);
        cell2.setCellValue("审核");
        Cell cell3 = row1.createCell(10);
        cell3.setCellValue("担当");

        // 第二行
        Row row2 = sheet.createRow(1);
        row2.setHeight((short) 700);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("采购订单");
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cell21.setCellStyle(cellStyle1);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(1, 1, 0, 7));

        // 第三行
        Row row3 = sheet.createRow(2);
        row3.setHeight((short) 500);
        row3.createCell(0).setCellValue("供应商：");
        row3.createCell(4).setCellValue("交货日期");
        row3.createCell(7).setCellValue("订单编号");

    }
}
