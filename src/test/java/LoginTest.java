import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.InventoryPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Nested
    @DisplayName("Casos positivos")
    class PositiveLoginTests {

        // Comprueba que el usuario estándar accede al catálogo de productos.
        @Test
        void standardUserCanLogIn() {
            assertSuccessfulLogin("standard_user");
        }

        // Comprueba que el usuario de prueba con productos problemáticos puede iniciar sesión.
        @Test
        void problemUserCanLogIn() {
            assertSuccessfulLogin("problem_user");
        }

        // Comprueba que el usuario con retrasos de rendimiento puede iniciar sesión.
        @Test
        void performanceGlitchUserCanLogIn() {
            assertSuccessfulLogin("performance_glitch_user");
        }

        // Comprueba que el usuario visual también accede al catálogo de productos.
        @Test
        void visualUserCanLogIn() {
            assertSuccessfulLogin("visual_user");
        }
    }

    @Nested
    @DisplayName("Casos negativos")
    class NegativeLoginTests {

        // Comprueba que una contraseña incorrecta muestra el mensaje de error esperado.
        @Test
        void incorrectPasswordShowsError() {
            loginPage.login("standard_user", "wrong_password");

            assertEquals(
                    "Epic sadface: Username and password do not match any user in this service",
                    loginPage.getErrorMessage(),
                    "An incorrect password should show the login error."
            );
        }

        // Comprueba que enviar el formulario vacío informa que falta el nombre de usuario.
        @Test
        void emptyFieldsShowUsernameRequiredError() {
            loginPage.login("", "");

            assertEquals(
                    "Epic sadface: Username is required",
                    loginPage.getErrorMessage(),
                    "Submitting empty fields should show that the username is required."
            );
        }
    }

    private void assertSuccessfulLogin(String username) {
        InventoryPage inventoryPage = loginPage.login(username, "secret_sauce");
        String actualTitle = inventoryPage.getTitle();
        assertEquals("Products", actualTitle,
                "The inventory page should display the title 'Products'.");
    }
}
