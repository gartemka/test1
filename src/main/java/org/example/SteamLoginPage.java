package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SteamLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для страницы входа (используем улучшенные XPath)
    private By usernameField = By.xpath("//div[text()='Войдите, используя имя аккаунта']/following-sibling::input[@type='text']");
    private By passwordField = By.xpath("//div[text()='Пароль']/following-sibling::input[@type='password']");
    private By signInButton = By.xpath("//button[@type='submit' and text()='Войти']");

    // Локатор для сообщения об ошибке
    private By errorMessage = By.xpath("//div[@class='login_signin_error' and (contains(text(), '" + Constants.ERROR_MESSAGE_PART_RU + "') or contains(text(), '" + Constants.ERROR_MESSAGE_PART_EN + "'))]");

    public SteamLoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        // При создании объекта SteamLoginPage убедимся, что мы на странице логина
        wait.until(ExpectedConditions.urlContains("login"));
        System.out.println("Создан Page Object SteamLoginPage. Текущий URL: " + driver.getCurrentUrl());
    }

    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
        System.out.println("Введено имя пользователя.");
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
        System.out.println("Введен пароль.");
    }

    public void clickSignInButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
        System.out.println("Нажата кнопка 'Войти'.");
    }

    /**
     * Выполняет полную операцию входа на странице.
     */
    public void login(String username, String password) {
        System.out.println("Попытка входа с именем: " + username);
        enterUsername(username);
        enterPassword(password);
        clickSignInButton();
    }

    /**
     * Получает текст сообщения об ошибке.
     */
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    /**
     * Проверяет, отображается ли сообщение об ошибке.
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            // Если элемент не найден в течение таймаута, значит, ошибка не отображается
            return false;
        }
    }

    /**
     * Открывает страницу логина напрямую (полезно для изоляции тестов).
     */
    public void openLoginPage() {
        driver.get(Constants.STEAM_LOGIN_URL); // Используем константу
        wait.until(ExpectedConditions.urlContains("login"));
        System.out.println("Открыта страница входа напрямую: " + driver.getCurrentUrl());
    }
}