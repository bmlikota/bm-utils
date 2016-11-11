package report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import hr.bm.report.ReportExample;
import hr.bm.report.ReportManager;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() throws Exception
    {
    	DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
    	DocumentTemplate template = documentTemplateFactory.getTemplate(new File("my-template.odt"));
    	Map data = new HashMap();
    	data.put("var", "value");
    	//...
    	template.createDocument(data, new FileOutputStream("my-output.odt"));
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public static void main(String[] args) {
    	ReportManager reportManager = new ReportManager();
    	ReportExample reportExample = new ReportExample("reportExample");
    	byte[] outputFile = reportManager.generateReport(reportExample.getTemplate(), reportExample.getReportData());
    	System.out.println(outputFile);
	}
}
