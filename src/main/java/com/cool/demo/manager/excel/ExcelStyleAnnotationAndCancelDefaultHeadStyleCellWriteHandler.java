package com.cool.demo.manager.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by vincent on 2020/8/31
 */
@Slf4j
public class ExcelStyleAnnotationAndCancelDefaultHeadStyleCellWriteHandler extends ExcelStyleAnnotationCellWriteHandler {


    private WriteCellStyle headWriteCellStyleSelf;

    private CellStyle headCellStyleSelf;
    private Class c;

    public ExcelStyleAnnotationAndCancelDefaultHeadStyleCellWriteHandler(Class c, WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        super(c,headWriteCellStyle, contentWriteCellStyle);
        this.headWriteCellStyleSelf = headWriteCellStyle;
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        Workbook workbook = cell.getSheet().getWorkbook();
        headCellStyleSelf = buildHeadCellStyle(workbook, headWriteCellStyleSelf);
        if (headCellStyleSelf == null) {
            return;
        }
        cell.setCellStyle(headCellStyleSelf);
    }

    /**
     * Build head cell style
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook, WriteCellStyle writeCellStyle) {
        CellStyle cellStyle = workbook.createCellStyle();
        if (writeCellStyle == null) {
            return cellStyle;
        }
        buildCellStyle(workbook, cellStyle, writeCellStyle);
        return cellStyle;
    }

    private static void buildCellStyle(Workbook workbook, CellStyle cellStyle, WriteCellStyle writeCellStyle) {
        buildFont(workbook, cellStyle, writeCellStyle.getWriteFont());
        if (writeCellStyle.getDataFormat() != null) {
            cellStyle.setDataFormat(writeCellStyle.getDataFormat());
        }
        if (writeCellStyle.getHidden() != null) {
            cellStyle.setHidden(writeCellStyle.getHidden());
        }
        if (writeCellStyle.getLocked() != null) {
            cellStyle.setLocked(writeCellStyle.getLocked());
        }
        if (writeCellStyle.getQuotePrefix() != null) {
            cellStyle.setQuotePrefixed(writeCellStyle.getQuotePrefix());
        }
        if (writeCellStyle.getHorizontalAlignment() != null) {
            cellStyle.setAlignment(writeCellStyle.getHorizontalAlignment());
        }
        if (writeCellStyle.getWrapped() != null) {
            cellStyle.setWrapText(writeCellStyle.getWrapped());
        }
        if (writeCellStyle.getVerticalAlignment() != null) {
            cellStyle.setVerticalAlignment(writeCellStyle.getVerticalAlignment());
        }
        if (writeCellStyle.getRotation() != null) {
            cellStyle.setRotation(writeCellStyle.getRotation());
        }
        if (writeCellStyle.getIndent() != null) {
            cellStyle.setIndention(writeCellStyle.getIndent());
        }
        if (writeCellStyle.getBorderLeft() != null) {
            cellStyle.setBorderLeft(writeCellStyle.getBorderLeft());
        }
        if (writeCellStyle.getBorderRight() != null) {
            cellStyle.setBorderRight(writeCellStyle.getBorderRight());
        }
        if (writeCellStyle.getBorderTop() != null) {
            cellStyle.setBorderTop(writeCellStyle.getBorderTop());
        }
        if (writeCellStyle.getBorderBottom() != null) {
            cellStyle.setBorderBottom(writeCellStyle.getBorderBottom());
        }
        if (writeCellStyle.getLeftBorderColor() != null) {
            cellStyle.setLeftBorderColor(writeCellStyle.getLeftBorderColor());
        }
        if (writeCellStyle.getRightBorderColor() != null) {
            cellStyle.setRightBorderColor(writeCellStyle.getRightBorderColor());
        }
        if (writeCellStyle.getTopBorderColor() != null) {
            cellStyle.setTopBorderColor(writeCellStyle.getTopBorderColor());
        }
        if (writeCellStyle.getBottomBorderColor() != null) {
            cellStyle.setBottomBorderColor(writeCellStyle.getBottomBorderColor());
        }
        if (writeCellStyle.getFillPatternType() != null) {
            cellStyle.setFillPattern(writeCellStyle.getFillPatternType());
        }
        if (writeCellStyle.getFillBackgroundColor() != null) {
            cellStyle.setFillBackgroundColor(writeCellStyle.getFillBackgroundColor());
        }
        if (writeCellStyle.getFillForegroundColor() != null) {
            cellStyle.setFillForegroundColor(writeCellStyle.getFillForegroundColor());
        }
        if (writeCellStyle.getShrinkToFit() != null) {
            cellStyle.setShrinkToFit(writeCellStyle.getShrinkToFit());
        }
    }

    private static void buildFont(Workbook workbook, CellStyle cellStyle, WriteFont writeFont) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        cellStyle.setFont(font);
        if (writeFont == null) {
            return;
        }
        if (writeFont.getFontName() != null) {
            font.setFontName(writeFont.getFontName());
        }
        if (writeFont.getFontHeightInPoints() != null) {
            font.setFontHeightInPoints(writeFont.getFontHeightInPoints());
        }
        if (writeFont.getItalic() != null) {
            font.setItalic(writeFont.getItalic());
        }
        if (writeFont.getStrikeout() != null) {
            font.setStrikeout(writeFont.getStrikeout());
        }
        if (writeFont.getColor() != null) {
            font.setColor(writeFont.getColor());
        }
        if (writeFont.getTypeOffset() != null) {
            font.setTypeOffset(writeFont.getTypeOffset());
        }
        if (writeFont.getUnderline() != null) {
            font.setUnderline(writeFont.getUnderline());
        }
        if (writeFont.getCharset() != null) {
            font.setCharSet(writeFont.getCharset());
        }
        if (writeFont.getBold() != null) {
            font.setBold(writeFont.getBold());
        }
    }

}
