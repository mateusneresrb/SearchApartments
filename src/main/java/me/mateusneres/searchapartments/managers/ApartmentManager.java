package me.mateusneres.searchapartments.managers;

import me.mateusneres.searchapartments.Main;
import me.mateusneres.searchapartments.objects.Apartments;
import me.mateusneres.searchapartments.utils.Util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApartmentManager {

    private static Map<String, Apartments> apartments;
    private Main instance = Main.getInstance();

    public ApartmentManager() {
        this.apartments = new HashMap<>();
    }

    public static void addApartments(String url, Apartments apart) {
        if (!apartments.containsKey(url)) {
            apartments.put(url, apart);
        }
    }

    public static void loadAparts() {
        final int[] x = {0};
        apartments.forEach((s, apps) -> {
            x[0]++;
            Main.valid_apart.add("<br>Name of the apartment: " + apps.getName() + "<br>Location: " + apps.getLocation() + "<br>URL: " + s + " <br>Price: " + apps.getCost() + "<br>Bedrooms: " + apps.getRooms() + "<br>Date posted: " + apps.getDate_posted() + "<br>Date end: " + apps.getDate_end() + "<br>");
            Main.urls_view.add(s);
            Util.writeFile(s);
        });
        System.out.println("We found about " + x[0] + " apartments, soon an email will be sent.");
    }

    public static void sendEmails() {
        if (Main.valid_apart.isEmpty()) {
            System.out.println("We can't find any new apartments at the moment. We'll check again soon.");
            return;
        }

        final String username = "apartamentsnew@gmail.com";
        final String password = "password";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("apartamentsnew@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("mateusneresrb@gmail.com")

            );

            message.setSubject("New apartments have been found! [" + Main.verify_url.size() + "]");

            String emailBody = "Below is some information about the last apartments posted on the site: https://bostad.stockholm.se/ <br>"
                    + Main.valid_apart.toString().replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "") + "<br><br> SearchApartments, <br>Good luck!";

            message.setContent(emailBody, "text/html; charset=UTF-8");
            Transport.send(message);

            System.out.println("An email was sent with the new apartment options found!");
            apartments.clear();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}



