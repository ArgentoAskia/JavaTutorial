package cn.argento.askia.excel.read.HSSF;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 此Demo描述了如何简单读取一个Excel表格(.xls)
 */
public class HSSFWorkBookReaderDemo {

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
            for (Row row : sheet){
                // 4. 代表一行，同样实现了Iterator接口,再往下就是列，以定位单元格
                boolean firstFlag = true;
                List<Object> rowData = new ArrayList<>();
                for (Cell cell : row){
                    // 5. Cell中我们可以使用getXXXCellValue()获取特定类型的值
                    // 遍历时要注意类型问题，单元格的值都有类型，如果类型不匹配则会抛出IllegalStateException: Cannot get a STRING value from a NUMERIC cell等异常
                    // 因此我们可以结合getCellType()方法来进行获取值之前的判断
                    // 但是比较遗憾的一点是，cell.getCellType()返回的CellType枚举类只有STRING、BOOLEAN、NUMERIC、ERROR时才是有效的解析
                    // 而getXXXCellValue()支持Date、RichString(富文本, 带字体、格式、颜色等)、LocaleDateTime,其中Date、LocaleDateTime会被当作CellType.NUMERIC
                    // RichString会被当作CellType.STRING
                    // 因此当cell.getCellType() == CellType.NUMERIC时，实际上当前单元格存储的值不一定是Number(CellType.STRING同理)
                    // 实际在查阅相关API和文档之后，得出：
                    // 如果是CellType.STRING的情况下Cell的实现HSSFCell中,  cell.getRichStringCellValue()和cell.getStringCellValue()没有区别, 在源代码中cell.getStringCellValue()实际上也是调用的cell.getRichStringCellValue()
                    // 而在处理Date和LocaleDateTime时，官方给我们提供了DateUtil工具类来进行判别（顺便一提，这个工具类提供了很多方法给我们处理日期数据，还是比较好用的）
                    if (CellType.STRING == cell.getCellType()){
                        final String stringCellValue = cell.getStringCellValue();
                        rowData.add(stringCellValue);
                    }
                    else if (CellType.BOOLEAN == cell.getCellType()){
                        final boolean booleanCellValue = cell.getBooleanCellValue();
                        rowData.add(booleanCellValue);
                    }
                    else if (CellType.NUMERIC == cell.getCellType()){
                        // 我们现在可以判断是否是日期类型了，由于Excel支持自定义日期格式的，官方提供了两类API给我们进行判断
                        // public static boolean isCellDateFormatted(Cell cell):   检查单元格是否包含日期。它会检查单元格的格式是否为日期格式，包括内部日期格式和自定义日期格式。(无特殊情况下推荐使用这个API)
                        // public static boolean isCellInternalDateFormatted(Cell cell) 此API只能检查单元格是否包含日期，但只检查内部 Excel 日期格式。这意味着它不会考虑自定义日期格式
                        if (DateUtil.isCellDateFormatted(cell)){
                            // 如果是日期类型的话日期类型数据, 则可以解析成LocaleDateTime或者Date都Ok
                            if (Math.random() > 0.5){
                                final Date dateCellValue = cell.getDateCellValue();
                                rowData.add(dateCellValue);
                            }
                            else{
                                final LocalDateTime localDateTimeCellValue = cell.getLocalDateTimeCellValue();
                                rowData.add(localDateTimeCellValue);
                            }
                        }
                        else{
                            final double numericCellValue = cell.getNumericCellValue();
                            rowData.add(numericCellValue);
                        }

                    }
                    else if (CellType.ERROR == cell.getCellType()){
                        final byte errorCellValue = cell.getErrorCellValue();
                        rowData.add(errorCellValue);
                    }
                    else if (CellType.FORMULA == cell.getCellType()){
                        final String cellFormula = cell.getCellFormula();
                        rowData.add(cellFormula);
                    }
                }
                // 输出表格内容
                for (Object o : rowData){
                    if (firstFlag){
                        System.out.printf("%-15s", o.toString());
                        firstFlag = false;
                    }
                    else{
                        System.out.printf("%-15s", o.toString());
                    }
                }
                System.out.println();
            }
            System.out.println("============ " + sheet.getSheetName() + " ============");
        }

        // 6.记得关闭工作簿
        workbook.close();
    }
}
