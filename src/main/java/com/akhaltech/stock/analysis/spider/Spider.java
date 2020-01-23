package com.akhaltech.stock.analysis.spider;

import com.akhaltech.stock.analysis.spider.os.OSinfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.function.Function;

public class Spider {

    private final static Logger log = Logger.getLogger(Spider.class);

    protected final static String EMPTY = "";
    protected final static String NEW_LINE = "\\n";
    protected ObjectMapper mapper = new ObjectMapper();
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected void initDriver() {
//        ClassLoader classLoader = getClass().getClassLoader();
//        File rootFolder = new File(classLoader.getResource("chromeDrivers").getFile());
//        String rootPath = rootFolder.getAbsolutePath();
        if(OSinfo.isLinux()) {
//            System.setProperty("webdriver.chrome.driver", rootPath + "/linux/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
            log.info("Run in Linux");
        }else if(OSinfo.isWindows()) {
//            System.setProperty("webdriver.chrome.driver", rootPath + "\\windows\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\x216124\\Downloads\\chromedriver.exe");
            driver = new ChromeDriver();
            log.info("Run in Windows");
        }else {
//            System.setProperty("webdriver.chrome.driver", rootPath + "/mac/chromedriver");
//            driver = new ChromeDriver();
//            log.info("Run in Mac");
        }
        wait = new WebDriverWait(driver, 10);
    }

    protected void scrollUntilLoaded() {
        log.info("Run scrollUntilLoaded()");
        long checkHeight = (long)((JavascriptExecutor)driver).executeScript("return document.body.scrollHeight;");
        while(true) {
            ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                final long checkHeightFinal = checkHeight;
                wait.until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        long checkHeight2 = (long)((JavascriptExecutor)driver).executeScript("return document.body.scrollHeight;");
                        return checkHeight2 > checkHeightFinal;
                    }
                });
                checkHeight = (long)((JavascriptExecutor)driver).executeScript("return document.body.scrollHeight;");
            }catch(Exception e) {
                e.printStackTrace();
                log.error(e.getMessage(), e);
                break;
            }
        }
    }

}
