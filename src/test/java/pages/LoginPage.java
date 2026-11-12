package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private static final String URL = "https://www.saucedemo.com/";
    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
    }

    public InventoryPage login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD)).sendKeys(username);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return new InventoryPage(driver);
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE)).getText();
    }
}
