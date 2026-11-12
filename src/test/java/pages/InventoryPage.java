package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private static final By PAGE_TITLE = By.className("title");
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    private final WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    public String getTitle() {
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE)).getText();
    }
}
