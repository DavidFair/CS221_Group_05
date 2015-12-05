package uk.ac.aber.cs221.group5.jUnit;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Scanner;

import uk.ac.aber.cs221.group5.logic.Database;

public class DatabaseTest {
	
	/* !! WARNING - If you populate these fields with any usernames or
	 * !! passwords ensure they are removed before pushing to GitHub !
	 * !! ESP - PERSONAL DETAILS SUCH AS YOUR OWN USERNAME/PASSWORD !!
	 */

	private static String dbURL = "db.dcs.aber.ac.uk";
	private static String dbUsername = "";
	private static String dbName = "";
	
	//This can be blank to force JDBC to use default port
	private static String dbPort = "";
	
	private static String dbPassword = "";
	
	private Database testClass;
	
	@BeforeClass
	public static void checkTestFields(){
		boolean allDbFieldsPresent = true;
		
		if (dbURL == "" || dbName == ""){
			System.err.println("Database details missing");
			allDbFieldsPresent = false;
		}
		
		if (dbUsername == "" || dbPassword == ""){
			System.err.println("Database credentials missing");
			allDbFieldsPresent = false;
		}
		
		assertTrue("Cannot run tests as database detail(s) missing", allDbFieldsPresent);
		
	}
	
	
	@Before
	public void createDbClass(){		
		testClass = new Database();
	}
	
	@Test
	public void testConnection(){
		boolean isConnected;
		isConnected = testClass.connect(dbURL, dbPort, dbUsername, dbPassword, dbName);
		
		assertTrue("Database failed to connect", isConnected);
		
	}
	
	
	
	

	
}
