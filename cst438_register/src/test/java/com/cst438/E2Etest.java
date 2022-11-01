package com.cst438;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
class E2Etest {
	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_NAME = "test"; 
	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	StudentRepository studentRepository;
	
	@Test
	@Order(1)
	public void addStudent() throws Exception{
		
		//if student already exists, then delete the student
		Student s = null;
		do {
			s = studentRepository.findByEmail(TEST_USER_EMAIL);
			if(s != null) studentRepository.delete(s);
		}while (s != null);
		
		
		//set up driver
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
   
		WebDriver driver = new ChromeDriver();
		
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		try {
			//TODO
			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);
			
			//locate and click "add student" button
			driver.findElement(By.xpath("//button[@id='AddStudentBtn']")).click();
			Thread.sleep(SLEEP_DURATION);
			
			//enter student email and name
			driver.findElement(By.xpath("//input[@name='student_name']")).sendKeys(TEST_NAME);
			driver.findElement(By.xpath("//input[@name='email']")).sendKeys(TEST_USER_EMAIL);
			driver.findElement(By.xpath("//button[@id='Add']")).click();
			Thread.sleep(SLEEP_DURATION);
			
			String toast_text = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
			assertEquals(toast_text, "Student added successfully.");
			
		}catch (Exception ex) {
			throw ex;
			
		} finally {
			//clean up database
			Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (student != null)
				studentRepository.delete(student);
			driver.quit();
		}
	}

	
//	@Test
//	@Order(2)
//	public void addStudentAlreadyExists() throws Exception{
//		
//		//set up driver
//		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
//   
//		WebDriver driver = new ChromeDriver();
//		
//		// Puts an Implicit wait for 10 seconds before throwing exception
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		
//		try {
//			//TODO
//			driver.get(URL);
//			Thread.sleep(SLEEP_DURATION);
//			
//			//locate and click "add student" button
//			driver.findElement(By.xpath("//button[@id='AddStudentBtn']")).click();
//			Thread.sleep(SLEEP_DURATION);
//			
//			//enter student email and name
//			driver.findElement(By.xpath("//input[@name='student_name']")).sendKeys(TEST_NAME);
//			driver.findElement(By.xpath("//input[@name='email']")).sendKeys(TEST_USER_EMAIL);
//			driver.findElement(By.xpath("//button[@id='Add']")).click();
//			Thread.sleep(SLEEP_DURATION);
//			
//			String toast_text = driver.findElement(By.cssSelector(".Toastify__toast-body div:nth-child(2)")).getText();
//			assertEquals(toast_text, "Student already exists.");
//			
//		}catch (Exception ex) {
//			throw ex;
//			
//		} finally {
//			//clean up database
//			Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
//			if (student != null)
//				studentRepository.delete(student);
//			driver.quit();
//		}
//	}
}
