package demo.Excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    @ExcelProperty(value = "学生编号",index = 0)
    Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    String name;

}
