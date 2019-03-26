package com.incture.zp.ereturns.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.incture.zp.ereturns.dto.IdpGroupDto;
import com.incture.zp.ereturns.dto.IdpUserDto;

@Component
public class ReadCustomerExcelUtil {

	@SuppressWarnings("resource")
	public List<IdpUserDto> readUserExcel(String path) {
		List<IdpUserDto> list = new ArrayList<>();
		try {
			// String excelPath = "C:\\temp\\User.xlsx";
			FileInputStream fileInputStream = new FileInputStream(new File(path));

			// Create Workbook instance holding .xls file
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

			// Get the first worksheet
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				// Get Each Row
				Row row = rowIterator.next();

				//Leaving the first row alone as it is header
				if (row.getRowNum() == 0)
					continue;

				// Iterating through Each column of Each Row
				Iterator<Cell> cellIterator = row.cellIterator();
				IdpUserDto idpUserDto = new IdpUserDto();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();

					// Checking the cell format
					switch (columnIndex + 1) {
					case 1:
						idpUserDto.setLoginName(cell.getStringCellValue());
						break;
					case 2:
						idpUserDto.setUserType(cell.getStringCellValue());
						break;
					case 3:
						idpUserDto.setFirstName(cell.getStringCellValue());
						break;
					case 4:
						idpUserDto.setLastName(cell.getStringCellValue());
						break;
					case 5:
						idpUserDto.setEmail(cell.getStringCellValue());
						break;
					case 6:
						idpUserDto.setGroup(cell.getStringCellValue());
						break;
					case 7:
						idpUserDto.setDivision(cell.getStringCellValue());
						break;

					}
					
				}
				list.add(idpUserDto);
			}

		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("resource")
	public List<IdpGroupDto> readGroupExcel(String path) {
		List<IdpGroupDto> list = new ArrayList<>();
		try {
			// String excelPath = "C:\\temp\\Group.xlsx";
			FileInputStream fileInputStream = new FileInputStream(new File(path));

			// Create Workbook instance holding .xls file
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

			// Get the first worksheet
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				// Get Each Row
				Row row = rowIterator.next();

				//Leaving the first row alone as it is header
				if (row.getRowNum() == 0)
					continue;

				// Iterating through Each column of Each Row
				Iterator<Cell> cellIterator = row.cellIterator();
				IdpGroupDto idpGroupDtop = new IdpGroupDto();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();

					// Checking the cell format
					switch (columnIndex + 1) {
					case 1:
						idpGroupDtop.setGroupDesc(cell.getStringCellValue());
						break;
					case 2:
						idpGroupDtop.setGroupDisplayName(cell.getStringCellValue());
						break;
					case 3:
						idpGroupDtop.setGroupName(cell.getStringCellValue());
						break;
					}
					
				}
				list.add(idpGroupDtop);
			}

		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return list;
	}

}