package pagefactory.mobile.pages.auth;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.sbtqa.tag.pagefactory.mobile.MobilePage;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.annotations.PageEntry;

@PageEntry(title = "Страница авторизации. Ввод email")
public class EmailPage extends MobilePage {

    @ElementTitle(value = "Электронная почта")
    @FindBy(id = "ru.sberbank.sbt.eso:id/email")
    protected WebElement nptEmail;

    @ElementTitle(value = "ОК")
    @FindBy(id = "ru.sberbank.sbt.eso:id/request_pin")
    protected WebElement btnOk;

    public EmailPage(WebDriver driver) {
        super(driver);
    }
}
