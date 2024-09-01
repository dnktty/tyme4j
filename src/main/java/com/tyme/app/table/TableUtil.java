package com.tyme.app.table;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import com.tyme.culture.Color;
import com.tyme.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class TableUtil {
  private static final int maxLength = 10;

  public static Table loadFromJSON(String filePath) {
    Table table = new Table();
    try {
      Gson gson = new Gson();
      filePath = TableUtil.class.getClassLoader().getResource(filePath).getFile();
      FileReader reader = new FileReader(filePath);
      table = gson.fromJson(reader, Table.class);
    } catch (Exception e) {
      log.error("Error reading JSON file: {}", filePath, e);
    }
    return table;
  }

  /**
   * 合并行
   *
   * @param mainTable
   * @param targetTable
   * @return
   */
  public static Table mergeRows(Table mainTable, Table targetTable) {
    for (int i = 0; i < mainTable.getHeaders().size(); i++) {
      if (!mainTable
          .getHeaders()
          .get(i)
          .getValue()
          .equals(targetTable.getHeaders().get(i).getValue())) {
        throw new BizException("合并表格行失败，列不对等");
      }
    }
    mainTable.getRows().addAll(targetTable.getRows());
    return mainTable;
  }

  /**
   * 合并列
   *
   * @param mainTable
   * @param targetTable
   * @return
   */
  public static Table mergeColumns(Table mainTable, Table targetTable) {
    if (mainTable.getRows().size() != targetTable.getRows().size()) {
      throw new BizException("合并表格行失败, 行数不相同");
    }
    for (Column column : targetTable.getColumns()) {
      mainTable.addColumn(column);
    }
    return mainTable;
  }

  /**
   * 打印表格数据
   *
   * @param table
   */
  public static void printTable(Table table) {
    printTable(table, maxLength);
  }

  public static void printTable(Table table, int maxLength) {
    log.info("==============================================================================");
    log.info("< {} - {} >", table.getName(), table.getTitle());
    // 打印表头
    StringBuilder headerBuilder = new StringBuilder(" ");
    List<Header> headers = table.getHeaders();
    List<Row> rows = table.getRows();
    if (headers.size() > 0) {
      for (Header columnHeader : headers) {
        headerBuilder.append(columnHeader.getColorVal());
        headerBuilder
            .append(
                formatStr(
                    columnHeader.getValue(),
                    0 == columnHeader.getWidth() ? maxLength : columnHeader.getWidth()))
            .append(Color.RESET.getStyle())
            .append(" | ");
      }
      headerBuilder.append(Color.RESET.getStyle());
      log.info(headerBuilder.toString());
    }

    // 打印表格数据
    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.get(i);
      StringBuilder rowBuilder = new StringBuilder();
      for (int j = 0; j < row.getCells().size(); j++) {
        Cell cell = row.getCells().get(j);
        int cellWidth = headers.size() > j ? headers.get(j).getWidth() : maxLength;
        rowBuilder
            .append(cell.getColorVal())
            .append(formatStr(cell.getValue(), 0 == cellWidth ? maxLength : cellWidth))
            .append(Color.RESET.getStyle())
            .append(" | ");
      }
      log.info(rowBuilder.toString());
    }
  }

  private static String formatStr(Object obj) {
    return formatStr(obj, maxLength);
  }

  private static String formatStr(Object obj, int width) {
    String str = null == obj ? "" : obj.toString();
    if (null == str) {
      str = "";
    }
    int appendLen = calculateWidth(str);
    appendLen = appendLen > width ? 0 : width - appendLen;
    for (int i = 0; i < appendLen; i++) {
      str = StringUtils.join(str, " ");
    }
    return str;
  }

  // 计算字符串的实际宽度（考虑中文字符）
  private static int calculateWidth(String str) {
    str.replace(" ", "");
    float width = 0;
    for (char c : str.toCharArray()) {
      // 判断是否为全角字符
      if (isFullWidth(c)) {
        width += 1.5; // 全角字符占两个位置
      } else {
        width += 1; // 半角字符占一个位置
      }
    }
    return Float.valueOf(width).intValue();
  }

  /**
   * 计算长度
   *
   * @param ch
   * @return
   */
  public static boolean isFullWidth(char ch) {
    Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
    return (block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        || block == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
        || ch >= '\u3400' && ch <= '\u4DBF'
        || // CJK Extensions A
        ch >= '\u4E00' && ch <= '\u9FFF'
        || // CJK Unified Ideographs
        ch >= '\uF900' && ch <= '\uFAFF'
        || // CJK Compatibility Ideographs
        ch >= '\uFE30' && ch <= '\uFE4F'
        || // CJK Compatibility Forms
        ch >= '\uFF00' && ch <= '\uFFEF' // Fullwidth Forms
    );
  }
}
