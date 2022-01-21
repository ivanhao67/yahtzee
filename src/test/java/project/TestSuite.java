package project;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import junit.framework.TestCase;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {AppTest.class, CucumberTestSuite.class } )
public class TestSuite {

}