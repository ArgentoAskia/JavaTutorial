package cn.argento.askia.excel.read.HSSF;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 默认情况下使用Iterator的迭代方式会自动忽略空行和空单元格
 * 如果你不希望忽略这些内容，则需要使用其他遍历方式
 */
public class HSSFWorkBookReaderWithBlankDemo {
    private static final String xlsFileName = "core-java-8/java-file-processing/javax-excel/src/main/resources/小量数据示例.xlsx";
    public static void main(String[] args) throws IOException {
        File xlsFile = new File(xlsFileName);
        // 1. 官方推荐使用WorkbookFactory.create()方法来创建, 可以传递File或者InputStream
        final Workbook workbook = WorkbookFactory.create(xlsFile);
        // 2. Workbook实现了Iterator接口, 因此当你需要迭代访问xls的时候可以直接迭代访问
        // Workbook底下
        for(Sheet sheet : workbook){
            // 3. 代表一个工作簿, 同样实现了Iterator接口,再往下就是Row(行)
            System.out.println("============ " + sheet.getSheetName() + " ============");
            // 4.接下来我们就不能再进行迭代了，我们需要获取当前sheet的行列长度（第一行有内容的行到最后一行有内容的行）
            // 需要注意，行Num是从0开始的，和实际打开Excel可能有点出入（Wps中是1开始）
            final int firstRowNum = sheet.getFirstRowNum();
            final int lastRowNum = sheet.getLastRowNum();
            System.out.println("表格第一行Number: " + firstRowNum);
            System.out.println("表格最后一行Number: " + lastRowNum);
            for (int row = firstRowNum; row <= lastRowNum; row++){
                // 获取当前行
                final Row row1 = sheet.getRow(row);
                System.out.println("当前行为" + (row + 1));
                if (row1 == null){
                    // 如果当前行是空行的话，此时将会返回null
                    System.out.println("当前行" + (row + 1) + "是一个空行");
                }
                else{
                    // 不是空行，则我们检查是否是空单元格
                    final short firstCellNum = row1.getFirstCellNum();
                    final short lastCellNum = row1.getLastCellNum();
                    System.out.println("表格第一列Cell：" + firstCellNum);
                    System.out.println("表格最后一列Cell：" + lastCellNum);
                    for (int cell = firstCellNum; cell <= lastCellNum; cell++) {
                        // getCell()有两个重载，一是默认返回空行和空列，二是可以指定如何解析单元格
                        final Cell cell1 = row1.getCell(cell);
//                        final Cell cell1 = row1.getCell(cell, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        if (cell1 == null){
                            //  cell1 为空
                            System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]是一个空单元格");
                        }
                        else {
                            if (CellType.STRING == cell1.getCellType()){
                                final String stringCellValue = cell1.getStringCellValue();
                                System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]是一个字符串, 值为：" + stringCellValue);
                            }
                            else if (CellType.BOOLEAN == cell1.getCellType()){
                                final boolean booleanCellValue = cell1.getBooleanCellValue();
                                System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]是一个布尔, 值为：" + booleanCellValue);
                            }
                            else if (CellType.NUMERIC == cell1.getCellType()){
                                // 我们现在可以判断是否是日期类型了，由于Excel支持自定义日期格式的，官方提供了两类API给我们进行判断
                                // public static boolean isCellDateFormatted(Cell cell):   检查单元格是否包含日期。它会检查单元格的格式是否为日期格式，包括内部日期格式和自定义日期格式。(无特殊情况下推荐使用这个API)
                                // public static boolean isCellInternalDateFormatted(Cell cell) 此API只能检查单元格是否包含日期，但只检查内部 Excel 日期格式。这意味着它不会考虑自定义日期格式
                                if (DateUtil.isCellDateFormatted(cell1)){
                                    // 如果是日期类型的话日期类型数据, 则可以解析成LocaleDateTime或者Date都Ok
                                    if (Math.random() > 0.5){
                                        final Date dateCellValue = cell1.getDateCellValue();
                                        System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]是一个日期, 值为：" + dateCellValue);
                                    }
                                    else{
                                        final LocalDateTime localDateTimeCellValue = cell1.getLocalDateTimeCellValue();
                                        System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]是一个日期, 值为：" + localDateTimeCellValue);
                                    }
                                }
                                else{
                                    final double numericCellValue = cell1.getNumericCellValue();
                                    System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]是一个数值,值为：" + numericCellValue);
                                }
                            }
                            else if (CellType.ERROR == cell1.getCellType()){
                                final byte errorCellValue = cell1.getErrorCellValue();
                                System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]有错误信息,值为：" + errorCellValue);
                            }
                            else if (CellType.FORMULA == cell1.getCellType()){
                                // 获取单元格的公式
                                final String cellFormula = cell1.getCellFormula();
                                System.out.println("当前单元格[" + (row + 1) + "," + (cell + 1) +"]包含公式，值为：" + cellFormula);
                            }
                        }
                    }
                }
            }
            System.out.println("============ " + sheet.getSheetName() + " ============");
        }

        // 6.记得关闭工作簿
        workbook.close();
    }
}
