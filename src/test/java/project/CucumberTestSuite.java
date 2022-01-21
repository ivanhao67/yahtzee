package project;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/project") //src/test/java/com/a1/yahtzeeGame/endingGame.feature")
public class CucumberTestSuite {
}
