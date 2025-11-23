package cn.argento.askia.excel.read.HSSF;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;

/**
 * 此Demo演示如何往xls文件中写入内容并保存到文件
 *
 */
public class HSSFWorkBookWriterDemo {
    public static void main(String[] args) throws IOException {
        // 1. 首先我们现创建一个Workbook
        final Workbook workbook = WorkbookFactory.create(false);
        // 2. 然后我们创建一个Sheet, 如果创建的过程中我们不指定sheet的名字的话，则默认使用sheet1、sheet2等名字
        final Sheet sheet = workbook.createSheet();
        // 3. 然后我们创建
    }
}
