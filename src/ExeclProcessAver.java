/**
 * Created by d on 2018/9/27.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.ss.usermodel.Row;

public class ExeclProcessAver {

    public static void main(String[] args) {

        // 读取Excel文件
        File file = new File("D:/execl.xls");
        try {
            //得到所有数据
            List<Bo> allData = readExcel(file);

            //直接将它写到excel中
            List<Bo> result = dealData(allData);
            makeExcel(result);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 获取数据
     * @param file
     * @return
     * @throws Exception
     */
    private static List<Bo> readExcel(File file) throws Exception {

        // 创建输入流，读取Excel
        InputStream is = new FileInputStream(file.getAbsolutePath());
        // jxl提供的Workbook类
        Workbook wb = Workbook.getWorkbook(is);
        // 只有一个sheet,直接处理
        //创建一个Sheet对象
        Sheet sheet = wb.getSheet(0);
        // 得到所有的行数
        int rows = sheet.getRows();
        // 所有的数据
        List<Bo> allData = new ArrayList();
        // 越过第一行 它是列名称
        for (int j = 1; j < rows; j++) {

            // 得到每一行的单元格的数据
            Cell[] cells = sheet.getRow(j);
            Bo oneData = new Bo();
            oneData.setItem(cells[0].getContents().trim());
            oneData.setSubject(cells[1].getContents().trim());
            oneData.setVowel(cells[2].getContents().trim());
            oneData.setNode(cells[3].getContents().trim());
            oneData.setF1(Integer.parseInt(cells[4].getContents().trim()));
            oneData.setF2(Integer.parseInt(cells[5].getContents().trim()));
            oneData.setF3(Integer.parseInt(cells[6].getContents().trim()));
            oneData.setF4(Integer.parseInt(cells[7].getContents().trim()));
            // 存储每一条数据
            allData.add(oneData);
            // 打印出每一条数据
            //System.out.println(oneData);

        }
        return allData;

    }

    /**
     * 处理数据
     */
    public static  List<Bo> dealData(List<Bo> allData) {
        Multimap<String, Bo> dataMap = ArrayListMultimap.create();        //结果
        List<Bo> result=new ArrayList();
        DecimalFormat fnum1 = new DecimalFormat("##0.00");
       for(int i=0;i<allData.size();i++) {
           Bo oneData = allData.get(i);
            String key = oneData.getSubject()+oneData.getVowel()+oneData.getNode();
            dataMap.put(key,oneData);
        }
        for(String key:dataMap.keySet())
        {
            List<Bo> dataList = (List<Bo>) dataMap.get(key);
            Bo avData = new Bo();
            double f1 = 0;
            double f2 = 0;
            double f3 = 0;
            double f4 = 0;
            Integer size = dataList.size();
            for(Bo vo:dataList)
            {
                avData.setSubject(vo.getSubject());
                avData.setVowel(vo.getVowel());
                avData.setNode(vo.getNode());
                f1 = f1+vo.getF1();
                f2 = f2+vo.getF2();
                f3 = f3+vo.getF3();
                f4 = f4+vo.getF4();
            }
            double avF1 = f1/size;
            double avF2 = f2/size;
            double avF3 = f3/size;
            double avF4 = f4/size;
            avData.setF1(Double.valueOf(fnum1.format(avF1)));
            avData.setF2(Double.valueOf(fnum1.format(avF2)));
            avData.setF3(Double.valueOf(fnum1.format(avF3)));
            avData.setF4(Double.valueOf(fnum1.format(avF4)));
            result.add(avData);
        }

        return result;
    }

    /**
     * 将数据写入到excel中
     */
    public static  void makeExcel(List<Bo> result) {

        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("resultFormants");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Subject");
        cell = row.createCell(1);
        cell.setCellValue("Vowel");
        cell = row.createCell(2);
        cell.setCellValue("Node");
        cell = row.createCell(3);
        cell.setCellValue("F1");
        cell = row.createCell(4);
        cell.setCellValue("F2");
        cell = row.createCell(5);
        cell.setCellValue("F3");
        cell = row.createCell(6);
        cell.setCellValue("F4");

        //第五步，写入数据
        for (int i = 0; i < result.size(); i++) {
            Bo oneData = result.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            //创建单元格设值
            row1.createCell(0).setCellValue(oneData.getSubject());
            row1.createCell(1).setCellValue(oneData.getVowel());
            row1.createCell(2).setCellValue(oneData.getNode());
            row1.createCell(3).setCellValue(oneData.getF1());
            row1.createCell(4).setCellValue(oneData.getF2());
            row1.createCell(5).setCellValue(oneData.getF3());
            row1.createCell(6).setCellValue(oneData.getF4());
    }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("D:\\result.xls");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}