
import org.testng.ITestNGListener;
import org.testng.TestNG;
public class InvokeMaster {	
		public static void main(String[] args) {							
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
