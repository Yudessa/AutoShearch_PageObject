package org.example.tests;

import org.example.pages.MainPage;
import org.example.pages.ResultsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BingSearchTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private ResultsPage rp;
    private MainPage mp;

    @BeforeEach
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();

        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://www.bing.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        rp = new ResultsPage(driver);
        mp = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка перехода на selenium.dev")
    public void searchResultsTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.sendText(input);
        rp.clickElement(0);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.urlContains("selenium.dev"));
        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("selenium.dev"), "Неверный URL");
    }

    @Test
    @DisplayName("поиск текста Selenium")
    public void searchFieldTest() {
        String input = "Selenium";
        mp.sendText(input);
        assertEquals(input, rp.getTextFromSearchField(), "Текст не совпал");
    }
}