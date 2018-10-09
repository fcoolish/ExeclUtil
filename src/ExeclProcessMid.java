/**
 * Created by d on 2018/9/27.
 */

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.deploy.util.StringUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExeclProcessMid {

    public static void main(String[] args) {

        // 读取Excel文件
        File file = new File("D:/mid.xls");
        try {
            //得到所有数据
            List<Voice> allData = readExcel(file);

            //直接将它写到excel中
            List<Voice> result = dealData(allData);
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
    private static List<Voice> readExcel(File file) throws Exception {

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
        List<Voice> allData = new ArrayList();
        // 越过第一行 它是列名称
        for (int j = 1; j < 359; j++) {

            // 得到每一行的单元格的数据
            Cell[] cells = sheet.getRow(j);
            Voice oneData = new Voice();
            if(cells[0].getContents().trim()!=null&&cells[0].getContents().trim()!="")
            {
                oneData.setPRONOUNCER(cells[0].getContents().trim());
                oneData.setVOWEL(cells[1].getContents().trim());
                oneData.setDURATION(Double.valueOf(cells[2].getContents().trim()));
                oneData.setK1(Double.valueOf(cells[3].getContents().trim()==null?"0":cells[3].getContents().trim()));
                oneData.setK2(Double.valueOf(cells[4].getContents().trim()==null?"0":cells[4].getContents().trim()));
                oneData.setK3(Double.valueOf(cells[5].getContents().trim()==null?"0":cells[5].getContents().trim()));
                oneData.setK4(Double.valueOf(cells[6].getContents().trim()==null?"0":cells[6].getContents().trim()));
                oneData.setSTART1(Double.valueOf(cells[7].getContents().trim()==null?"0":cells[7].getContents().trim()));
                oneData.setSTART2(Double.valueOf(cells[8].getContents().trim()==null?"0":cells[8].getContents().trim()));
                oneData.setSTART3(Double.valueOf(cells[9].getContents().trim()==null?"0":cells[9].getContents().trim()));
                oneData.setSTART4(Double.valueOf(cells[10].getContents().trim()==null?"0":cells[10].getContents().trim()));
                oneData.setDIFFERENCE1(Double.valueOf(cells[11].getContents().trim()==null?"0":cells[11].getContents().trim()));
                oneData.setDIFFERENCE2(Double.valueOf(cells[12].getContents().trim()==null?"0":cells[12].getContents().trim()));
                oneData.setDIFFERENCE3(Double.valueOf(cells[13].getContents().trim()==null?"0":cells[13].getContents().trim()));
                oneData.setDIFFERENCE4(Double.valueOf(cells[14].getContents().trim()==null?"0":cells[8].getContents().trim()));
            }
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
    public static  List<Voice> dealData(List<Voice> allData) {
        Multimap<String, Voice> dataMap = ArrayListMultimap.create();        //结果
        List<Voice> result=new ArrayList();
        DecimalFormat fnum1 = new DecimalFormat("##0.00");
       for(int i=0;i<allData.size();i++) {
           Voice oneData = allData.get(i);
            dataMap.put(oneData.getVOWEL(),oneData);
        }
        for(String key:dataMap.keySet())
        {
            List<Voice> dataList = (List<Voice>) dataMap.get(key);
            Voice avData = new Voice();
            avData.setVOWEL(dataList.get(0).getVOWEL());
            Integer size = dataList.size();
            for(int j = 1;j<=12;j++)
            {
                double midData = calMid(dataList, size,j);
                switch (j)
                {
                    case 1:
                      avData.setK1(Double.valueOf(fnum1.format(midData)));
                      break;
                    case 2:
                        avData.setK2(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 3:
                        avData.setK3(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 4:
                        avData.setK4(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 5:
                        avData.setSTART1(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 6:
                        avData.setSTART2(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 7:
                        avData.setSTART3(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 8:
                        avData.setSTART4(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 9:
                        avData.setDIFFERENCE1(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 10:
                        avData.setDIFFERENCE2(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 11:
                        avData.setDIFFERENCE3(Double.valueOf(fnum1.format(midData)));
                        break;
                    case 12:
                        avData.setDIFFERENCE4(Double.valueOf(fnum1.format(midData)));
                        break;

                }
            }
            result.add(avData);
        }

        return result;
    }

    private static Double calMid(List<Voice> dataList, Integer size,int type) {
        Boolean flag=false;
        if(size%2==0)
        {
            flag = true;
        }
        List<Double> midList=new ArrayList();
        switch (type)
        {
            case 1:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getK1());
                }
                break;
            case 2:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getK2());
                }
                break;
            case 3:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getK3());
                }
                break;
            case 4:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getK4());
                }
                break;
            case 5:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getSTART1());
                }
                break;
            case 6:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getSTART2());
                }
                break;
            case 7:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getSTART3());
                }
                break;
            case 8:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getSTART4());
                }
                break;
            case 9:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getDIFFERENCE1());
                }
                break;
            case 10:
            for(Voice vo:dataList)
            {
                midList.add(vo.getDIFFERENCE2());
            }
            break;
            case 11:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getDIFFERENCE3());
                }
                break;
            case 12:
                for(Voice vo:dataList)
                {
                    midList.add(vo.getDIFFERENCE4());
                }
                break;
        }
        Collections.sort(midList);
        double midData = 0;
        if(!flag)
        {
            int mid = size/2;
            midData=midList.get(mid);
        }else
        {
            int mid = size/2;
            midData = (midList.get(mid-1)+midList.get(mid))/2;
        }
        return midData;
    }


    /**
     * 将数据写入到excel中
     */
    public static  void makeExcel(List<Voice> result) {

        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("resultFormants");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("VOWEL");
        cell = row.createCell(1);
        cell.setCellValue("K1");
        cell = row.createCell(2);
        cell.setCellValue("K2");
        cell = row.createCell(3);
        cell.setCellValue("K3");
        cell = row.createCell(4);
        cell.setCellValue("K4");
        cell = row.createCell(5);
        cell.setCellValue("START1");
        cell = row.createCell(6);
        cell.setCellValue("START2");
        cell = row.createCell(7);
        cell.setCellValue("START3");
        cell = row.createCell(8);
        cell.setCellValue("START4");
        cell = row.createCell(9);
        cell.setCellValue("DIFFERENCE1");
        cell = row.createCell(10);
        cell.setCellValue("DIFFERENCE2");
        cell = row.createCell(11);
        cell.setCellValue("DIFFERENCE3");
        cell = row.createCell(12);
        cell.setCellValue("DIFFERENCE4");
        //第五步，写入数据
        for (int i = 0; i < result.size(); i++) {
            Voice oneData = result.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            //创建单元格设值
            row1.createCell(0).setCellValue(oneData.getVOWEL());
            row1.createCell(1).setCellValue(oneData.getK1());
            row1.createCell(2).setCellValue(oneData.getK2());
            row1.createCell(3).setCellValue(oneData.getK3());
            row1.createCell(4).setCellValue(oneData.getK4());
            row1.createCell(5).setCellValue(oneData.getSTART1());
            row1.createCell(6).setCellValue(oneData.getSTART2());
            row1.createCell(7).setCellValue(oneData.getSTART3());
            row1.createCell(8).setCellValue(oneData.getSTART4());
            row1.createCell(9).setCellValue(oneData.getDIFFERENCE1());
            row1.createCell(10).setCellValue(oneData.getDIFFERENCE2());
            row1.createCell(11).setCellValue(oneData.getDIFFERENCE3());
            row1.createCell(12).setCellValue(oneData.getDIFFERENCE4());
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