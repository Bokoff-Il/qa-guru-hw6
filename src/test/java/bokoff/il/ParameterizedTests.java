package bokoff.il;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.String.format;


import com.codeborne.selenide.Configuration;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterizedTests {

  @BeforeEach
  void setUP(){
    Configuration.browserSize = "1920x1080";
    Configuration.baseUrl = "https://demoqa.com";
  }

  @ValueSource(strings = {
      "ivan",
      "qwerty1233"
  })
  @ParameterizedTest(name = "Fill name {0} from value")
  void fillFormWithValueSource(String testData){
    open("/text-box");
    $("#userName").setValue(testData);
    $("#userEmail").setValue(format("%s@gmail.com", testData));
    $("#submit").click();

    $("#name").shouldBe(text(format("Name:%s", testData)));
    $("#email").shouldBe(text(format("Email:%s@gmail.com", testData)));
  }

  @CsvSource(value = {
      "ivan,gmail.com,Moscow",
      "qwerty1233,yandex.ru,Saint-Petersburg",
  })
  @ParameterizedTest(name = "Fill name {0} and email {1} and address {2} from csv")
  void fillFormWithCsvSource(String name, String email, String address){
    open("/text-box");
    $("#userName").setValue(name);
    $("#userEmail").setValue(format("%s@%s", name, email));
    $("#currentAddress").setValue(address);
    $("#permanentAddress").setValue(address);
    $("#submit").click();

    $("#name").shouldBe(text(format("Name:%s", name)));
    $("#email").shouldBe(text(format("Email:%s@%s", name, email)));
    $("p#currentAddress").shouldBe(text(format("Current Address :%s", address)));
    $("p#permanentAddress").shouldBe(text(format("Permananet Address :%s", address)));
  }

  static Stream<Arguments> fillFormWithMethodSource() {
    return Stream.of(
        Arguments.of("ivan", "gmail.com", "Moscow"),
        Arguments.of("qwerty1233", "yandex.ru", "Saint-Petersburg")
                    );
  }

    @MethodSource("fillFormWithMethodSource")
    @ParameterizedTest(name = "Fill name {0} and email {1} abd address {2} from method")
    void fillFormWithMethodSource(String name, String email, String address){
      open("/text-box");
      $("#userName").setValue(name);
      $("#userEmail").setValue(format("%s@%s", name, email));
      $("#currentAddress").setValue(address);
      $("#permanentAddress").setValue(address);
      $("#submit").click();

      $("#name").shouldBe(text(format("Name:%s", name)));
      $("#email").shouldBe(text(format("Email:%s@%s", name, email)));
      $("p#currentAddress").shouldBe(text(format("Current Address :%s", address)));
      $("p#permanentAddress").shouldBe(text(format("Permananet Address :%s", address)));
    }
  }
