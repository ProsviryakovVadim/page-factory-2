package ru.sbtqa.tag.pagefactory;

import java.lang.reflect.Field;
import java.util.Map;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.annotations.ElementTitle;
import ru.sbtqa.tag.pagefactory.exceptions.ElementDescriptionException;
import static ru.sbtqa.tag.pagefactory.util.PageFactoryUtils.getElementByField;

/**
 * Common reflection utils as static methods from Page.Core
 *
 * May be it should be in entry_point module
 *
 */
public class ReflectionUtil {
    
    public static final Logger LOG = LoggerFactory.getLogger(ReflectionUtil.class);
    
    /**
     * Search for the given WebElement in page repository storage, that is being
     * generated during preconditions to all tests. If element is found, return
     * its title annotation. If nothing found, log debug message and return
     * toString() of corresponding element
     *
     * @param element WebElement to search
     * @param page page for searching
     * @return title of the given element
     */
    public static String getElementTitle(Page page, WebElement element) {
        for (Map.Entry<Field, String> entry : PageFactory.getPageRepository().get(page.getClass()).entrySet()) {
            try {
                if (getElementByField(page, entry.getKey()) == element) {
                    ElementTitle elementTitle = entry.getKey().getAnnotation(ElementTitle.class); 
                    if (elementTitle != null && !elementTitle.value().isEmpty()) { 
                        return elementTitle.value(); 
                    }
                    return entry.getValue();
                }
            } catch (java.util.NoSuchElementException | StaleElementReferenceException | ElementDescriptionException ex) {
                LOG.debug("Failed to get element '" + element + "' title", ex);
            }
        }
        return element.toString();
    }
}
 

