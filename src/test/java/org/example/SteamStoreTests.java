package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SteamStoreTests extends BaseTest {

    @Test
    @DisplayName("Тест: Проверка поиска игры на главной странице")
    void testSearchFunctionality() {
        SteamHomePage homePage = new SteamHomePage(driver, wait);
        // homePage.open(); // Страница уже открыта в BaseTest.setUp()

        String searchTerm = "Red Dead Redemption 2"; // Название для поиска
        homePage.enterSearchTerm(searchTerm);
        homePage.clickSearchButton();

        // Проверка: Убеждаемся, что мы перешли на страницу поиска и в URL есть искомый термин
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("search/?term=" + searchTerm.replace(" ", "+")));
        System.out.println("✓ Перешли на страницу поиска.");

        // Дополнительная проверка: можно убедиться, что результаты поиска содержат нужную игру
        // Для этого нужно будет добавить локатор на странице поиска (не на главной)
        // Например, на странице поиска ищем //a[contains(@class, 'search_result_row')]//span[@class='title' and text()='Red Dead Redemption 2']
        // Но это уже для отдельного Page Object 'SteamSearchPage'
    }
    //------------------------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Тест: Проверка навигации по меню 'Магазин' -> 'Главная страница'")
    void testStoreMenuNavigation() {
        SteamHomePage homePage = new SteamHomePage(driver, wait);
        // homePage.open(); // Страница уже открыта

        homePage.hoverOverStoreMenu(); // Наводим курсор
        homePage.clickHomePageSubMenuItem(); // Кликаем по пункту в подменю

        // Проверка: Убеждаемся, что мы вернулись на главную страницу (URL должен быть базовым)
        Assertions.assertEquals(Constants.STEAM_BASE_URL, driver.getCurrentUrl(), "Ошибка: Не удалось перейти на Главную страницу из меню 'Магазин'.");
        System.out.println("✓ Успешно перешли на Главную страницу через меню.");
    }
    //------------------------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Тест: Проверка заголовка и кнопки 'Скидки и мероприятия'")
    void testDiscountsSection() {
        SteamHomePage homePage = new SteamHomePage(driver, wait);
        // homePage.open(); // Страница уже открыта

        // Проверяем, что заголовок "Скидки и мероприятия" виден
        Assertions.assertTrue(homePage.isDiscountsAndEventsHeaderDisplayed(), "Ошибка: Заголовок 'Скидки и мероприятия' не отображается.");
        System.out.println("✓ Заголовок 'Скидки и мероприятия' виден.");

        // Кликаем по кнопке "Ещё/Больше продуктов"
        homePage.clickMoreDiscountsButton();

        // Проверяем, что URL изменился на страницу скидок
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("specials"));
        System.out.println("✓ Успешно перешли на страницу скидок.");
    }
    //------------------------------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Тест: Проверка названия первой игры в карусели")
    void testFirstFeaturedGameTitle() {
        SteamHomePage homePage = new SteamHomePage(driver, wait);
        // homePage.open(); // Страница уже открыта

        String expectedTitle = "Red Dead Redemption 2"; // Название игры, которое мы ожидаем
        String actualTitle = homePage.getFirstFeaturedGameTitle();

        Assertions.assertEquals(expectedTitle, actualTitle, "Ошибка: Название первой игры в карусели не соответствует ожидаемому.");
        System.out.println("✓ Название первой игры в карусели: '" + actualTitle + "' соответствует ожидаемому: '" + expectedTitle + "'.");
    }
}