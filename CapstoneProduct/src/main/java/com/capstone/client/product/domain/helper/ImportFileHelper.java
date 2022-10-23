package com.capstone.client.product.domain.helper;

import com.capstone.client.product.domain.model.entity.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportFileHelper {

    public static String EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String CSV_TYPE = "application/csv";

    static String[] HEADERs = { "productId", "name", "description", "price", "inStock", "active" };
    static String SHEET = "Products";

    public static boolean hasExcelFormat(MultipartFile file) {
        return EXCEL_TYPE.equalsIgnoreCase(file.getContentType());
    }
    public static boolean hasCsvFormat(MultipartFile file) {
        return CSV_TYPE.equalsIgnoreCase(file.getContentType());
    }

    public static List<Product> excelToProducts(InputStream is) {
        try {

            Workbook workbook = new XSSFWorkbook(is); // matching .xlsx format
            Sheet sheet = workbook.getSheetAt(0);
            List<Product> products = new ArrayList<Product>();

            if (sheet != null) {

                for (Row nextRow : sheet) {

                    if (nextRow.getRowNum() == 0) {
                        // Ignore header
                        continue;
                    }

                    // Get all cells
                    Iterator<Cell> cellIterator = nextRow.cellIterator();

                    // Read cells and set value for book object
                    Product product = new Product();
                    while (cellIterator.hasNext()) {
                        // Read cell
                        Cell cell = cellIterator.next();

                        if (cell == null || cell.toString().isEmpty()) continue;

                        // Set value for product object
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case 0:
                                product.setProductId((int) cell.getNumericCellValue());
                                break;
                            case 1:
                                product.setName(cell.getStringCellValue());
                                break;
                            case 2:
                                product.setDescription(cell.getStringCellValue());
                                break;
                            case 3:
                                product.setPrice((float) cell.getNumericCellValue());
                                break;
                            case 4:
                                product.setInStock((int) cell.getNumericCellValue());
                                break;
                            case 5:
                                product.setActive(((int) cell.getNumericCellValue()) > 0);
                                break;
                            default:
                                break;
                        }
                    }

                    System.out.println(product.toString());
                    products.add(product);
                }
            } else {
                System.out.println("File is null or empty");
            }

            workbook.close();

            return products;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage());
        }
    }
}
