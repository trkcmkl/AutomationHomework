package com.prestashop.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestaShopNegativeScenarios {
	WebDriver dr;

	@BeforeMethod
	public void setUp() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		dr = new ChromeDriver();
		dr.switchTo();
		dr.manage().window().fullscreen();
		Thread.sleep(1000);
	}

	@Test
	public void testWrongCredentials() {

		dr.get("http://automationpractice.com");
		dr.findElement(By.xpath("//a[@class='login']")).click();
		String email = "trying@gmail.com";
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[2]"))
				.sendKeys(email);
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[3]"))
				.sendKeys("123456");
		dr.findElement(By.xpath("//p[@class='submit']//span")).click();
		Assert.assertEquals(dr.findElement(By.xpath("//div[@class='alert alert-danger']//ol//li")).getText(),
				"Authentication failed.");
	}

	@Test
	public void testInvalidEmail() {
		dr.get("http://automationpractice.com");
		dr.findElement(By.xpath("//a[@class='login']")).click();
		String email = "tryingmail.com";
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[2]"))
				.sendKeys(email);
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[3]"))
				.sendKeys("123456");
		dr.findElement(By.xpath("//p[@class='submit']//span")).click();
		Assert.assertEquals(dr.findElement(By.xpath("//div[@class='alert alert-danger']//ol//li")).getText(),
				"Invalid email address.");
	}

	@Test
	public void testBlankEmail() {
		dr.get("http://automationpractice.com");
		dr.findElement(By.xpath("//a[@class='login']")).click();
		String email = "";
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[2]"))
				.sendKeys(email);
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[3]"))
				.sendKeys("123456");
		dr.findElement(By.xpath("//p[@class='submit']//span")).click();
		Assert.assertEquals(dr.findElement(By.xpath("//div[@class='alert alert-danger']//ol//li")).getText(),
				"An email address required.");
	}

	@Test
	public void testBlankPassword() {
		dr.get("http://automationpractice.com");
		dr.findElement(By.xpath("//a[@class='login']")).click();
		String email = "trying@gmail.com";
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[2]"))
				.sendKeys(email);
		dr.findElement(By.xpath("(//input[@class='is_required validate account_input form-control'])[3]")).sendKeys("");
		dr.findElement(By.xpath("//p[@class='submit']//span")).click();
		Assert.assertEquals(dr.findElement(By.xpath("//div[@class='alert alert-danger']//ol//li")).getText(),
				"Password is required.");

	}

	@AfterMethod
	public void closeUp() throws InterruptedException {
		Thread.sleep(2000);
		dr.close();
	}

}
