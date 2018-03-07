package ru.sbtqa.tag.pagefactory;

import io.appium.java_client.AppiumDriver;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.qautils.properties.Props;
import ru.sbtqa.tag.videorecorder.VideoRecorder;

/**
 * Общая информация о контексте теста
 */
public class PageFactory {

    private static final Logger LOG = LoggerFactory.getLogger(PageFactory.class);

    private static final Map<Class<? extends WebElementsPage>, Map<Field, String>> PAGES_REPOSITORY = new HashMap<>();

    private static Actions actions;
    private static PageManager pageManager;
    private static VideoRecorder videoRecorder;
    private static boolean aspectsDisabled = false;

    private static final String PAGES_PACKAGE = Props.get("page.package");
    private static final String TIMEOUT = Props.get("page.load.timeout");


    public static void initElements(WebDriver driver, Object page) {
        org.openqa.selenium.support.PageFactory.initElements(driver, page);
    }

    public static void initElements(FieldDecorator decorator, Object page) {
        org.openqa.selenium.support.PageFactory.initElements(decorator, page);
    }

    /**
     * Get PageFactory instance
     *
     * @return PageFactory
     */
    public static PageManager getInstance() {
        if (null == pageManager) {
            pageManager = new PageManager(PAGES_PACKAGE);
        }
        return pageManager;
    }

    /**
     * Get driver actions
     *
     * @return Actions
     */
    public static Actions getActions() {
        if (null == actions) {
            actions = new Actions(PageContext.getCurrentPage().getDriver());
        }
        return actions;
    }

    /**
     * @return the pagesPackage
     */
    public static String getPagesPackage() {
        return PAGES_PACKAGE;
    }

    /**
     * @return the timeOut
     */
    public static int getTimeOut() {
        return Integer.parseInt(TIMEOUT);
    }

    /**
     * @return the timeOut
     */
    public static int getTimeOutInSeconds() {
        return Integer.parseInt(TIMEOUT) / 1000;
    }

    /**
     * @return the pageRepository
     */
    public static Map<Class<? extends WebElementsPage>, Map<Field, String>> getPageRepository() {
        return PAGES_REPOSITORY;
    }

    /**
     * Affects click and sendKeys aspects only
     *
     * @return the aspectsDisabled default false
     */
    public static boolean isAspectsDisabled() {
        return aspectsDisabled;
    }

    /**
     * Affects click and sendKeys aspects only
     *
     * @param aAspectsDisabled default false
     */
    public static void setAspectsDisabled(boolean aAspectsDisabled) {
        aspectsDisabled = aAspectsDisabled;
    }

    public static void setVideoRecorderToNull() {
        videoRecorder = null;
    }

}
