package Slutupg.grafik;

import Slutupg.FordonSamling;
import Slutupg.Buss;
import Slutupg.Kund;
import Slutupg.Motorcykel;
import Slutupg.Personbil;
import Slutupg.Reperering;
import Slutupg.tools.tools_fordon;
import Slutupg.tools.tools_kund;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminPage extends JFrame {
    static String KundPath = "info/Kund.txt";
    static String FordonPath = "info/fordon.txt";
    static String ReparationPath = "info/Reperering.txt";
    private Kund admin;

    private Kund kund;
    private static int reparationsID = 1;

    public AdminPage(Kund admin) {
        this.admin = admin;
        setTitle("Admin Panel");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JPanel adminPanel = new JPanel();
        adminPanel.setBackground(Color.CYAN);
        buttonPanel.setBackground(Color.CYAN);

        JButton logoutButton = new JButton("Logout");
        buttonPanel.add(logoutButton, BorderLayout.CENTER);
        logoutButton.setBounds(50, 50, 200, 50);
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutButton.setBackground(Color.BLUE);
        logoutButton.setForeground(Color.WHITE);

        logoutButton.addActionListener(e -> {
            //Stänger KundInloggadGUI och går tillbaka till login screen
            dispose();
        });

        //skapar en knapp (registrera fordon)
        JButton registeraFordon = new JButton("Register a vehicle");
        buttonPanel.add(registeraFordon, BorderLayout.CENTER);
        registeraFordon.setBounds(50, 50, 200, 50);
        registeraFordon.setFont(new Font("Arial", Font.PLAIN, 18));
        registeraFordon.setBackground(Color.BLUE);
        registeraFordon.setForeground(Color.WHITE);

        /**
         * Koden nedan hanterar händelsen när användaren klickar på "Registera fordon"-knappen.
         * En dialogruta visas för att välja fordonskategori (personbil, motorcykel, buss).
         * Beroende på det valda fordonsvalet visas en specifik inmatningsform för att registrera fordonet.
         * Efter att användaren har fyllt i formuläret kontrolleras och valideras inmatade värden.
         * Om alla obligatoriska fält är ifyllda och korrekta, skapas och registreras fordonet.
         * */
        registeraFordon.addActionListener(e -> {
            JComboBox<FordonSamling> fordonsTypJComboBox = new JComboBox<>(FordonSamling.values());
            int valAvFordon = JOptionPane.showConfirmDialog(this, fordonsTypJComboBox, "Val av fordon", JOptionPane.OK_CANCEL_OPTION);
            if (valAvFordon == JOptionPane.OK_OPTION) {
                if (fordonsTypJComboBox.getSelectedItem() != null) {
                    FordonSamling valdFordonsTyp = FordonSamling.valueOf(fordonsTypJComboBox.getSelectedItem().toString());
                    JTextField kundEmailField = new JTextField("");
                    JTextField maxAntalPassagerareField = new JTextField("");
                    JTextField inköpsPrisField = new JTextField("");
                    JTextField regPlåtField = new JTextField("");
                    boolean ärSparad = false;
                    try {
                        //En switch case som är baserat på vad för fordon adminen vill skapa
                        switch (valdFordonsTyp) {
                            case PERSONBIL -> {
                                JTextField bagageUtrymmeVolymField = new JTextField("");

                                // Deklarerar en array av objekt för att lagra labels och input fields.
                                Object[] InputDataFields = {
                                        "Costumer email: ", kundEmailField,
                                        "Total passengers: ", maxAntalPassagerareField,
                                        "Purchase price: ", inköpsPrisField,
                                        "bagage: ", bagageUtrymmeVolymField,
                                        "Licence plate: ", regPlåtField,
                                };


                                int inputOption = JOptionPane.showConfirmDialog(this, InputDataFields, "Ny Person bil", JOptionPane.OK_CANCEL_OPTION);

                                //Om man har tryckt "ok"
                                if (inputOption == JOptionPane.OK_OPTION) {
                                    //Hämta data från formen
                                    String kundEmail = kundEmailField.getText();
                                    String maxAntalPassagerare = maxAntalPassagerareField.getText();
                                    String inköpsPris = inköpsPrisField.getText();
                                    String regPlåt = regPlåtField.getText();
                                    String bagageUtrymme = bagageUtrymmeVolymField.getText();
                                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                                    boolean upptagenRegPlåt = false;

                                    // En loop som läser varje rad från en bufferedReader tills det inte finns fler rader att läsa.
                                    while (true) {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        if (line.equals("Fordonsnummer:" + regPlåt)) {
                                            upptagenRegPlåt = true;
                                        }
                                    }
                                    bufferedReader.close();
                                    if (upptagenRegPlåt) {
                                        JOptionPane.showMessageDialog(this, "Regplåten finns redan", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        if (!kundEmail.isEmpty() && !maxAntalPassagerare.isEmpty() && !inköpsPris.isEmpty() && !regPlåt.isEmpty() && !bagageUtrymme.isEmpty()) {
                                            boolean isKund = tools_kund.isKundVisaEmail(kundEmail);
                                            if (isKund) {
                                                Kund kund = tools_kund.readKundFromFile(kundEmail);
                                                double inKöpsPrisDouble = Double.parseDouble(inköpsPris);
                                                Calendar datum = Calendar.getInstance();
                                                Personbil personalCar = new Personbil(kund,
                                                        regPlåt,
                                                        Integer.parseInt(maxAntalPassagerare),
                                                        datum,
                                                        inKöpsPrisDouble,
                                                        inKöpsPrisDouble,
                                                        kund.getAdress(),
                                                        12
                                                );
                                                ärSparad = tools_fordon.mataFordonTextFIl(personalCar);
                                            } else {
                                                JOptionPane.showMessageDialog(this, "Email finns ej", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(this,
                                                    "Fyll i alla fält",
                                                    "Error",
                                                    JOptionPane.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            }
                            //Om man väljer att skapa en motorcykel
                            case MOTORCYKEL -> {
                                //FOrmen för inmattning av en motorcykel
                                Object[] InputDataFields = {
                                        "Kund email: ", kundEmailField,
                                        "Max Antal Passagerare: ", maxAntalPassagerareField,
                                        "inKöps pris: ", inköpsPrisField,
                                        "RegPlåt: ", regPlåtField,
                                };

                                //Visar formen
                                int inputOption = JOptionPane.showConfirmDialog(this, InputDataFields, "Register vehicle", JOptionPane.OK_CANCEL_OPTION);
                                if (inputOption == JOptionPane.OK_OPTION) {
                                    String kundEmail = kundEmailField.getText();
                                    String maxAntalPassagerare = maxAntalPassagerareField.getText();
                                    String inKöpsPris = inköpsPrisField.getText();
                                    String regPlåt = regPlåtField.getText();

                                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                                    boolean upptagenRegPlåt = false;

                                    //KOllar om regplåten redan finns
                                    while (true) {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        if (line.equals("Fordonsnummer:" + regPlåt)) {
                                            upptagenRegPlåt = true;
                                        }
                                    }
                                    bufferedReader.close();
                                    //Ge meddlanden om reg plåten redan finns
                                    if (upptagenRegPlåt) {
                                        JOptionPane.showMessageDialog(this, "Regplåten finns redan", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        //Kollar om formen är tom eller inte
                                        if (!kundEmail.isEmpty() && !maxAntalPassagerare.isEmpty() && !inKöpsPris.isEmpty() && !regPlåt.isEmpty()) {
                                            //Kolla om kunden finns genom mail
                                            boolean isKund = tools_kund.isKundVisaEmail(kundEmail);
                                            if (isKund) {
                                                //En statisk metod som används för att hitta rätt kund med kundEmail
                                                Kund kund = tools_kund.readKundFromFile(kundEmail);
                                                //Hämta priset från formen
                                                double inKöpsPrisDouble = Double.parseDouble(inKöpsPris);
                                                //Skapa dagens datum
                                                Calendar datum = Calendar.getInstance();
                                                //Skapa en motorcykel klass och skriva in det i filen(fordon.txt)
                                                Motorcykel motorBike = new Motorcykel(
                                                        kund,
                                                        regPlåt,
                                                        2,
                                                        datum,
                                                        inKöpsPrisDouble,
                                                        inKöpsPrisDouble,
                                                        kund.getAdress());
                                                //En Statisk class som kallas för att skriva in data i fordon.txt
                                                ärSparad = tools_fordon.mataFordonTextFIl(motorBike);
                                                //Om email inte finns i kund.txt
                                            } else {
                                                JOptionPane.showMessageDialog(this, "No user with this email exiests", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                            //Fel meddelande om man inte har fyllt i alla fält i formen
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Enter in all fields", "Error", JOptionPane.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            }
                            //Om man väljer att skapa en buss
                            case BUSS -> {
                                //Bussens inputsfields
                                JTextField beställandeKommunField = new JTextField("Stockholm");
                                Object[] inMattningsField = {
                                        "Customer email: ", kundEmailField,
                                        "Passengers: ", maxAntalPassagerareField,
                                        "Purchase Price: ", inköpsPrisField,
                                        "Kommun: ", beställandeKommunField,
                                        "Licence plate: ", regPlåtField,
                                };

                                //Visar formen
                                int inputOption = JOptionPane.showConfirmDialog(this, inMattningsField, "Login", JOptionPane.OK_CANCEL_OPTION);
                                //Om de väljer att gå vidare
                                if (inputOption == JOptionPane.OK_OPTION) {
                                    //Hämta texten från formen
                                    String kundEmail = kundEmailField.getText();
                                    String maxAntalPassagerare = maxAntalPassagerareField.getText();
                                    String inKöpsPris = inköpsPrisField.getText();
                                    String regPlåt = regPlåtField.getText();
                                    String beställandeKommun = beställandeKommunField.getText();
                                    // Öppnar en  bufferedreader och en file reader för att granska fordon text filen
                                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                                    //Kollar om regPlåten redan finns
                                    boolean upptagenRegPlåt = false;
                                    //While loop som kollar om regplåten redan finns genom att läsa fordon.txt
                                    while (true) {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        if (line.equals("Fordonsnummer:" + regPlåt)) {
                                            upptagenRegPlåt = true;
                                        }
                                    }
                                    bufferedReader.close();
                                    //Ge meddelande om regplåten redan finns
                                    if (upptagenRegPlåt) {
                                        JOptionPane.showMessageDialog(this, "License plate does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        //Kollar om formen inte är tom
                                        if (!kundEmail.isEmpty() && !maxAntalPassagerare.isEmpty() && !inKöpsPris.isEmpty() && !regPlåt.isEmpty() && !beställandeKommun.isEmpty()) {
                                            //Hämta kunden genom email
                                            boolean isKund = tools_kund.isKundVisaEmail(kundEmail);
                                            //Om kunden hittas i kund.txt
                                            if (isKund) {
                                                //En statisk metod som används för att hitta rätt kund med kundEmail
                                                Kund kund = tools_kund.readKundFromFile(kundEmail);
                                                //Sparar priset i en double, från formen
                                                double inköpsPrisDOuble = Double.parseDouble(inKöpsPris);
                                                //Hämtar dagens datum
                                                Calendar datum = Calendar.getInstance();
                                                //Skapar en buss klass
                                                Buss buss = new Buss(kund, regPlåt, Integer.parseInt(maxAntalPassagerare), 8, datum, inköpsPrisDOuble, inköpsPrisDOuble, "Stockholm");
                                                //Skriver in bussen i fordon.txt med en statisk klass
                                                ärSparad = tools_fordon.mataFordonTextFIl(buss);
                                                //Om mailet inte hittas i kund.txt
                                            } else {
                                                JOptionPane.showMessageDialog(this, "Email not found", "Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                            //Om formen inte fylls i, ge fel meddelande
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Enter all Fields", "Fel", JOptionPane.WARNING_MESSAGE);
                                        }
                                    }
                                }

                            }
                        }
                        //Fel meddelande om det blev fel med att läsa/skriva datan
                    } catch (NumberFormatException | IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    //Om fordondet blev skapat ge rätt medelande
                    if (ärSparad) {
                        JOptionPane.showMessageDialog(this, "Vehicle was successfully registered!", "Success", JOptionPane.PLAIN_MESSAGE);
                        //Om fordondet inte skapas get ut fel meddelanden
                    } else {
                        JOptionPane.showMessageDialog(this, "Unable to register Vehicle!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

        });

        //En knapp för att redigera fordons ägaren
        JButton redigeraFordonsÄgare = new JButton("Changes vehicle owner");
        buttonPanel.add(redigeraFordonsÄgare, BorderLayout.CENTER);
        redigeraFordonsÄgare.setBounds(50, 50, 200, 50);
        redigeraFordonsÄgare.setFont(new Font("Arial", Font.PLAIN, 18));
        redigeraFordonsÄgare.setBackground(Color.BLUE);
        redigeraFordonsÄgare.setForeground(Color.WHITE);

        /**
         * Koden visar en dialogruta för att ange regplåt att redigera ägaren för.
         * Om registreringsnumret anges och finns i filen, visas en dialogruta för att ange den nya ägaren.
         * Om den nya ägaren finns i kundfilen, ändras ägaren i filen.
         * En bekräftese visas om ändringen lyckas.
         * Om det uppstår ett fel vid läsning/skrivning av filerna visas får använadren ett error
         */
        redigeraFordonsÄgare.addActionListener(e -> {
            //Visar formen för att kunna ändra fordons ägare
            String fordonensRegplåt = JOptionPane.showInputDialog(this, "vehicle licence plate");
            //Om de väljer att gå vidare
            if (fordonensRegplåt != null && fordonensRegplåt.length() > 0) {
                try {
                    // Öppnar en file reader och en bufferedreader för att granska fordon text filen
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                    //Kollar om regPlåten redan finns
                    boolean isRegPlåt = false;
                    //En while loop som går igenom fordon.txt för att hitta regplåt som blev sökt efter
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.equals("Fordonsnummer:" + fordonensRegplåt)) {
                            isRegPlåt = true;
                            break;
                        }
                    }
                    bufferedReader.close();
                    //Om fordondet inte finns
                    if (!isRegPlåt) {
                        JOptionPane.showMessageDialog(this, "No vehicle was found!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //En liten form för att välja vilken epost som ska ersätta fordondet
                        String nyaÄgareEmail = JOptionPane.showInputDialog(this, "New owner");
                        //Används för att se om det lyckades att ändra fordons ägare
                        boolean ärSparad = false;
                        boolean isEmail = tools_kund.isKundVisaEmail(nyaÄgareEmail);
                        //Om mailet inte hittas
                        if (!isEmail) {
                            JOptionPane.showMessageDialog(this, "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            //En buffereader för att läsa kund.txt
                            bufferedReader = new BufferedReader(new FileReader(KundPath));
                            String s;
                            String line = bufferedReader.readLine();
                            //Om kunden hittas eller inte
                            int nyaKundNummer = -1;
                            //Hämtar kundens nummer genom email
                            while (line != null) {
                                s = line;
                                //Om eposten hittas skippa lösenords delen sedan spara den nyakundnummret så kommer ersätta
                                //Den gamla
                                if (s.equals("E-post:" + nyaÄgareEmail)) {
                                    bufferedReader.readLine();
                                    nyaKundNummer = Integer.parseInt(bufferedReader.readLine().substring(11));
                                    break;
                                }
                                line = bufferedReader.readLine();
                            }
                            bufferedReader.close();

                            //Läsa in från fordon text filen
                            bufferedReader = new BufferedReader(new FileReader(FordonPath));
                            //Leta efter fordonsnummer så den kan ersättas
                            while (true) {
                                String sTemp = bufferedReader.readLine();
                                if (sTemp == null) {
                                    break;
                                }
                                //Om forondet hittas
                                if (sTemp.equals("Fordonsnummer:" + fordonensRegplåt)) {
                                    //Om nya kund nummret hittades
                                    if (nyaKundNummer > 0) {
                                        //Hitta gamla kund nummret
                                        String gamlaKundNummer = bufferedReader.readLine();
                                        //Sätter filvägen till fordon text filen
                                        String filePath = FordonPath;

                                        //Sätta den nya kund nummret till samma format som finns i text filen
                                        // fordon.txt så det går att hitta den
                                        String newKundNummer = "Ägare:" + nyaKundNummer; // or any new customer number you want to replace with
                                        //Läsa filens data
                                        Path path = Paths.get(filePath);
                                        Charset charset = StandardCharsets.UTF_8;
                                        String content = Files.readString(path, charset);

                                        //Hitta linjen med den gamla regplået
                                        String regex = "Fordonsnummer:" + fordonensRegplåt + "\n" + gamlaKundNummer;
                                        Pattern pattern = Pattern.compile(regex);
                                        Matcher matcher = pattern.matcher(content);

                                        //Ändra gamla ägaren med den nya
                                        if (matcher.find()) {
                                            String oldLine = matcher.group();
                                            String newLine = "Fordonsnummer:" + fordonensRegplåt + "\n" + newKundNummer;
                                            content = content.replace(oldLine, newLine);
                                            ärSparad = true;
                                        }
                                        //Skriva data in i filen
                                        Files.writeString(path, content, charset);
                                    }
                                }
                            }
                        }
                        //Om ägaren till fordondet har ändrats
                        if (ärSparad) {
                            JOptionPane.showMessageDialog(this, "Owner was changed!", "Success", JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Could not change owner!", "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    //Fel meddelande om numret är fel eller om det blir fel med att läsa/skriva data till filerna
                } catch (NumberFormatException | IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });
        //adminPanel.add(redigeraFordonsÄgare);

        JButton sökaFordon = new JButton("Search vehicle");
        buttonPanel.add(sökaFordon, BorderLayout.CENTER);
        sökaFordon.setBounds(50, 50, 200, 50);
        sökaFordon.setFont(new Font("Arial", Font.PLAIN, 18));
        sökaFordon.setBackground(Color.BLUE);
        sökaFordon.setForeground(Color.WHITE);
        sökaFordon.addActionListener(e -> {
            //Ett fält där man skriv in vilket fordon man letar efter
            String regPlåt = JOptionPane.showInputDialog(this, "Vehicle licence plate");
            if (regPlåt != null && !regPlåt.isEmpty()) {

                //Kollar om fordondet finns genom en statisk metod
                boolean isVehicle = tools_fordon.isFordonViaRegplåt(regPlåt);

                if (isVehicle) {
                    try {
                        // Open a filereader and a Bufferedreader to write to the file kund.txt
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                        //Skapar en string där alla information kommer sparas
                        String fordonsInformation = "";
                        String line = "";
                        //Läser av filen så längde den inte är tom
                        while ((line = bufferedReader.readLine()) != null) {

                            //Om den hittar den rätta fordondet
                            if (line.equals("Fordonsnummer:" + regPlåt)) {
                                fordonsInformation += line + "\n";

                                //Sparar all information om fordondet och slutar när det är tomt
                                while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                                    fordonsInformation += line + "\n"; // Add the other lines until an empty line is found
                                }
                            }
                        }
                        //Visar fordondet
                        JOptionPane.showMessageDialog(this, fordonsInformation);
                        //Om det blir något fel med att läsa från filerna
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    //Om fordondet inte finns i text filen
                    JOptionPane.showMessageDialog(this, "Vehicle was not found", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        JButton sökaKund = new JButton("Search customer");
        buttonPanel.add(sökaKund, BorderLayout.CENTER);
        sökaKund.setBounds(50, 50, 200, 50);
        sökaKund.setFont(new Font("Arial", Font.PLAIN, 18));
        sökaKund.setBackground(Color.BLUE);
        sökaKund.setForeground(Color.WHITE);

        /**
         * Koden söker efter en kund baserat på den angivna email i en fil.
         * Om kunden hittas visas informationen i rutan
         * Om kunden inte hittas visas får användren en error
         * Om det uppstår fel vid läsning av filen får användaren ett error meddelande
         */
        sökaKund.addActionListener(e -> {
            //Ett fält där man skriv in vilket fordon man letar efter
            String email = JOptionPane.showInputDialog(this, "customer email");
            //Om man har skrivit något
            if (email != null && !email.isEmpty()) {
                //Kollar om fordondet finns genom en statisk metod
                boolean iskund = tools_kund.isKundVisaEmail(email);
                if (iskund) {
                    try {
                        // Open a filereader and a Bufferedreader to write to the file kund.txt
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(KundPath));
                        //Skapar en string där alla information kommer sparas
                        String kundensInformation = "";
                        String line = "";
                        //Läser av filen så längde den inte är tom
                        while ((line = bufferedReader.readLine()) != null) {
                            //Om den hittar den rätta fordondet
                            if (line.equals("E-post:" + email)) {
                                kundensInformation += line + "\n"; // Add the fordonnummer line
                                //Sparar all information om fordondet och slutar när det är tomt
                                while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                                    kundensInformation += line + "\n"; // Add the other lines until an empty line is found
                                }
                            }
                        }
                        //Visar fordondet
                        JOptionPane.showMessageDialog(this, kundensInformation);
                        //Om det blir något fel med att läsa från filerna
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    //Om fordondet inte finns i text filen
                    JOptionPane.showMessageDialog(this, "Vehicle was not found", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
        //En knapp för att reparera ett fordon
        //adminPanel.add(sökaKund);

        JButton reparaFordon = new JButton("Repair a vehicle");
        buttonPanel.add(reparaFordon, BorderLayout.CENTER);
        reparaFordon.setBounds(50, 50, 200, 50);
        reparaFordon.setFont(new Font("Arial", Font.PLAIN, 18));
        reparaFordon.setBackground(Color.BLUE);
        reparaFordon.setForeground(Color.WHITE);


        /**
         * Koden hanterar reparation av ett fordon och uppdaterar information i en fil.
         * En dialogruta visas för att ange regplåten, kundens e-postadress, reparationens beskrivning och datum.
         * Om alla fält fylls i korrekt och informationen valideras, sparas reparationen i en separat fil.
         * Fordonets värde och inköpspris uppdateras i huvudfilen baserat på vissa villkor.
         * Om det uppstår fel vid läsning eller skrivning av filerna visas en felmeddelanderuta.
         */
        reparaFordon.addActionListener(e -> {

            //Ett fält där man skriv in vilket fordon man vill reparera
            String regPlåt = JOptionPane.showInputDialog(this, "vehicle Licence plate");
            //Om man har skrivit något
            if (regPlåt != null && !regPlåt.isEmpty()) {
                //Kollar om fordondet finns genom en statisk metod
                boolean isVehicle = tools_fordon.isFordonViaRegplåt(regPlåt);
                if (isVehicle) {

                    //Skapar fields för input
                    JTextField kundEmailField = new JTextField("");
                    JTextField reparationsBeskrivingField = new JTextField("");
                    JTextField datumField = new JTextField("");
                    //För att se att datumet är i rätt format
                    SimpleDateFormat simple_data_format = new SimpleDateFormat("yyyy-MM-dd");

                    Calendar calendar = Calendar.getInstance();
                    //Ett object för att hålla all jTextFields
                    Object[] InputDataFields = {
                            "Kund email: ", kundEmailField,
                            "Beskriving: ", reparationsBeskrivingField,
                            "Datum: ", datumField
                    };


                    //Visar formen men InputDataFields
                    int inputOption = JOptionPane.showConfirmDialog(this, InputDataFields, "Repair", JOptionPane.OK_CANCEL_OPTION);
                    //Om de väljer att gå vidare

                    if (inputOption == JOptionPane.OK_OPTION) {
                        //Hämta all text från formen
                        String kundEmail = kundEmailField.getText();
                        String datumString = datumField.getText();
                        String reparationsBeskrining = reparationsBeskrivingField.getText();
                        //Om text filerna är tomma
                        if (kundEmail.isEmpty() || datumString.isEmpty() || reparationsBeskrining.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean isCustomer = tools_kund.isKundVisaEmail(kundEmail);
                            if (isCustomer) {
                                try {
                                    //Sätter datumet till YEAR.MONTH.Day format
                                    calendar.setTime(simple_data_format.parse(datumString));
                                    BufferedReader bufferedReader = new BufferedReader(new FileReader(KundPath));
                                    String line;
                                    int kundNr = -1;
                                    while ((line = bufferedReader.readLine()) != null) {
                                        if (line.startsWith("E-post:" + kundEmail)) {
                                            bufferedReader.readLine(); // Skip the Lösenord line
                                            line = bufferedReader.readLine();
                                            if (line.startsWith("Kundnummer:")) {
                                                kundNr = Integer.parseInt(line.replace("Kundnummer:", "").trim());
                                                break;
                                            }
                                        }
                                    }
                                    //Om det hittades ett KundID
                                    if (kundNr > 0) {
                                        boolean foundFordonsnummer = false;
                                        StringBuilder stringBuilder = new StringBuilder();
                                        try (BufferedReader reader = new BufferedReader(new FileReader(FordonPath))) {
                                            String lines;
                                            while ((lines = reader.readLine()) != null) {
                                                if (lines.equals("Fordonsnummer:" + regPlåt)) {
                                                    foundFordonsnummer = true;
                                                    stringBuilder.append(lines + "\n");

                                                    for (int i = 0; i < 5; i++) {
                                                        stringBuilder.append(reader.readLine() + "\n");
                                                    }
                                                    double inköpsPris = Double.parseDouble(reader.readLine().replaceAll("Inköpspris:", ""));
                                                    double värde = Double.parseDouble(reader.readLine().replaceAll("Värde:", ""));

                                                    värde = värde * 1.2;
                                                    if (värde > inköpsPris) {
                                                        värde = inköpsPris;
                                                    }
                                                    stringBuilder.append("Inköpspris:" + inköpsPris).append("\n");
                                                    stringBuilder.append("Värde:" + värde).append("\n");

                                                    System.out.println(inköpsPris);
                                                    System.out.println(värde);
                                                } else if (foundFordonsnummer) {
                                                    // only append lines after the Fordonsnummer block
                                                    stringBuilder.append(lines).append("\n");
                                                    foundFordonsnummer = false;
                                                } else {
                                                    stringBuilder.append(lines).append("\n");
                                                }
                                            }


                                        } catch (IOException e5) {
                                            System.out.println("Error when writng to file");

                                        }

                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FordonPath))) {
                                            writer.write(stringBuilder.toString());
                                        } catch (IOException e6) {
                                            System.out.println("Error when writng to file");
                                        }

                                        //Skapar en reparation med alla information från formen samt kundens ID
                                        Reperering nyaReperation = new Reperering(reparationsID++, calendar, reparationsBeskrining, kundNr, regPlåt);
                                        boolean ärReperationSparad = tools_fordon.mataReparationTextFIl(nyaReperation);
                                        if (ärReperationSparad) {
                                            JOptionPane.showMessageDialog(this, "Repair completed!");

                                        } else {
                                            JOptionPane.showMessageDialog(this, "Unebale to repair");
                                        }
                                    } else {

                                        JOptionPane.showMessageDialog(this, "An error occurred. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (ParseException ex) {
                                    JOptionPane.showMessageDialog(this, "Date format (YYYY-MM-DD)", "Error", JOptionPane.ERROR_MESSAGE);

                                } catch (IOException ex) {
                                    JOptionPane.showMessageDialog(this, "An error occurred. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "No customer with the email: " + kund, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    //Om fordondet inte finns i text filen
                    JOptionPane.showMessageDialog(this, "Fordondet finns inte", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton Annual_entertainment = new JButton("Annual fix");

        /**.
         * Koden uppdaterar garantitiden och värdet för fordonen i en fil.
         * En dialogruta visas med ett meddelande om att ett år har tagits bort från alla garantier.
         * Om det uppstår fel vid läsning eller skrivning av filen visas en felmeddelanderuta.
         */
        Annual_entertainment.addActionListener(e -> {
            try {
                JOptionPane.showMessageDialog(this, "Removed a year from all guarantees", "Success", JOptionPane.INFORMATION_MESSAGE);
                File inputFile = new File(FordonPath);
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                StringBuilder updatedContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Garantitid:")) {
                        double Garantitid = Double.parseDouble(line.replaceAll("Garantitid:", ""));
                        double nyaGarantitid = Garantitid - 1;
                        if (nyaGarantitid < 0) {
                            nyaGarantitid = 0;
                        }

                        String garantiTidStr = "Garantitid:" + nyaGarantitid;
                        String nyaVärde;
                        double värde;
                        String inköpsPrisStr;
                        if (nyaGarantitid == 0) {
                            inköpsPrisStr = reader.readLine();
                            double inköpsPris = Double.parseDouble(inköpsPrisStr.replaceAll("Inköpspris:", ""));
                            reader.readLine();
                            värde = inköpsPris * 0.2;
                            nyaVärde = "Värde:" + värde;
                        } else {
                            inköpsPrisStr = reader.readLine();
                            värde = Double.parseDouble(reader.readLine().replaceAll("Värde:", ""));
                            nyaVärde = "Värde:" + (värde * 0.9);
                        }
                        updatedContent.append(garantiTidStr).append("\n").append(inköpsPrisStr).append("\n").append(nyaVärde).append("\n");
                    } else {
                        updatedContent.append(line).append("\n");
                    }
                }
                reader.close();
                //Skriva till filen med den nya information
                Files.write(inputFile.toPath(), updatedContent.toString().getBytes());
            } catch (IOException e5) {
                JOptionPane.showMessageDialog(this, "Error when writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        adminPanel.add(Annual_entertainment);


        add(adminPanel);
        adminPanel.add(buttonPanel, BorderLayout.CENTER);
        //Visar allt
        setVisible(true);
    }

}
