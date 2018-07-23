
import javax.swing.JOptionPane;

import org.testng.ITestNGListener;
import org.testng.TestNG;
public class InvokeMaster {
	public static String sheetDirPathAndName;
	public static String sheetDirPath;
		public static void main(String[] args) {
			sheetDirPathAndName = "C:\\Test\\CRMAutomatedTestCases.xlsm";
			sheetDirPath = "C:\\Test\\";
			/**if (args.length > 0)			// ENABLE FOR TOOL, for debigging disable it
			{
				
				if (!args[0].equals(null) && !args[0].equals(""))
				{
					sheetDirPathAndName = args[0];
					
				}
				
				if (!args[1].equals(null))  
				{
					sheetDirPath = args[1];					
				}
				
				//System.out.println(args[0]);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Issue in identifying the User directory.");
				System.exit(1);
				//sheetDirPathAndName= null; //System.getProperty("user.dir")+"/TestCases.xlsm";				
			}**/
			//	for(String s : args){
			//		System.out.println("Hello"+ s);
			 //   }
				//TestListenerAdapter tla = new TestListenerAdapter();
				ITestNGListener tla = new ITestNGListener() {
				};
				TestNG testng = new TestNG();			
				testng.setTestClasses(new Class[] { Master.class });
				//testng.setAnnotationTransformer(new Master());
				testng.addListener(new Master());
				testng.addListener(tla);				
				testng.run();			
	}
		
		

}
