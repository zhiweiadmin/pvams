package com.goodpower.pvams.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ExcelService {

    public String getCellValue(Cell cell){
        if(cell == null){
            return null;
        }
        String value = null;
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellTypeEnum()) {
            case FORMULA:
                try {
                    value = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                value = formatter.formatCellValue(cell);
                break;
        }
        return  value;
    }

    public String getCellValue(Row row,int col){
        Cell cell = row.getCell(col);
        if(cell == null){
            return null;
        }
        String value = null;
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellTypeEnum()) {
            case FORMULA:
                try {
                    value = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                value = formatter.formatCellValue(cell);
                break;
        }
        return  value;
    }

    public String getCellDate(Cell currentCell) {
        if(currentCell == null){
            return "";
        }
        String currentCellValue = "";
        // 判断单元格数据是否是日期
        if ("yyyy/mm;@".equals(currentCell.getCellStyle().getDataFormatString())
                || "m/d/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yy/m/d".equals(currentCell.getCellStyle().getDataFormatString())
                || "mm/dd/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "dd-mmm-yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yyyy/m/d".equals(currentCell.getCellStyle().getDataFormatString())) {
            if (DateUtil.isCellDateFormatted(currentCell)) {
                // 用于转化为日期格式
                Date d = currentCell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                currentCellValue = formater.format(d);
            }
        } else {
            // 不是日期原值返回
            currentCellValue = currentCell.toString();
        }
        return currentCellValue;
    }

    public String getRowValue(Row row, int colNum){
        DataFormatter formatter = new DataFormatter();
        if(row != null){
            Cell cell = row.getCell(colNum);
            if(cell != null){
                if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    short format = cell.getCellStyle().getDataFormat();
                    System.out.println("format:"+format+";;;;;value:"+cell.getNumericCellValue());
                    SimpleDateFormat sdf = null;
                    if (format == 14 || format == 31 || format == 57 || format == 58
                            || (176<=format && format<=178) || (182<=format && format<=196)
                            || (210<=format && format<=213) || (208==format ) ) { // 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                        sdf = new SimpleDateFormat("HH:mm");
                    } else { // 不是日期格式
                        return formatter.formatCellValue(cell);
                    }
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    if(date==null || "".equals(date)){
                        return null;
                    }
                    String result=null;
                    try {
                        result = sdf.format(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    return result;
                }else{
                    return formatter.formatCellValue(cell);
                }

            }
            return null;
        }
        return null;
    }


}
