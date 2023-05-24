
package Slutupg.grafik;

import Slutupg.Kund;
import Slutupg.tools.tools_fordon;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Kundgrafik extends JFrame {
    private Kund kund;
    static String KundPath = "info/Kund.txt";
    static String FordonPath = "info/fordon.txt";
    static String ReparationPath = "info/Reperering.txt";

    public Kundgrafik(Kund kund) {
        this.kund = kund;

        setTitle("Customer information");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Skapar panel för att visa kund information
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JPanel kundPanel = new JPanel();

        //Skapa logga ut knapp
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(logoutButton);
        JButton visaInformation = new JButton("Show information");
        visaInformation.addActionListener(e -> {
            String report = kund.toString();
            JOptionPane.showMessageDialog(kundPanel, report);
        });
        buttonPanel.add(visaInformation);


        JButton minaFordon = new JButton("My vehicles");

        /**
         * Läser innehållet från filen "fordon.txt" och letar efter fordonsnummer som matchar kundens ägande.
         * Visar fordonsnummer i en dialogruta om de hittas, annars visas ett meddelande om att inga fordon ägs.
         * Felmeddelanden visas vid problem med filinläsningen.
         */
        minaFordon.addActionListener(e -> {

            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                String line;
                String fordonsNummer = "";
                String alla = "";
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("Fordonsnummer:")) {
                        fordonsNummer = line.replaceAll("Fordonsnummer:", "");
                        if (bufferedReader.readLine().equals("Ägare:" + kund.getKundNr())) {
                            alla += fordonsNummer + "\n";
                        }
                    }
                }
                if (!alla.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Your vehicles:\n" + alla);
                } else {
                    JOptionPane.showMessageDialog(this, "You dont own any vehicles");

                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        JButton sökaMittFordon = new JButton("Search for vehicle");
        /**
         * Frågar användaren efter ett regplåt och kontrollerar om det är ett giltigt fordon.
         * Om det är giltigt, läser innehållet från filen "fordon.txt" och letar efter information om det angivna fordonet.
         * Visar fordonsinformation i en dialogruta om det hittas och användaren äger fordonet, annars visas lämpliga felmeddelanden.
         */
        sökaMittFordon.addActionListener(e -> {
            String regPlåt = JOptionPane.showInputDialog(this, "vehicle licence plate");
            if (regPlåt != null && !regPlåt.isEmpty()) {
                boolean ärFordon = tools_fordon.isFordonViaRegplåt(regPlåt);
                if (ärFordon) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(FordonPath));
                        String fordonsInformation = "";
                        String line = "";
                        boolean ägerFordondet = true;
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.equals("Fordonsnummer:" + regPlåt)) {
                                fordonsInformation += line + "\n"; // Add the fordonnummer line
                                String ägare = bufferedReader.readLine();
                                if (ägare.equals("Ägare:" + kund.getKundNr())) {
                                    while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                                        fordonsInformation += line + "\n"; // Add the other lines until an empty line is found
                                    }
                                } else {
                                    //Fordonet ägs inte av, kunden som är inloggad
                                    ägerFordondet = false;
                                    break;
                                }
                            }
                        }
                        bufferedReader.close();
                        //Visar information baserat om man äger fordondet
                        if (ägerFordondet) {
                            JOptionPane.showMessageDialog(this, fordonsInformation);
                        } else {
                            JOptionPane.showMessageDialog(this, "You dont own this vehicle");
                        }
                        //Om något gick med att läsa filen
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    //Om fordondet inte finns i text filen
                    JOptionPane.showMessageDialog(this, "vehicle does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton visaReperationsHistorik = new JButton("Show repair history");
        /**
         * Frågar användaren efter ett regplåt och kontrollerar om det är ett giltigt fordon.
         * Om det är giltigt, läser innehållet från filen "fordon.txt" och kontrollerar om användaren äger fordonet.
         * Om användaren äger fordonet, läser innehållet från filen "reparation.txt" och visar reparationens historik för det angivna fordonet.
         * Visar reparationens historik i en dialogruta om det finns, annars visas lämpliga felmeddelanden.
         */
        visaReperationsHistorik.addActionListener(e -> {
            //Ett fält där man skriv in vilket fordon man letar efter
            String regPlåt = JOptionPane.showInputDialog(this, "vehicle licence plate");
            //Om man har skrivit något
            if (regPlåt != null && !regPlåt.isEmpty()) {
                boolean ärFordon = tools_fordon.isFordonViaRegplåt(regPlåt);
                if (ärFordon) {
                    try {
                        BufferedReader bufferedReader;
                        bufferedReader = new BufferedReader(new FileReader(FordonPath));
                        String line = "";
                        //Boolean för att kolla om man äger fordonet man vill kolla efter
                        boolean ägerFordondet = true;
                        //Läser av filen så längde den inte är tom
                        while ((line = bufferedReader.readLine()) != null) {
                            //Om den hittar rätta fordonet så kollar den vem ägaren är
                            if (line.equals("Fordonsnummer:" + regPlåt)) {
                                String ägare = bufferedReader.readLine();
                                if (ägare.equals("Ägare:" + kund.getKundNr())) {
                                    ägerFordondet = true;
                                    break;
                                } else {
                                    ägerFordondet = false;
                                    break;
                                }
                            }
                        }
                        if (ägerFordondet) {
                            StringBuilder reparationsHistorikStrBuilder = new StringBuilder();
                            bufferedReader = new BufferedReader(new FileReader(ReparationPath));
                            //Läser av filen så längde den inte är tom
                            while ((line = bufferedReader.readLine()) != null) {
                                //Om den hittar rätta fordonet så kollar den vem ägaren är
                                if (line.equals("Fordonsnummer:" + regPlåt)) {
                                    for (int i = 0; i < 3; i++) {
                                        reparationsHistorikStrBuilder.append(bufferedReader.readLine()).append("\n");
                                    }
                                    reparationsHistorikStrBuilder.append("\n");
                                }
                            }
                            if(reparationsHistorikStrBuilder.length()>0){
                                JOptionPane.showMessageDialog(this, reparationsHistorikStrBuilder);
                            }else{
                                JOptionPane.showMessageDialog(this, "vehicle does not have any repair");

                            }

                        } else {
                            JOptionPane.showMessageDialog(this, "You dont own a vehicle", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "vehicle does not exist", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }

        });

        logoutButton.setBounds(50, 50, 200, 50);
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutButton.setBackground(Color.BLUE);
        logoutButton.setForeground(Color.WHITE);

        visaReperationsHistorik.setBounds(50, 50, 200, 50);
        visaReperationsHistorik.setFont(new Font("Arial", Font.PLAIN, 18));
        visaReperationsHistorik.setBackground(Color.BLUE);
        visaReperationsHistorik.setForeground(Color.WHITE);

        sökaMittFordon.setBounds(50, 50, 200, 50);
        sökaMittFordon.setFont(new Font("Arial", Font.PLAIN, 18));
        sökaMittFordon.setBackground(Color.BLUE);
        sökaMittFordon.setForeground(Color.WHITE);

        minaFordon.setBounds(50, 50, 200, 50);
        minaFordon.setFont(new Font("Arial", Font.PLAIN, 18));
        minaFordon.setBackground(Color.BLUE);
        minaFordon.setForeground(Color.WHITE);


        visaInformation.setBounds(50, 50, 200, 50);
        visaInformation.setFont(new Font("Arial", Font.PLAIN, 18));
        visaInformation.setBackground(Color.BLUE);
        visaInformation.setForeground(Color.WHITE);


        kundPanel.setBackground(Color.CYAN);
        buttonPanel.setBackground(Color.CYAN);
        buttonPanel.add(visaReperationsHistorik);
        buttonPanel.add(sökaMittFordon);
        buttonPanel.add(minaFordon);

        //Lägger till informationen i JFramen
        add(kundPanel);
        kundPanel.add(buttonPanel, BorderLayout.CENTER);
        //Visar allt
        setVisible(true);
    }
}

