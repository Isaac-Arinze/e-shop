package com.zikan.e_shop.helper;

import com.zikan.e_shop.dto.ProductDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {

    /* check that file is of Excel type or not */

    public static boolean checkExcelFormat(MultipartFile file){

        String contentType = file.getContentType();

        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        {
            return true;
        }
        else {
            return false;
        }

    }

    //convert excel to list of products
    public static List<ProductDto> convertExcelToListOfProduct(InputStream is)
    {
        List<ProductDto> productDtoList = new ArrayList<>();

        try{
            XSSFWorkbook workbook = new XSSFWorkbook(is);
             XSSFSheet sheet = workbook.getSheet("data");

             int rowNumber = 0;
             Iterator<Row> iterator = sheet.iterator();

             while (iterator.hasNext()){
                 Row row = iterator.next();

                 if(rowNumber==0){
                     rowNumber++;
                     continue;
                 }
                  Iterator<Cell> cells = row.iterator();
                 int cid = 0;

                 ProductDto productDto = new ProductDto();
                 while (cells.hasNext())
                 {
                      Cell cell = cells.next();

                      switch (cid)
                      {
                          case 0:
                          productDto.setId((long)cell.getNumericCellValue());
                          break;
                          case 1:
                              productDto.setName(cell.getStringCellValue());
                              break;
                          case 3:
                              productDto.setDescription(cell.getStringCellValue());
                              break;
                          default:
                              break;
                      }
                      cid++;
                 }
                 productDtoList.add(productDto);
             }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return productDtoList;

    }
}
