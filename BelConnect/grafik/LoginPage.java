
package Slutupg.grafik;
import Slutupg.Kund;
import Slutupg.tools.tools_file;
import Slutupg.tools.tools_kund;


import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginPage {
    static String KundPath = "info/Kund.txt";
    static String FordonPath = "info/fordon.txt";
    static String ReparationPath = "info/Reperering.txt";
    static String imgPath = "broomBroo.jpg";
    private JFrame frame;
    private JPanel panel;
    private JLabel emailLabel, passwordLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginPage() {
        frame = new JFrame("Login Form");
        panel = new JPanel(new GridBagLayout());

        ImageIcon imageIcon = new ImageIcon(imgPath);
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Create a JLabel and set the resized image
        JLabel label = new JLabel(resizedIcon);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridy = 0; // Set the initial y position
        constraints.gridx = 1; // Set the initial x position

        // Add the label to the panel with constraints
        panel.add(label, constraints);

        //Skapar labels för inmattnings fälten
        emailLabel = new JLabel("Your email:");
        passwordLabel = new JLabel("Your Password:");

        //Skapar inmattnings fälten
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);

        //Skapar knapparna samt deras panel
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);


        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(buttonPanel, constraints);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        /**
         * Hämtar email och lösenord från inmatningsfälten.
         * Kontrollerar om inmatningsfälten är inte är tomma och visar ett error om de är.
         * Annars läser innehållet från filen "kund.txt" och kontrollerar om det finns en matchande e-postadress och lösenord.
         * Om en matchning hittas, skapar en instans av Kund-klassen och öppnar antingen en AdminPage eller Kundgrafik baserat på email
         */
        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            //Kolla om fälten är tomma
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Fyll i alla fält", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                // Logga in användaren
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(KundPath));
                    String s;
                    String line = bufferedReader.readLine();
                    //Kollar om det finns ett mail som matchar med input, om så ge fel meddelande
                    Kund konto = null;
                    boolean ärKund = tools_kund.isKundVisaEmail(email);
                    if(ärKund){

                        while (line != null) {
                            s = line;
                            if (s.equals("E-post:"+email)) {
                                s = bufferedReader.readLine();
                                String passTemp = s.substring(9);
                                if (password.equals(passTemp)) {
                                    int kundNr = Integer.parseInt(bufferedReader.readLine().substring(11));
                                    String namn = bufferedReader.readLine().substring(5);
                                    String[] namnParts = namn.split(",");
                                    String fornamn = namnParts[0];
                                    String efternamn = namnParts[1];
                                    String adress = bufferedReader.readLine().substring(7);
                                    String postOrt = bufferedReader.readLine().substring(11);
                                    String telefonNr = bufferedReader.readLine().substring(14);
                                    konto = new Kund(kundNr, fornamn, efternamn, adress, postOrt, telefonNr, email, password);
                                    if (konto.getEmail().endsWith("@admin.se")) {
                                        AdminPage adminSida = new AdminPage(konto);
                                    } else {
                                        Kundgrafik kundgrafik = new Kundgrafik(konto);
                                    }
                                    break;
                                }

                            }
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }else{
                        JOptionPane.showMessageDialog(frame, "Wrong password or username", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Ett fel har uppstod, testa igen om några minuter", "Fel", JOptionPane.ERROR_MESSAGE);

                }
            }



        });
        panel.setBackground(Color.CYAN);
        buttonPanel.setBackground(Color.cyan);


        /**
         * Skapar en AtomicInteger variabel för att räkna antalet skapade konton.
         * Hämtar det högsta kundID-numret från "kund.txt" genom att använda metoden HögstaNummer i tools_file-klassen.
         */
        registerButton.addActionListener((ActionEvent e) -> {
            AtomicInteger skapadeKonton = new AtomicInteger();
            int kundIdIndex;
            try {
                kundIdIndex = tools_file.HögstaNummer(KundPath);
            } catch (IOException f) {
                throw new RuntimeException(f);
            }


            // Create a custom dialog by extending JDialog
            JDialog dialog = new JDialog(frame, "Register an Account", true);
            JPanel registerPanel = new JPanel(new GridBagLayout());
            GridBagConstraints registerConstraints = new GridBagConstraints();
            registerConstraints.insets = new Insets(10, 10, 10, 10);

            JLabel firstNameLabel = new JLabel("First Name:");
            JLabel passwordLabel = new JLabel("Password");
            JLabel lastNameLabel = new JLabel("Last Name:");
            JLabel addressLabel = new JLabel("Address:");
            JLabel postOrtLabel = new JLabel("Postal Code:");
            JLabel telefonNrLabel = new JLabel("Telefon Number:");
            JLabel emailLabel = new JLabel("Email:");

            JTextField firstNameField = new JTextField(20);
            JTextField lastNameField = new JTextField(20);
            JTextField addressField = new JTextField(20);
            JTextField postOrtField = new JTextField(20);
            JTextField telefonNrField = new JTextField(20);
            JTextField emailField = new JTextField(20);
            JTextField passwordField = new JTextField(20);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 0;
            registerPanel.add(firstNameLabel, registerConstraints);


            registerConstraints.gridx = 1;
            registerPanel.add(firstNameField, registerConstraints);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 1;
            registerPanel.add(lastNameLabel, registerConstraints);

            registerConstraints.gridx = 1;
            registerPanel.add(lastNameField, registerConstraints);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 2;
            registerPanel.add(addressLabel, registerConstraints);

            registerConstraints.gridx = 1;
            registerPanel.add(addressField, registerConstraints);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 3;
            registerPanel.add(postOrtLabel, registerConstraints);

            registerConstraints.gridx = 1;
            registerPanel.add(postOrtField, registerConstraints);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 4;
            registerPanel.add(telefonNrLabel, registerConstraints);

            registerConstraints.gridx = 1;
            registerPanel.add(telefonNrField, registerConstraints);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 5;
            registerPanel.add(emailLabel, registerConstraints);

            registerConstraints.gridx = 1;
            registerPanel.add(emailField, registerConstraints);

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 6;
            registerPanel.add(passwordLabel, registerConstraints);

            registerConstraints.gridx = 1;
            registerConstraints.gridy = 6;
            registerPanel.add(passwordField, registerConstraints);

            dialog.getContentPane().add(registerPanel);
            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Prevent closing by default

            /**
             * Hanterar händelsen när användaren klickar på "OK"-knappen i registreringsdialogen.
             * Hämtar email från inmatningsfältet och kontrollerar om den redan är upptagen.
             * Om email inte är upptagen, hämtas resten av värdena från formuläret och kontrolleras om de är tomma.
             * Om inga fält är tomma, skapas en ny instans av Kund-klassen och sparar inmatningen i textfilen "kund.txt".
             * Visar lämpliga meddelanden beroende på om inmatningen lyckades eller om det finns några fel.
             */
            ActionListener okListener = ok -> {
                String email = emailField.getText().replaceAll("\\s", "");
                boolean upptagenEmail = tools_kund.isKundVisaEmail(email);

                if (!upptagenEmail) {

                    //Hämta resterande värden från formen
                    String firstName = firstNameField.getText().replaceAll("\\s", "");
                    String lastName = lastNameField.getText().replaceAll("\\s", "");
                    String address = addressField.getText().replaceAll("\\s", "");
                    String postOrt = postOrtField.getText().replaceAll("\\s", "");
                    String telefonNr = telefonNrField.getText().replaceAll("\\s", "");
                    String lösenord = passwordField.getText().replaceAll("\\s", "");

                    //Kolla om fälten är tomma
                    if (!firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && !postOrt.isEmpty() && !telefonNr.isEmpty() && !email.isEmpty()) {
                        //Skapa ett kund object
                        Kund kund = new Kund(kundIdIndex+1+ skapadeKonton.get(), firstName, lastName, address, postOrt, telefonNr, email, lösenord);
                        boolean sparadInmattning = tools_kund.mataKundTextFil(kund);
                        if (sparadInmattning) {
                            JOptionPane.showMessageDialog(frame, "Account registered!");
                            skapadeKonton.set(skapadeKonton.get() + 1);
                            dialog.dispose();

                        } else {
                            JOptionPane.showMessageDialog(frame, "Typing error!", "Error", JOptionPane.ERROR_MESSAGE);


                        }
                    }else{
                        JOptionPane.showMessageDialog(frame, "Enter all fields!", "Error", JOptionPane.ERROR_MESSAGE);

                    }

                } else {
                    JOptionPane.showMessageDialog(registerPanel, "The email is already in use!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            };

            // lambood btn 1 (ok
            JButton okButton = new JButton("OK");

            okButton.addActionListener(okListener);
            JButton cancelButton = new JButton("Cancel");

            cancelButton.addActionListener(e12 -> {
                dialog.dispose();
            });

            registerConstraints.gridx = 0;
            registerConstraints.gridy = 7;
            registerPanel.add(okButton, registerConstraints);
            registerConstraints.gridx = 1;
            registerPanel.add(cancelButton, registerConstraints);

            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });


    }

    public static void main(String[] args) {
        LoginPage form = new LoginPage();
    }
}