import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class JuniteTest {
    private static WebDriver driver;
    private static String baseUrl;

    private String lastName;
    private String firstName;
    private String middleName;

    public JuniteTest(String lastName, String firstName, String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }



    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();

        /* String path = System.getProperty("app.env");

        if (path=="FireFox"){
            System.setProperty("webdriver.gecko.driver", "drv/geckodriver.exe");
            driver = new FirefoxDriver() ;
        }else if(path=="Chrome"){
            System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
            driver = new ChromeDriver();
        }else if (path=="IE"){
            System.setProperty("webdriver.ie.driver", "drv/IEDriverServer6464.exe");
            driver = new InternetExplorerDriver();
        }else {
            System.out.println("Error! Browser is not found!");
        }

         */

        baseUrl = "https://www.rgs.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//ol[contains(@class,'rgs-menu')]/li/*[contains(text(),'Страхование')]")).click();
        driver.findElement(By.xpath("//*[contains(text(),'ДМС')]")).click();
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]"))));
        driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));
    }



    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Object[][]data = new Object[][]{
                {"Иванов", "Иван", "Иванович"},{"Петрова", "Анна", "Владимировна"},{"Путин", "Владимир", "Владимирович"}
        };
        return Arrays.asList(data);
    }



    @Test
    public void testInsurance() throws Exception {

        fillField(By.name("LastName"), lastName);
        fillField(By.name("FirstName"), firstName);
        fillField(By.name("MiddleName"), middleName);

        assertEquals(lastName, driver.findElement(By.name("LastName")).getAttribute("value"));
        assertEquals(middleName, driver.findElement(By.name("MiddleName")).getAttribute("value"));
        assertEquals(firstName, driver.findElement(By.name("FirstName")).getAttribute("value"));
    }
    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

    private void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }


}
