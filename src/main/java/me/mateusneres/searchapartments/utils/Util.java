package me.mateusneres.searchapartments.utils;

import me.mateusneres.searchapartments.Main;
import me.mateusneres.searchapartments.managers.ApartmentManager;
import me.mateusneres.searchapartments.objects.Apartments;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static void check_url(WebDriver driver, String url) {
        driver.get(url);

        int id_url = Integer.parseInt(url.replaceAll("\\D*", ""));
        String name_url = driver.findElement(By.cssSelector("body > main > header > div > div > div.heading-row > h1")).getText();
        int rooms_url = Integer.parseInt(driver.findElement(By.cssSelector("#objFacts > div > div:nth-child(3) > div.v")).getText().replaceAll("\\D*", ""));
        int cost_url = Integer.parseInt(driver.findElement(By.cssSelector("#objFacts > div > div:nth-child(5) > div.v")).getText().replaceAll("\\D*", ""));
        String date_posted_url = driver.findElement(By.cssSelector("#annons-top-bar > div:nth-child(1) > div:nth-child(1) > div.v")).getText();
        String date_end_url = driver.findElement(By.cssSelector("#annons-top-bar > div:nth-child(1) > div:nth-child(2) > div.v")).getText();

        String location = driver.findElement(By.cssSelector("body > main > header > div > div > div.highlightTxt")).getText().substring(6);

        String[] date_posted_split = date_posted_url.split("-");
        String[] date_end_split = date_end_url.split("-");

        LocalDateTime checkdate1 = LocalDateTime.of(Integer.parseInt(date_posted_split[0]), Integer.parseInt(date_posted_split[1]), Integer.parseInt(date_posted_split[2]), 0, 0, 0);
        LocalDateTime checkdate2 = LocalDateTime.of(Integer.parseInt(date_end_split[0]), Integer.parseInt(date_end_split[1]), Integer.parseInt(date_end_split[2]), 0, 0, 0);

        LocalDateTime checkdate3 = LocalDateTime.now(ZoneId.of("Europe/Stockholm"));
        long dias = checkdate1.until(checkdate2, ChronoUnit.DAYS);
        Main.checkurl.remove(0);
        if (dias <= 1) {
            if (!Main.urls_view.contains(url)) {
                Main.verify_url.add(url);
                Apartments apps = new Apartments(id_url, name_url, location, rooms_url, cost_url, date_posted_url, date_end_url);
                ApartmentManager.addApartments(url, apps);
            }
        }
    }

    public static void writeFile(final String line) {
        try {
            final FileWriter fw = new FileWriter("urls_view.txt", true);
            final PrintWriter pw = new PrintWriter(fw);
            pw.println(line);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("§cFaild to write to file: 'urls_view.txt'");
        }
    }

    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
