package me.mateusneres.searchapartments;

import me.mateusneres.searchapartments.managers.ApartmentManager;
import me.mateusneres.searchapartments.utils.Util;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static ArrayList<String> checkurl = new ArrayList<>();
    public static ArrayList<String> verify_url = new ArrayList<>();
    public static ArrayList<String> urls_view = new ArrayList<>();
    public static ArrayList<String> valid_apart = new ArrayList<>();
    public static Main instance;
    public static ApartmentManager apartmentManager;

    public static void main(String[] args) throws InterruptedException {
        loadUrls();
        apartmentManager = new ApartmentManager();
        SearchManager.runSearch();


    }

    public static void loadUrls() {
        try {
            File file = new File("urls_view.txt");
            RandomAccessFile read = new RandomAccessFile(file, "rw");
            String line;
            int load = 0;
            while ((line = read.readLine()) != null) {
                load++;
                if (load > 200 ) {
                    PrintWriter writer = new PrintWriter(file);
                    writer.print("");
                    writer.close();
                }
                urls_view.add(line);
            }
            System.out.println("We carry about " + load + " urls viewed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Main getInstance() {
        return instance;
    }
}
