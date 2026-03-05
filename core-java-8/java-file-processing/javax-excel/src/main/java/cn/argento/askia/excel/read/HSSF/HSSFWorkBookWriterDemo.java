package cn.argento.askia.excel.read.HSSF;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 此Demo演示如何往xls文件中写入内容并保存到文件
 *
 */
public class HSSFWorkBookWriterDemo {
    public static void main(String[] args) throws IOException {
        // 1. 首先我们现创建一个Workbook
//        final Workbook workbook = WorkbookFactory.create(false);
        // 如果希望写出xlsx
        Workbook workbook = WorkbookFactory.create(true);
        // 2. 然后我们创建一个Sheet, 如果创建的过程中我们不指定sheet的名字的话，则默认使用sheet0、sheet1、sheet2等名字
        final Sheet sheet = workbook.createSheet();
        // 3. 然后我们在sheet中创建一个单元格
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("单元格1");
        Cell cell1 = row.createCell(1, CellType.NUMERIC);
        cell1.setCellValue(23.5);


        // 6. 写出workbook到文本
//        FileOutputStream fileOutputStream = new FileOutputStream(new File("core-java-8/java-file-processing/javax-excel/src/main/resources/1.xls"));
        //  如果导出的是xlsx,则打开这个
        FileOutputStream fileOutputStream = new FileOutputStream(new File("core-java-8/java-file-processing/javax-excel/src/main/resources/1.xlsx"));
        workbook.write(fileOutputStream);
        fileOutputStream.close();



    }
}
