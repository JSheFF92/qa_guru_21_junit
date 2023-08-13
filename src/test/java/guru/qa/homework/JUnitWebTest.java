package guru.qa.homework;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class JUnitWebTest {

    static {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.browserCapabilities = capabilities;
        Configuration.pageLoadStrategy = "eager";
    }

    @BeforeEach
    void setUp() {
        open("https://github.com/");
    }

    @CsvSource(value = {
            "selenide | Selenide",
            "junit5 | JUnit 5"
    },
            delimiter = '|')
    @Tags({
            @Tag("smoke"),
            @Tag("web")

    })
    @DisplayName("Проверка поиса в GitHub")
    @ParameterizedTest(name = "Найденный результат поиска {0} совпадает поиском {1}")
    void successfulSearchTextTestSelenideJUnit5(String result, String searchQuery) {
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("[data-target='query-builder.input']").setValue(searchQuery).pressEnter();
        $("[data-testid=results-list]").shouldHave(text(result));
    }


    @ValueSource(
            strings = {"selenide", "junit5"}
    )
    @Tags({
            @Tag("smoke"),
            @Tag("web")

    })
    @DisplayName("Проверка поиса в Github")
    @ParameterizedTest(name = "Поиск в github для запроса {0} не пустой")
    void successfulSearchNotBeEmpty() {
        {
            $("[data-target='qbsearch-input.inputButtonText']").click();
            $("[data-target='query-builder.input']").setValue("Gradle").pressEnter();
            $$("[data-testid=results-list]").shouldHave(CollectionCondition.sizeGreaterThan(0));
        }
    }
}