package me.mateusneres.searchapartments;

import me.mateusneres.searchapartments.managers.ApartmentManager;
import me.mateusneres.searchapartments.objects.Apartments;
import me.mateusneres.searchapartments.utils.Util;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class SearchManager {

    public static void runSearch() throws InterruptedException {

        System.setProperty("webdriver.chrome.silentOutput", "true");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

        WebDriver driver;
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--lang=en-US");
        chromeOptions.addArguments("--disable-infobars");

        driver = new ChromeDriver(chromeOptions);


        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Main.valid_apart.clear();
            Main.verify_url.clear();
            Main.checkurl.clear();

            driver.get("https://bostad.stockholm.se/Lista/?s=58.97550&n=59.62471&w=16.95190&e=18.75092&sort=annonserad-fran-desc&minAntalRum=2");

            WebDriverWait wait = new WebDriverWait(driver, 15);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> apartaments_list = driver.findElements(By.xpath("//*[@id=\"apartment-search-list\"]/div"));
            int count = apartaments_list.size();


            String url;
            // Add all links in arraylist and check.
            for (int x = 1; x < count; x++) {
                url = driver.findElement(By.cssSelector("#apartment-search-list > div:nth-child(" + x + ") > div.m-apartment-card__base > div:nth-child(1) > div:nth-child(1) > a")).getAttribute("href").toString();
                Main.checkurl.add(url);
            }

            boolean finish = false;

            while (!finish) {
                try {
                    if (Main.checkurl.size() > 0) {
                        Util.check_url(driver, Main.checkurl.get(0));
                    } else {
                        ApartmentManager.loadAparts();
                        ApartmentManager.sendEmails();
                        finish = true;

                    }

                } catch (Exception e) {
                    finish = false;

                }

            }
        }, 0, 6, TimeUnit.HOURS);
    }
}

