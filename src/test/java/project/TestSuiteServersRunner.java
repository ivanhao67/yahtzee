package project;

import org.junit.experimental.categories.Category;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

public class TestSuiteServersRunner {

    public static void main(String[] args) {
        TestSuiteServersRunner serverRunner = new TestSuiteServersRunner();
        serverRunner.startServers();
    }

    public void startServers(){
        //Create Servers
        ServerTest endingGameFeature = new ServerTest(3005, 1);
        ServerTest gameNetworkFeature = new ServerTest(3007, 3);
        ServerTest startingGameFeature = new ServerTest(3009, 1);

        //Create Server Threads
        Thread endingGameFeatureThread = new Thread(endingGameFeature);
        Thread gameNetworkFeatureThread = new Thread(gameNetworkFeature);
        Thread startingGameFeatureThread = new Thread(startingGameFeature);

        endingGameFeatureThread.start();
        gameNetworkFeatureThread.start();
        startingGameFeatureThread.start();
    }
}
