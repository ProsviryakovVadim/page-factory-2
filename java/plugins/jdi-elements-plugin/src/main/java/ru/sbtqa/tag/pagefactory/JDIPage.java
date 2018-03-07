package ru.sbtqa.tag.pagefactory;


import com.epam.jdi.uitests.web.selenium.elements.complex.Dropdown;
import org.openqa.selenium.WebDriver;
import ru.sbtqa.tag.pagefactory.annotations.ActionTitle;
import ru.sbtqa.tag.pagefactory.exceptions.PageException;
import ru.sbtqa.tag.pagefactory.util.PageFactoryUtils;

/**
 * Contains basic actions in particular with jdi elements
 * If we want to extend this functional - inherit from this class
 */
public class JDIPage extends WebElementsPage {

    public JDIPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Find element with specified title annotation in dropdown, and select given option
     * Add elementTitle-&gt;text as parameter-&gt;value to corresponding step in
     * allure report
     *
     * @param elementTitle dropdown element
     * @param value option text
     * @throws ru.sbtqa.tag.pagefactory.exceptions.PageException if page was not
     * initialized, or required element couldn't be found
     */
    @ActionTitle("ru.sbtqa.tag.pagefactory.select.dropdown.value")
    public void selectDropdownValue(String elementTitle, String value) throws PageException {
        Object element = JDIUtils.getElementByTitle(PageContext.getCurrentPage(), elementTitle);
        ((Dropdown) element).select(value);

        PageFactoryUtils.addToReport(elementTitle, value);
    }


}
