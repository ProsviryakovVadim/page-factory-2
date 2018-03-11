package ru.sbtqa.tag.pagefactory.extensions;

import java.util.List;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sbtqa.tag.pagefactory.PageContext;
import ru.sbtqa.tag.pagefactory.exceptions.SwipeException;
import ru.sbtqa.tag.qautils.strategies.DirectionStrategy;
import ru.sbtqa.tag.qautils.strategies.MatchStrategy;

public class MobileExtension {

    private static final Logger LOG = LoggerFactory.getLogger(MobileExtension.class);
    
    private static final int DEFAULT_SWIPE_TIME = 3000;
    private static final int DEFAULT_SWIPE_DEPTH = 256;
    private static final double INDENT_BOTTOM = 0.80;
    private static final double INDENT_TOP = 0.20;
    private static final double INDENT_LEFT = 0.30;
    private static final double INDENT_RIGHT = 0.70;

    /**
     * Swipe element to direction
     *
     * @param element element to swipe
     * @param direction swipe direction
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipe(WebElement element, DirectionStrategy direction) throws SwipeException {
	swipe(element, direction, DEFAULT_SWIPE_TIME);
    }

    /**
     * Swipe element to direction
     *
     * @param element taget element to swipe
     * @param direction swipe direction
     * @param time how fast element should be swiped
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipe(WebElement element, DirectionStrategy direction, int time) throws SwipeException {
	Dimension size = element.getSize();
	Point location = element.getLocation();
	swipe(location, size, direction, time);
    }

    /**
     * Swipe screen to direction
     *
     * @param direction swipe direction
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipe(DirectionStrategy direction) throws SwipeException {
	swipe(direction, DEFAULT_SWIPE_TIME);
    }

    /**
     * Swipe screen to direction
     *
     * @param direction swipe direction
     * @param time how fast screen should be swiped
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipe(DirectionStrategy direction, int time) throws SwipeException {
	Dimension size = PageContext.getCurrentPage().getDriver().manage().window().getSize();
	swipe(new Point(0, 0), size, direction, time);
    }

    /**
     * Swipe to direction
     *
     * @param location top left-hand corner of the element
     * @param size width and height of the element
     * @param direction swipe direction
     * @param time how fast screen should be swiped
     * @throws SwipeException if there is an error while swiping
     */
    private static void swipe(Point location, Dimension size, DirectionStrategy direction, int time) throws SwipeException {
	int startx, endx, starty, endy;
	switch (direction) {
	    case DOWN:
		startx = endx = size.width / 2;
		starty = (int) (size.height * INDENT_BOTTOM);
		endy = (int) (size.height * INDENT_TOP);
		break;
	    case UP:
		startx = endx = size.width / 2;
		starty = (int) (size.height * INDENT_TOP);
		endy = (int) (size.height * INDENT_BOTTOM);
		break;
	    case RIGHT:
		startx = (int) (size.width * INDENT_RIGHT);
		endx = (int) (size.width * INDENT_LEFT);
		starty = endy = size.height / 2;
		break;
	    case LEFT:
		startx = (int) (size.width * INDENT_LEFT);
		endx = (int) (size.width * INDENT_RIGHT);
		starty = endy = size.height / 2;
		break;
	    default:
		throw new SwipeException("Failed to swipe to direction " + direction);
	}

	int x = location.getX();
	int y = location.getY();
	LOG.debug("Swipe parameters: location {}, dimension {}, direction {}, time {}", location, size, direction, time);
	((AppiumDriver) PageContext.getCurrentPage().getDriver()).swipe(x + startx, y + starty, x + endx, y + endy, time);
    }

    /**
     * Swipe until the text becomes visible
     * 
     * @param direction swipe direction
     * @param text text on page to swipe to
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipeToText(DirectionStrategy direction, String text) throws SwipeException { 
	swipeToText(direction, text, MatchStrategy.EXACT);
    }
    
    /**
     * Swipe until the text becomes visible
     * 
     * @param direction swipe direction
     * @param text text on page to swipe to
     * @param strategy contains or exact
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipeToText(DirectionStrategy direction, String text, MatchStrategy strategy) throws SwipeException { 
	swipeToText(direction, text, MatchStrategy.EXACT, DEFAULT_SWIPE_DEPTH);
    }
    
    /**
     * Swipe until the text becomes visible
     * 
     * @param direction swipe direction
     * @param text text on page to swipe to
     * @param strategy contains or exact
     * @param depth the amount of swipe action
     * @throws SwipeException if there is an error while swiping
     */
    public static void swipeToText(DirectionStrategy direction, String text, MatchStrategy strategy, int depth) throws SwipeException {
	for (int depthCounter = 0; depthCounter < depth; depthCounter++) {
	    String oldPageSource = PageContext.getCurrentPage().getDriver().getPageSource();
	    switch (strategy) {
		case EXACT:
		    if (((AppiumDriver) PageContext.getCurrentPage().getDriver()).findElementsByXPath("//*[@text='" + text + "']").size() > 0) {
			return;
		    }
		case CONTAINS:
		    List<WebElement> textViews = ((AppiumDriver) PageContext.getCurrentPage().getDriver()).findElementsByClassName("android.widget.TextView");
		    if (textViews.size() > 0) {
			for (WebElement textView : textViews) {
			    if (textView.getText().contains(text)) {
				return;
			    }
			}
		    }
	    }
	    swipe(direction);

	    if (PageContext.getCurrentPage().getDriver().getPageSource().equals(oldPageSource)) {
		throw new SwipeException("Swiping limit is reached. Text not found");
	    }
	}

	throw new SwipeException("Swiping depth is reached. Text not found");
    }
}
