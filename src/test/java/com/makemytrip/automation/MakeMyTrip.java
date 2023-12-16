package com.makemytrip.automation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MakeMyTrip {
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	
	
	


	

			
			
			
			WebDriverManager.chromedriver().setup();
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();

			driver.get("https://www.makemytrip.com/");

			Thread.sleep(8000);

			WebElement frame1 = driver
					.findElement(By.xpath("//iframe[@id='webklipper-publisher-widget-container-notification-frame']"));

			driver.switchTo().frame(frame1);

			driver.findElement(By.xpath("//i[@class='wewidgeticon we_close']")).click();
			// Thread.sleep(500);
			driver.switchTo().parentFrame();
			// Thread.sleep(1000);
			Thread.sleep(1000);

			
			String expected_from_city = "Bengaluru, India";
			String expcted_to_city = "New Delhi, India";
			String departure_date = "10/March/2024";

			driver.findElement(By.id("fromCity")).click();
			
			select_city(driver, expected_from_city);

			driver.findElement(By.id("toCity")).click();
			// Thread.sleep(5000);
			select_city(driver, expcted_to_city);
			Thread.sleep(800);
	        selectDate(driver, departure_date, "dd/MMM/yyyy");
	        String  adult_traveller= "3";
	        String childtraveller="2";
	        selectTraveller(driver,adult_traveller, childtraveller );
	       
			
			Thread.sleep(800);


			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Traveller']")));

			try {
				driver.findElement(By.xpath("//span[@class='lbl_input appendBottom5']")).click();
				// element.click();
			} catch (NoSuchElementException e) {
				// Handle the exception or log an error message
				System.out.println("Element not found: " + e.getMessage());
			}

			driver.findElement(
					By.xpath("//div[@class='appendBottom20']//p[text()='ADULTS (12y +)']//following::ul[1]//li["+adult_traveller+"]"))
					.click();
			
			Thread.sleep(500);
			driver.findElement(
					By.xpath("//div[@class='appendBottom20']//p[text()='CHILDREN (2y - 12y )']//following::ul[1]//li["+childtraveller+"]"))
					.click();

			Thread.sleep(1000);
			driver.findElement(By.xpath("//button[text()='APPLY']")).click();

			driver.findElement(By.xpath("//a[normalize-space()='Search']")).click();
			

		}

		public static void  selectTraveller( WebDriver driver, String adult_traveller, String childtraveller )  {
			System.out.println("adult_traveller------" + adult_traveller+"childtraveller +="+childtraveller);

			// String city=" ";
			
			try {
				driver.findElement(By.xpath("//span[@class='lbl_input appendBottom5']")).click();
				// element.click();
			} catch (NoSuchElementException e) {
				// Handle the exception or log an error message
				System.out.println("Element not found: " + e.getMessage());
			}

			driver.findElement(
					By.xpath("//div[@class='appendBottom20']//p[text()='ADULTS (12y +)']//following::ul[1]//li[3]"))
					.click();
			driver.findElement(
					By.xpath("//div[@class='appendBottom20']//p[text()='CHILDREN (2y - 12y )']//following::ul[1]//li[2]"))
					.click();

			driver.findElement(By.xpath("//button[text()='APPLY']")).click();
			
			}
		
		
		
		public static void select_city(WebDriver driver, String selected_city) throws InterruptedException {
			System.out.println("selected_city------" + selected_city);

			// String city=" ";
			String city_dropdown1 = " ";
			String city_dropdown2 = " ";
			String cities_data = "";

			// while(selected_city = null) {

			List<WebElement> cities = driver.findElements(By.xpath("//div[@class='calc60']//p[1]"));

			for (int i = 0; i < cities.size(); i++) {
				WebElement element = cities.get(i);
				String innerhtml = element.getAttribute("innerHTML");
				System.out.println(innerhtml);
				Thread.sleep(500);
				if (innerhtml.contentEquals(selected_city)) {
					element.click();
					break;
				}
				// System.out.println("Values in list " +innerhtml);
			}
		}

		public static void selectDate(WebDriver driver, String targetDate, String dateFormat) throws Exception {

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat targetDateFormat = new SimpleDateFormat(dateFormat);
			Date formattedTargetDate;
			try {
				targetDateFormat.setLenient(false);
				formattedTargetDate = targetDateFormat.parse(targetDate);
				calendar.setTime(formattedTargetDate);

				int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
				// System.out.println("targetDay======" + targetDay);
				int targetMonth = calendar.get(Calendar.MONTH);
				// System.out.println("targetMonth======" + targetMonth);
				int targetYear = calendar.get(Calendar.YEAR);
				// System.out.println("targetYear======" + targetYear);

				String actualDate = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']//div")).getText();
				// System.out.println("actualDate-----" + actualDate);
				calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));

				int actualMonth = calendar.get(Calendar.MONTH);
				int actualYear = calendar.get(Calendar.YEAR);

				while (targetMonth < actualMonth || targetYear < actualYear) {
					System.out.println("smaller than target");

					// String labelText = element.getAttribute("aria-label");
					List<WebElement> actualDate1 = driver.findElements(By.xpath("//div[@class='DayPicker-Caption']//div"));

					for (WebElement a : actualDate1) {
						actualDate = a.getText();
						System.out.println(actualDate);
					}

					calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));

					actualMonth = calendar.get(Calendar.MONTH);
					actualYear = calendar.get(Calendar.YEAR);
				}

				while (targetMonth > actualMonth || targetYear > actualYear) {
					driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
					// System.out.println("for departure ");
					System.out.println("targetMonth ----" + targetMonth);
					System.out.println("targetYear----" + targetYear);
					actualDate = driver.findElement(By.xpath("//div[@class='DayPicker-Caption']")).getText();
					System.out.println("actualDate=======" + actualDate);
					// System.out.println(actualDate);

					calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));

					actualMonth = calendar.get(Calendar.MONTH);
					actualYear = calendar.get(Calendar.YEAR);
				}

				Thread.sleep(500);
				//System.out.println("for departure-----" + targetDay);

				driver.findElement(By.xpath("//div[@class='DayPicker-Day']//p[text()=" + targetDay + "]")).click();

			} catch (ParseException e) {
				throw new Exception("Invalid date is provided, please check input date");
			}
		}

	}



