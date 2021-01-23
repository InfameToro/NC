package com.example.vk;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.io.File;
import java.io.IOException;
import java.util.Random;


public class lab6 {
    ChromeDriver driver = null; //1)Login parameters
    String vklogin = "Phone or mail";
    String vkpass = EncodeDecrypt("YourPass",10 );//Encrypted pass params(character: your pass, secretKey)

    public static void main(String[] args) throws InterruptedException, IOException {
        lab6 vk = new lab6();
        //EncryptModule
        /*String passwordIn = EncodeDecrypt("YouPassToDecrypt",10);
        String passwordOut = "";
        for (int i =0 ; i<passwordIn.length(); i++){
            passwordOut +=TopSecret(passwordIn.charAt(i),10);
        }
        System.out.println(passwordIn);*/
    }
    public lab6() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe"); //start Chrome Driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data");
        try {
            driver = new ChromeDriver(options);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (driver == null) return;
        AuthVk(driver);//lab tasks1
        for (int i = 1; i < 11; i++) {
            MenuClick(driver, i);//lab tasks2
            TakeScreenshot(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //
        PostOnWall(driver, "Я учусь в Учебном центре Netcracker Тольятти https://vk.com/infocom_tlt");//lab tasks3
        EditTown(driver, "Самара");//lab tasks5
        EditEducation(driver, "Тольятти", "Лицей №19");
    }

    //1)Login in main page
    private boolean AuthVk(ChromeDriver driver) {
        driver.navigate().to("https://vk.com");
        if (!driver.getCurrentUrl().contains("feed")) {
            try {
                driver.findElement(By.id("index_email")).click();
                Thread.sleep(2000);
                driver.findElement(By.id("index_email")).sendKeys(vklogin);
                Thread.sleep(2000);
                driver.findElement(By.id("index_pass")).click();
                Thread.sleep(2000);
                driver.findElement(By.id("index_pass")).sendKeys(EncodeDecrypt(vkpass, 10));
                Thread.sleep(2000);
                driver.findElement(By.id("index_login_button")).click();
                Thread.sleep(2000);
                driver.findElement(By.id("//*[@id='l_pr']/a/span/span[3]"));
                Thread.sleep(1000);
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        } else return true;
    }
    //StringEncrypter
    public static String EncodeDecrypt(String str, int secretKey) {
        char[] ch = str.toCharArray(); //converting a string to characters
        String newStr = ""; // variable encrypted string
        for (char c : ch) //select each element
            newStr += TopSecret(c, secretKey); //encrypt each element and save it to a string
        return newStr;
    }
    //CharEncrypter
    public static char TopSecret(char character, int secretKey) {
        character = (char) (character ^ secretKey); //Do XOR
        return character;
    }

    //task2 - clicking
    private void MenuClick(ChromeDriver driver, int i) {
        try {
            driver.findElement(By.xpath("//*[@id=\"side_bar_inner\"]/nav/ol/li[" + i + "]/a")).click();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void TakeScreenshot(int num) {
        try {
            String name = "SeleniumScreenshot" + (num) + ".jpg";
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(name));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //tasks 3,4 post and screenshot
    private void PostOnWall(ChromeDriver driver, String text) {
        driver.navigate().to("https://vk.com");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//*[@id=\"side_bar_inner\"]/nav/ol/li[1]/a")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("post_field")).sendKeys(text);
        driver.findElement(By.id("send_post")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String xpath = "//*[.='" + text + "']";
            driver.findElement(By.xpath(xpath));
            System.out.println("PostAdded!");
        } catch (Exception e) {
            System.out.println("PostNotFound");
        }
    }

    //5) Change bio parameters
    private void EditTown(ChromeDriver driver, String town) {
        driver.navigate().to("https://vk.com/edit?act=contacts");
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"dropdown1\"]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[text()=\"" + town + "\"]/..")).click();
            driver.findElement(By.xpath("//*[@id=\"pedit_contacts\"]/div[11]/button")).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //education
    private void EditEducation(ChromeDriver driver, String town, String school) {
        driver.navigate().to("https://vk.com/edit?act=education");
        try {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"dropdown6\"]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[text()=\"" + town + "\"]/..")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"dropdown5\"]")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[text()=\"" + school + "\"]/..")).click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"pedit_education\"]/div[4]/button")).click();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}



