package com.deque.tests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;

public class DequeDemoTest {
	
	String URL ="https://dequeuniversity.com/demo/mars";
	WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		this.driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void testDeque() {
		driver.get(URL);
		
		//Explicit wait for  main-nav to load
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#main-nav")));
		
		
		// Validate number of radio buttons under “Let the Adventure Begin” is 5
		int radioButtons = driver.findElements(By.xpath("//div[@class='interior-container']//input[@name='widget-type']")).size();
		assertEquals(radioButtons, 5);
		
		
		//Validate that clicking “add a traveler” adds another select to the page
		driver.findElement(By.linkText("Add A Traveler")).click();
		assertTrue(driver.findElement(By.id("r-passenger1")).isDisplayed());
		
		//Validate that when clicking the arrows under the video, the heading changes
		String videoText = driver.findElement(By.id("video-text")).getText();
		assertEquals(videoText,"Life was possible on Mars");
		String rightArrowxpath ="//body/section[@id='content']/div[@id='right-column']/div[@id='video-box']/div[2]/div[3]/i[1]";
		driver.findElement(By.xpath(rightArrowxpath)).click();
		videoText = driver.findElement(By.id("video-text")).getText();
		assertEquals(videoText,"Why Mars died");
		
	}
	
	@Test
	public void testAccessibility() {
		 driver.get(URL);
		 AxeBuilder builder = new AxeBuilder();
		 Results results = builder.analyze(driver);
		 List<Rule> violations = results.getViolations();
		 if (violations.size() == 0)
		    {
		        Assert.assertTrue(true, "No violations found");
		    }
		    else
		    {
		      
		        for(int i =0; i<violations.size();i++) {
		        	System.out.println(violations.get(i));
		        }
		        Assert.assertEquals(violations.size(), 0, violations.size() + " violations found"); 
		    }
	}

}
