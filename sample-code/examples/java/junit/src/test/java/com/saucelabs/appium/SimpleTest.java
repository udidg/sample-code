package com.saucelabs.appium;

import com.oracle.javafx.jmx.json.JSONException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.SystemClock;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Simple <a href="https://github.com/appium/appium">Appium</a> test which runs against a local Appium instance deployed
 * with the 'TestApp' iPhone project which is included in the Appium source distribution.
 *
 * @author Ross Rowe
 */
public class SimpleTest {

    private AppiumDriver driver;

    private List<Integer> values;

    private static final int MINIMUM = 0;
    private static final int MAXIMUM = 10;

    @Before
    public void setUp() throws Exception {
        // set up appium
        System.out.println("Ruuning Appium Test Client");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium-version", "1.0");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "7.1");

        //capabilities.setCapability("udid","becfbcdc585a8d9089c7061a43b4054b7b4b88f5");
        //capabilities.setCapability("deviceName", "Udi Dagan's iPod");
        capabilities.setCapability("app", "com.liveperson.sample.udi.demoApp");



        capabilities.setCapability("deviceName", "iPhone 5s");
        capabilities.setCapability("app", "/Users/udid/Library/Developer/Xcode/DerivedData/EcoSmart-cusbojosskpxlidyhpenbproiwri/Build/Products/Debug-iphonesimulator/EcoSmart.app");
        //capabilities.setCapability("app", "/Users/udid/Library/Developer/Xcode/DerivedData/helloWorld-btdbulvfchgwqbhicvxcmlfyvdnk/Build/Products/Debug-iphonesimulator/helloWorld.app");
        driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void populate() {
        //populate text fields with two random number
        List<WebElement> elems = driver.findElements(By.className("UIATextField"));
        Random random = new Random();
        for (WebElement elem : elems) {
            int rndNum = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
            elem.sendKeys(String.valueOf(rndNum));
            values.add(rndNum);
        }
    }

    private Point getCenter(WebElement element) {

      Point upperLeft = element.getLocation();
      Dimension dimensions = element.getSize();
      return new Point(upperLeft.getX() + dimensions.getWidth()/2, upperLeft.getY() + dimensions.getHeight()/2);
    }



    @Test
    public void testUIComputationEcoSmart() throws Exception {


        try{
            driver.switchTo().alert().accept();
        }catch (Exception e){
            System.out.println("Alert wasn't found");
        }

        try{
            driver.switchTo().alert().accept();
        }catch (Exception e){
            System.out.println("Alert wasn't found");
        }
        //Settings page:
        driver.findElement(By.name("ESFMasterViewController.settingsButton")).click();
        driver.findElement(By.name("Staging")).click();
            //Type account no'
        WebElement webElement = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[1]/UIATextField[1]"));
        logElement(webElement);
        if (!webElement.getText().equals("43244280")){

            webElement.sendKeys("43244280");
        }

            //Type skill
        webElement = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIAImage[2]/UIATextField[1]"));
        logElement(webElement);
        if (!webElement.getText().equals("mobile")) {
            webElement.sendKeys("mobile");
        }
            //Save and relaunch visit
        driver.findElement(By.name("ESFSettingsViewController.saveButton")).click();

        //Enter product page
        driver.findElement(By.name("ZETA")).click();

        //Contact A Representitive Button - Start Chat
        driver.findElement(By.name("ESFDetailViewController.helpButton")).click();
        driver.findElement(By.name("Next")).click();

        //Write your name
        webElement = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAScrollView[1]/UIAScrollView[2]/UIATextField[1]"));
        logElement(webElement);
        if (webElement.getText().equals("")){
            webElement.sendKeys("Test User");
        }
        //Go Next
        webElement = driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[3]/UIAKeyboard[1]/UIAButton[4]"));
        webElement.click();


        //Go next
        webElement = driver.findElement(By.name("Done"));
        webElement.click();

        driver.findElement(By.name("Chat Entry")).sendKeys("Hello");
        driver.findElement(By.name("Send")).click();
        //driver.findElement(By.name("Chat Entry")).sendKeys("I would like to get some ");
        //driver.findElement(By.name("Chat Entry")).sendKeys("information about ZETA");
        //driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAButton[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.name("Chat Entry")).sendKeys("You are repeating yourself..");
        driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAButton[3]")).sendKeys("what??");
        driver.findElement(By.name("Menu")).click();
        driver.findElement(By.name("End Session")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        printAllElements();

        //driver.swipe(300,200,120,200,1);
        //driver.switchTo().activeElement();
        driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[2]/UIAScrollView[1]/UIAScrollView[1]/UIAButton[6]")).click();
        driver.findElement(By.name("Yes")).click();



        Thread.sleep(10000);

    }



    //@Test
    public void testUIComputation() throws Exception {
        //for (int i=0;i<3;i++){
            driver.findElement(By.name("Press Here")).click();
        //}
        List<WebElement> buttons = driver.findElements(By.className("UIAButton"));
        List<WebElement> textFields = driver.findElements(By.className("UIATextField"));
        logElements(buttons);


        driver.findElement(By.name("Live Chat")).click();
        driver.findElement(By.name("Next")).click();
        logElement(driver.findElement(By.xpath("//UIAApplication/UIAWindow[2]/UIAElement")));

        driver.findElement(By.name("Done")).click();
        driver.findElement(By.name("Chat Entry")).sendKeys("Hi");

        driver.findElement(By.xpath("Send")).click();

        Thread.sleep(10000);

    }

    private void printAllElements (){
        System.out.println("Printing all elements");
        String pageSource = driver.getPageSource();
        System.out.println(pageSource);
        try {
            org.json.JSONObject xmlJSONObj = XML.toJSONObject(pageSource);
            String jsonPrettyPrintString = xmlJSONObj.toString(4);
            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }

    private void  logElements (List<WebElement> webElementList){
        webElementList.forEach(this::logElement);
    }

    private void logElement (WebElement we){
        System.out.printf("Found new element:\n");
        System.out.printf("Name: " + we.getAttribute("name") + "\n");
    }

    //@Test
    public void testActive() throws Exception {
        WebElement text = driver.findElement(By.xpath("//UIATextField[1]"));
        assertTrue(text.isDisplayed());

        WebElement button = driver.findElement(By.xpath("//UIAButton[1]"));
        assertTrue(button.isDisplayed());
    }

    //@Test
    public void testBasicAlert() throws Exception {
        driver.findElement(By.xpath("//UIAButton[2]")).click();

        Alert alert = driver.switchTo().alert();
        //check if title of alert is correct
        assertEquals("Cool title this alert is so cool.", alert.getText());
        alert.accept();
    }

    //@Test
    public void testBasicButton() throws Exception {
        WebElement button = driver.findElement(By.xpath("//UIAButton[1]"));
        assertEquals("ComputeSumButton", button.getText());
    }

    //@Test
    public void testClear() throws Exception {
        WebElement text = driver.findElement(By.xpath("//UIATextField[1]"));
        text.sendKeys("12");
        text.clear();

        assertEquals("", text.getText());
    }

    //@Test
    public void testHideKeyboard() throws Exception {
        driver.findElement(By.xpath("//UIATextField[1]")).sendKeys("12");

        WebElement button = driver.findElement(MobileBy.AccessibilityId("Done"));
        assertTrue(button.isDisplayed());

        button.click();
    }

    //@Test
    public void testFindElementByClassName() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElementByClassName("UIATextField");
        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        driver.findElementByClassName("UIAButton").click();

        // is sum equal ?
        WebElement sumLabel = driver.findElementByClassName("UIAStaticText");
        assertEquals(String.valueOf(number), sumLabel.getText());
    }

    //@Test
    public void testFindElementsByClassName() throws Exception {
      Random random = new Random();

      WebElement text = driver.findElementsByClassName("UIATextField").get(1);
      int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
      text.sendKeys(String.valueOf(number));

      driver.findElementByClassName("UIAButton").click();

      // is sum equal ?
      WebElement sumLabel = driver.findElementsByClassName("UIAStaticText").get(0);
      assertEquals(String.valueOf(number), sumLabel.getText());
    }

    //@Test
    public void testAttribute() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElement(By.xpath("//UIATextField[1]"));

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        assertEquals("TextField1", text.getAttribute("name"));
        assertEquals("TextField1", text.getAttribute("label"));
        assertEquals(String.valueOf(number), text.getAttribute("value"));
    }

    //@Test
    public void testSlider() throws Exception {
        //get the slider
        WebElement slider = driver.findElement(By.xpath("//UIASlider[1]"));
        assertEquals("50%", slider.getAttribute("value"));
        Point sliderLocation = getCenter(slider);
        driver.swipe(sliderLocation.getX(), sliderLocation.getY(), sliderLocation.getX()-100, sliderLocation.getY(), 1000);

        assertEquals("0%", slider.getAttribute("value"));
    }

    //@Test
    public void testLocation() throws Exception {
        WebElement button = driver.findElement(By.xpath("//UIAButton[1]"));

        Point location = button.getLocation();

        assertEquals(94, location.getX());
        assertEquals(122, location.getY());
    }

    //@Test
    public void testSessions() throws Exception {
        HttpGet request = new HttpGet("http://localhost:4723/wd/hub/sessions");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(EntityUtils.toString(entity));

        String sessionId = driver.getSessionId().toString();
        assertEquals(jsonObject.get("sessionId"), sessionId);
    }

    //@Test
    public void testSize() {
        Dimension text1 = driver.findElement(By.xpath("//UIATextField[1]")).getSize();
        Dimension text2 = driver.findElement(By.xpath("//UIATextField[2]")).getSize();
        assertEquals(text1.getWidth(), text2.getWidth());
        assertEquals(text1.getHeight(), text2.getHeight());
    }
}
