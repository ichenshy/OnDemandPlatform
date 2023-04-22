package demo.Excel;
import com.alibaba.excel.EasyExcel;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
public class EasyExcelDmo {

    //读操作
    @Test
    public void test() {
        String filename1 = "D:/demo1.xlsx";
        EasyExcel.read(filename1,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //写操作
    @Test
    public void test1() {
        String filename1 = "D:/demo1.xlsx";
        EasyExcel.write(filename1, DemoData.class).sheet("学生列表").doWrite(getList());
    }

    private List getList() {
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setName("盖盖" + i);
            list.add(data);
        }
        return list;
    }


}
