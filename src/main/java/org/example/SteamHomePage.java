package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions; // Добавляем импорт для наведения мыши
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SteamHomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions; // Добавляем объект Actions для наведения мыши

    // --- Локаторы для домашней страницы ---
    private By loginButton = By.xpath("//a[contains(@class, 'global_action_link') and text()='войти']");
    private By loggedInAccountPulldown = By.xpath("//div[@id='account_pulldown']");

    // Новые локаторы
    private By storeMenuButton = By.xpath("//div[@id='foryou_tab']//a[text()='Магазин']");
    private By homePageSubMenuItem = By.xpath("//div[@id='foryou_flyout']//a[text()='Главная страница']");
    private By searchInputField = By.xpath("//input[@id='store_nav_search_term']");
    private By searchButton = By.xpath("//a[@id='store_search_link']");
    private By discountsAndEventsHeader = By.xpath("//h2[text()='Скидки и мероприятия']");
    private By moreDiscountsButton = By.xpath("//h2[text()='Скидки и мероприятия']//a[contains(., 'Ещё') or contains(., 'Больше продуктов')]"); // Универсальный текст
    private By firstFeaturedGameTitle = By.xpath("//div[@id='home_maincap_v7']//div[@class='app_name']/div"); // Название первой игры в карусели

    public SteamHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.actions = new Actions(driver); // Инициализируем Actions
    }

    public void open() {
        driver.get(Constants.STEAM_BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        System.out.println("Открыта домашняя страница Steam.");
    }

    public SteamLoginPage clickLoginButton() {
        System.out.println("Нажимаем кнопку 'Войти' на домашней странице.");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.urlContains("login"));
        return new SteamLoginPage(driver, wait);
    }

    public boolean isUserLoggedIn() {
        try {
            boolean loginButtonInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(loginButton));
            boolean loggedInElementVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInAccountPulldown)).isDisplayed();
            return loginButtonInvisible && loggedInElementVisible;
        } catch (Exception e) {
            System.out.println("Не удалось подтвердить вход: " + e.getMessage());
            return false;
        }
    }

    // --- Новые методы для взаимодействия с элементами главной страницы ---

    /**
     * Наводит курсор на кнопку "Магазин" для активации выпадающего меню.
     */
    public void hoverOverStoreMenu() {
        WebElement storeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(storeMenuButton));
        actions.moveToElement(storeMenu).perform();
        System.out.println("Наведен курсор на меню 'Магазин'.");
        // Добавьте небольшую задержку, если меню появляется медленно
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePageSubMenuItem));
    }

    /**
     * Кликает по пункту "Главная страница" в выпадающем меню "Магазин".
     */
    public void clickHomePageSubMenuItem() {
        wait.until(ExpectedConditions.elementToBeClickable(homePageSubMenuItem)).click();
        System.out.println("Клик по пункту 'Главная страница' в подменю 'Магазин'.");
    }

    /**
     * Вводит текст в поле поиска.
     */
    public void enterSearchTerm(String term) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputField));
        searchInput.clear();
        searchInput.sendKeys(term);
        System.out.println("Введен текст поиска: " + term);
    }

    /**
     * Нажимает кнопку поиска.
     */
    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        System.out.println("Нажата кнопка поиска.");
    }

    /**
     * Проверяет видимость заголовка "Скидки и мероприятия".
     */
    public boolean isDiscountsAndEventsHeaderDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(discountsAndEventsHeader)).isDisplayed();
    }

    /**
     * Кликает по кнопке "Ещё" или "Больше продуктов" в секции "Скидки и мероприятия".
     */
    public void clickMoreDiscountsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(moreDiscountsButton)).click();
        System.out.println("Нажата кнопка 'Ещё/Больше продуктов' в секции скидок.");
    }

    /**
     * Получает название первой игры в карусели "Популярное и рекомендуемое".
     */
    public String getFirstFeaturedGameTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstFeaturedGameTitle)).getText();
    }
}