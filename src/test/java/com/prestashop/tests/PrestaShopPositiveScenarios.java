package com.prestashop.tests;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.github.javafaker.PhoneNumber;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestaShopPositiveScenarios {

	WebDriver dr;
	Faker f =new Faker();

	@BeforeMethod
	public void setUp() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		dr = new ChromeDriver();
		dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		dr.switchTo();
		dr.manage().window().fullscreen();
		Thread.sleep(1000);
		
	}
	@Test
	public void positiveScenario() throws InterruptedException {
		
		dr.get("http://automationpractice.com");
		dr.findElement(By.xpath("//a[@class='login']")).click();
		String fakeEmail=f.internet().emailAddress();
		dr.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(fakeEmail);
		dr.findElement(By.xpath("//div[@class='submit']//button//span")).click();
		Thread.sleep(500);
		Thread.sleep(2000);
		dr.findElement(By.id("uniform-id_gender"+f.number().numberBetween(1, 2))).click();
		String Firstname=f.name().firstName();
		dr.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(Firstname);
		String Lastname= f.name().lastName();
		dr.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys(Lastname);
		dr.findElement(By.xpath("(//input[@type='text'])[4]")).clear();
		dr.findElement(By.xpath("(//input[@type='text'])[4]")).sendKeys(fakeEmail);
		String password= f.number().digits(7);
		dr.findElement(By.xpath("(//input[@type='password'])")).sendKeys(password);
//		-------creating real random date
		Date start= Date.valueOf("1900-01-01");
		Date finish=Date.valueOf(LocalDate.of(2000, 06, 22)); //only accessible for older people then 18 years old		
		String date = f.date().between(start, finish).toString();
//		-------converting month to int
		String months[]= {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		int month=0;
		for(int i=0; i<12; i++) {
			if(date.substring(4, 7).equals(months[i])) {
				month=i+1;			
			}
		}	
//		--------
//		--------getting xpath order of year
		int minyear=1900;
		int maxYearPathNum=120;
		Integer year=0;
		year=Integer.valueOf(date.substring(24,28));
		int YearPathNum=maxYearPathNum-(year-minyear);
//		System.out.println(YearPathNum);
//		--------getting the xpath order of days
		Integer daysvalue =0;
		daysvalue= Integer.valueOf(date.substring(8,10))+1;
//		--------
		dr.findElement(By.xpath("(//select[@id='months'])//option["+(month+1)+"]")).click();
		dr.findElement(By.xpath("(//select[@id='days'])//option["+daysvalue+"]")).click();
		dr.findElement(By.xpath("(//select[@id='years'])//option["+(YearPathNum)+"]")).click();
		
//		dr.findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys(Firstname);
//		dr.findElement(By.xpath("(//input[@type='text'])[6]")).sendKeys(Lastname);
		String company= f.company().profession();
		dr.findElement(By.xpath("(//input[@type='text'])[7]")).sendKeys(company);
		Thread.sleep(1000);
		String adress = f.address().streetAddress();
		dr.findElement(By.name("address1")).sendKeys(adress);
		String city= f.address().city();
		dr.findElement(By.xpath("(//input[@type='text'])[10]")).sendKeys(city);
		String state= f.address().state();
		Thread.sleep(1000);
		Select select = new Select(dr.findElement(By.xpath("//select[@id='id_state']")));
		Thread.sleep(1000);
		select.selectByVisibleText(state);
		String Postalcode=f.number().digits(5);
		System.out.println(Postalcode);
		Thread.sleep(1000);
		dr.findElement(By.xpath("//input[@id='postcode']")).sendKeys(Postalcode);
		
		Thread.sleep(1000);
		String phone= f.phoneNumber().cellPhone();
		dr.findElement(By.xpath("//input[@id='phone'] ")).sendKeys(phone);
		String phone2= f.phoneNumber().cellPhone();
		dr.findElement(By.xpath("//input[@id='phone_mobile'] ")).sendKeys(phone2);
		Thread.sleep(1000);
//		dr.findElement(By.xpath("alias")).sendKeys(f.address().fullAddress());
		dr.findElement(By.xpath("//button[@id='submitAccount']")).click();
		Thread.sleep(6000);
		dr.findElement(By.xpath("//a[@class='logout']")).click();
		Thread.sleep(1000);
		dr.findElement(By.xpath("//input[@id='email'] ")).sendKeys(fakeEmail);
		dr.findElement(By.xpath("//input[@id='passwd'] ")).sendKeys(password);
		dr.findElement(By.xpath("//button[@id='SubmitLogin']")).click();
		Thread.sleep(1000);
		if(dr.findElement(By.xpath("//a[@class='account']//span")).getText().equals(Firstname+" "+Lastname)) {
			System.out.println("passed");
		}
		else {
			System.out.println("failed");
			System.out.println(Firstname+" "+Lastname);
		
		}
	}
	
//	@AfterMethod
//	public void closeUp() throws InterruptedException {
//		Thread.sleep(5000);
//		dr.close();
//	}

}
