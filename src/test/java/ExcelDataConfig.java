import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataConfig {
	
	public XSSFWorkbook wbxlsx;    //Global Variable. It will work for both xls and xlsx.
	public XSSFSheet sheetxlsx;	//Global Variable
	OPCPackage opcPackage;
	
	//Open Excel WorkBook
	public ExcelDataConfig(String excelpath, int sheetnumber) // Constructor
	{
		try 
		{					
			//FileInputStream fis = new FileInputStream(new File(excelpath));  // Load the excel sheet in the form of Bytes		 
			opcPackage = OPCPackage.open(new File(excelpath)); // To speed up... ??
			wbxlsx = new XSSFWorkbook(opcPackage);
			sheetxlsx = wbxlsx.getSheetAt(sheetnumber);
			Row row=sheetxlsx.getRow(0);
			if (row==null)
			{
				JOptionPane.showMessageDialog(null, "No record/row data found in the excel sheet - "+excelpath);
				System.exit(1);
			}
			
			/**
			///wb = WorkbookFactory.create(new File(excelpath));
			  
			fileExtensionName = excelpath.substring(excelpath.indexOf(".")); //check the extension
			if(fileExtensionName.equals(".xlsx"))
			{
				///wbxlsx = new XSSFWorkbook(fis); //Load the excel file by this command.   //If it is xlsx file then create object of XSSFWorkbook class  	// For .xls workbook use HSSFWorkbook...
				///OPCPackage pkg = OPCPackage.open(new File(excelpath));
				wbxlsx = new XSSFWorkbook(fis);
				//wbxlsx = new XSSFWorkbook(new File(excelpath));
			}	    
			else if(fileExtensionName.equals(".xls"))
			{ 
				//File src=new File(excelpath); //  "C:\\Users\\rbhatia\\Google Drive\\Project\\Automation\\TEA\\Sprint2\\TestData.xlsx");  //Specify the File source location	
				//POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(new File(excelpath)));
				///FileInputStream fis = new FileInputStream(new File(excelpath)); // Load the excel sheet in the form of Bytes		 
				wbxls = new XSSFWorkbook(fis);	//If it is xls file then create object of XSSFWorkbook class
			}	
				**/	
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Exception",0);
			System.exit(1);
			//System.out.println(e.getMessage());
		}		
	}
	
/**	public void closeOPC()
	{
		try {
			//sheetxlsx.
			wbxlsx.close();
			opcPackage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}**/
	
	//Get data from Excel	
	public String getData(int sheetnumber, int row, int column)
	{
		Cell cell=null;
		DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
		sheetxlsx = wbxlsx.getSheetAt(sheetnumber);
		cell = sheetxlsx.getRow(row).getCell(column);   //get the value from the cell
		String data = formatter.formatCellValue((Cell) cell);  //Returns the formatted value of a cell as a String regardless of the cell type.
		
		return data;		
	}
	
	public int getRowCount(int sheetIndex)
	{
		int row=0;
		//if(fileExtensionName.equals(".xlsx"))		row = wbxlsx.getSheetAt(sheetIndex).getLastRowNum();
		row = wbxlsx.getSheetAt(sheetIndex).getLastRowNum();
		//row++;
		return row;
	}
	
	public int getColCount(int sheetIndex)
	{
		int col =0;
		col=wbxlsx.getSheetAt(sheetIndex).getRow(0).getLastCellNum();
		return col;
	}
	
	/**public void closeWB()
	{
		try {
			wbxlsx.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}**/
	
	public String putData(int sheetnumber, int row, int column)
	{
		Cell cell=null;
		DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
		sheetxlsx = wbxlsx.getSheetAt(sheetnumber);
		cell = sheetxlsx.getRow(row).getCell(column);   //get the value from the cell
		String data = formatter.formatCellValue(cell);  //Returns the formatted value of a cell as a String regardless of the cell type.
	
		return data;		
	}
	
	public String changeDateFormat(String datevalue)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		String newdatestring = null;
		try {
			date = sdf.parse(datevalue);
			DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
	        newdatestring = formatter.format(date);	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		return newdatestring;
		 
		
	/**	
	//	LocalDate localDate = new LocalDate();
	//	DateTimeFormatter dtf = new DateTimeFormatter(, arg1)
	//	LocalDate.parse(datevalue, "dd/MMM/yyyy");
		final String OLD_FORMAT = "MM/dd/yy";
		final String NEW_FORMAT = "dd/MMM/yyyy";
		String olddatestring = datevalue;
		String newdatestring;
		Date d = null;
		//SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		
		try {
			d = sdf.parse(olddatestring);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sdf.applyPattern(NEW_FORMAT);
		newdatestring = sdf.format(d);
	**/
		
	}
	
/**	public int getColCount(int sheetIndex)
	{
		int col = wb.getSheetAt(sheetIndex).getRow(0).getLastCellNum();   //getLastCellNum(): Gets the index of the last cell contained in this row PLUS ONE
		col++;
		return col;
	}**/
	
}
