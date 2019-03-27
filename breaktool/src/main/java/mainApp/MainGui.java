package mainApp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainApp.toolClasses.Calendar;
import mainApp.toolClasses.Entrymanagement;
import mainApp.toolClasses.FileWebOpener;
import mainApp.toolClasses.StringEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.SystemTray;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


public class MainGui extends Application {
    private static boolean trayisCreated = false; //is true if a systemtray is created
    private static String name = "kein";
    private static SystemTray tray; // the Systemtray we put the Icons in
    private static TrayIcon trayIconProgramm; //the main trayIconProgramm (a cup of whatever: TrayIconProgramm.png)
    private static TrayIcon trayIconCalls; //the other trayIconProgramm (a handset: TrayIconCalls.png)
    private static Entrymanagement verwaltung = new Entrymanagement(); // this is where all data is stores e.g Einträge
    private BorderPane mainpane = new BorderPane(); //mainpane of the stage
    private File backupFolder = new File(System.getProperty("user.home") + File.separator + "documents" + File.separator + "Breaktool"); //the location where all exportable files and the serialization is stored
    private File backup = new File(backupFolder.getAbsolutePath(), "BreaktoolExport.ser"); // the file where the serialization is in
    private MainGui mainGui = this; //stored the instance of the mainclass
    private Stage stage; // the programs current stage
    private boolean shouldBeHidden = false; // decides whether the window of this programm is hidden of not


    public static void main(String[] args) {
        launch(args);
    }

    public static boolean isTrayisCreated() {
        return trayisCreated;
    }

    public void setTrayisCreated(boolean trayisCreated) {
        MainGui.trayisCreated = trayisCreated;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        MainGui.name = name;
    }

    public static void refreshToolTip() {
        trayIconProgramm.setToolTip("Breaktool v.1.4.1" +
                "\nAnrufe: " + verwaltung.getZahlAnrufe() +
                "\nRückrufe: " + verwaltung.getRückrufeListe().size() +
                "\nReasoncodes: " + verwaltung.getPausensituationen().size());
    }

    public static TrayIcon getTrayIconCalls() {
        return trayIconCalls;
    }

    public static void setTrayIconCalls(TrayIcon trayIconCalls) {
        MainGui.trayIconCalls = trayIconCalls;
    }

    public static Entrymanagement getVerwaltung() {
        return verwaltung;
    }

    public static void setVerwaltung(Entrymanagement verwaltung) {
        MainGui.verwaltung = verwaltung;
    }

    @Override
    public void start(Stage primaryStage) {
        //if (!System.getProperty("os.name").startsWith("Mac")) {

        shouldBeHidden = true;
        //}
        createApp(trayisCreated, primaryStage, name);
        trayisCreated = true;


    }

    void show() {
        stage.show();
    }

    private void createtray() {
        try {
            // ensure awt toolkit is initialized.
            Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                return;
            }

            // set up a system tray icon.
            tray = SystemTray.getSystemTray();
            java.awt.Image image = ImageIO.read(getClass().getResourceAsStream("/icons/TrayIconProgramm.png"));
            trayIconProgramm = new TrayIcon(image);
            trayIconCalls = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("/icons/TrayIconCalls.png")));
            tray.add(trayIconCalls);
            refreshToolTip();

            //java.awt.MenuItem openItem = new java.awt.MenuItem("Start");
            MenuItem exitItem = new MenuItem("Schließen");
            MenuItem restartItem = new MenuItem("Neustart");
            MenuItem resetItem = new MenuItem("Reset");

            MenuItem rückrufeListeViewItem = new MenuItem("Rückruf hinzufügen");
            MenuItem anrufeViewItem = new MenuItem("Begründung hinzufügen");
            MenuItem reasoncodeViewItem = new MenuItem("Reasoncode hinzufügen");

            MenuItem anrufEntfernenItem = new MenuItem("Anruf entfernen");
            MenuItem anrufHinzufügenItem = new MenuItem("Anruf hinzufügen");

            Menu speichernItem = new Menu("Speichern");
            MenuItem exportAlsZwischenablageItem = new MenuItem("In Zwischenablage");
            MenuItem exportAlsTxtItem = new MenuItem("In Textdatei");
            MenuItem exportAlsEmailItem = new MenuItem("Als Email");

            Menu produkteItem = new Menu("Produkte");
            Menu computerhilfeItem = new Menu("Computerhilfe");
            Menu highspeedItem = new Menu("DSL");
            Menu magentaTVItem = new Menu("MagentaTV");

            MenuItem coHiLeistungsübersichtItem = new MenuItem("Leistungsübersicht");
            Menu coHiWeitereInfosItem = new Menu("Details");
            MenuItem coHiAGB = new MenuItem("AGB");

            MenuItem coHiS = new MenuItem("Computerhilfe S");
            MenuItem coHiM = new MenuItem("Computerhilfe M");
            MenuItem coHiL = new MenuItem("Computerhilfe L");

            MenuItem highspeedLeistungen = new MenuItem("Leistungsübersicht");
            Menu minimalLeistungenDetails = new Menu("Minimalleistungen");
            Menu highspeedDetails = new Menu("Details");
            MenuItem highspeedAGB = new MenuItem("AGB");

            MenuItem highspeedDetailsS = new MenuItem("Magenta Zuhause S");
            MenuItem highspeedDetailsM = new MenuItem("Magenta Zuhause M");
            MenuItem highspeedDetailsL = new MenuItem("Magenta Zuhause L");
            MenuItem highspeedDetailsXL = new MenuItem("Magenta Zuhause XL");

            MenuItem minimalLeistungenDetailsS = new MenuItem("Magenta Zuhause S");
            MenuItem minimalLeistungenDetailsM = new MenuItem("Magenta Zuhause M");
            MenuItem minimalLeistungenDetailsL = new MenuItem("Magenta Zuhause L");
            MenuItem minimalLeistungenDetailsXL = new MenuItem("Magenta Zuhause XL");

            MenuItem magentaTVLeistungsübersicht = new MenuItem("Leistungsübersicht");
            MenuItem magentaTVHDSender = new MenuItem("Senderliste");

            MenuItem öffnenItem = new MenuItem("Öffnen");

            Menu seitenMenu = new Menu("Seiten");
            MenuItem timeboardItem = new MenuItem("Timeboard");
            MenuItem sksWmsItem = new MenuItem("SKS-WMS");
            MenuItem irrpItem = new MenuItem("IRRP Client");
            MenuItem onlineStatistikenItem = new MenuItem("Online Statistiken");
            MenuItem svtItem = new MenuItem("SVT");

            Menu routerFAQMenu = new Menu("RouterFAQ");
            Menu routerFAQFritzBoxItem = new Menu("FRITZ!Box Übersicht");
            MenuItem fritzbox7490 = new MenuItem("FRITZ!Box 7490");
            MenuItem fritzbox7590 = new MenuItem("FRITZ!Box 7590");
            MenuItem fritzBox7390 = new MenuItem("FRITZ!Box 7390");
            MenuItem fritzBox7330 = new MenuItem("FRITZ!Box 7330");
            MenuItem fritzBox7430 = new MenuItem("FRITZ!Box 7430");
            MenuItem fritzBox7530 = new MenuItem("FRITZ!Box 7530");
            Menu fritzboxAndere = new Menu("Andere");

            //fritzBox Andere Entries:
            MenuItem fritzbox = new MenuItem("FRITZ!Box");
            MenuItem fritzbox2030 = new MenuItem("FRITZ!Box 2030");
            MenuItem fritzbox2031 = new MenuItem("FRITZ!Box 2031");
            MenuItem fritzbox2070 = new MenuItem("FRITZ!Box 2070");
            MenuItem fritzbox2110 = new MenuItem("FRITZ!Box 2110");
            MenuItem fritzbox2170 = new MenuItem("FRITZ!Box 2170");
            MenuItem fritzbox3020 = new MenuItem("FRITZ!Box 3020");
            MenuItem fritzbox3030 = new MenuItem("FRITZ!Box 3030");
            MenuItem fritzbox3050 = new MenuItem("FRITZ!Box 3050");
            MenuItem fritzbox3070 = new MenuItem("FRITZ!Box 3070");
            MenuItem fritzbox3130 = new MenuItem("FRITZ!Box 3130");
            MenuItem fritzbox3131 = new MenuItem("FRITZ!Box 3131");
            MenuItem fritzbox3170 = new MenuItem("FRITZ!Box 3170");
            MenuItem fritzbox3270 = new MenuItem("FRITZ!Box 3270");
            MenuItem fritzbox3270v3 = new MenuItem("FRITZ!Box 3270 V3");
            MenuItem fritzbox3272 = new MenuItem("FRITZ!Box 3272");
            MenuItem fritzbox3370 = new MenuItem("FRITZ!Box 3370");
            MenuItem fritzbox3390 = new MenuItem("FRITZ!Box 3390");
            MenuItem fritzbox3490 = new MenuItem("FRITZ!Box 3490");
            MenuItem fritzbox4020 = new MenuItem("FRITZ!Box 4020");
            MenuItem fritzbox4040 = new MenuItem("FRITZ!Box 4040");
            MenuItem fritzbox5010 = new MenuItem("FRITZ!Box 5010");
            MenuItem fritzbox5012 = new MenuItem("FRITZ!Box 5012");
            MenuItem fritzbox5050 = new MenuItem("FRITZ!Box 5050");
            MenuItem fritzbox5113 = new MenuItem("FRITZ!Box 5113");
            MenuItem fritzbox5124 = new MenuItem("FRITZ!Box 5124");
            MenuItem fritzbox5140 = new MenuItem("FRITZ!Box 5140");
            MenuItem fritzbox5490 = new MenuItem("FRITZ!Box 5490");
            MenuItem fritzbox5491 = new MenuItem("FRITZ!Box 5491");
            MenuItem fritzbox6320Cable = new MenuItem("FRITZ!Box 6320 Cable");
            MenuItem fritzbox6320V2Cable = new MenuItem("FRITZ!Box 6320 V2 Cable");
            MenuItem fritzbox6340Cable = new MenuItem("FRITZ!Box 6340 Cable");
            MenuItem fritzbox6360Cable = new MenuItem("FRITZ!Box 6360 Cable");
            MenuItem fritzbox6430Cable = new MenuItem("FRITZ!Box 6430 Cable");
            MenuItem fritzbox6490Cable = new MenuItem("FRITZ!Box 6490 Cable");
            MenuItem fritzbox6590Cable = new MenuItem("FRITZ!Box 6590 Cable");
            MenuItem fritzbox6591Cable = new MenuItem("FRITZ!Box 6591 Cable");
            MenuItem fritzbox6810LTE = new MenuItem("FRITZ!Box 6810 LTE");
            MenuItem fritzbox6820LTE = new MenuItem("FRITZ!Box 6820 LTE");
            MenuItem fritzbox6840LTE = new MenuItem("FRITZ!Box 6840 LTE");
            MenuItem fritzbox6842LTE = new MenuItem("FRITZ!Box 6842 LTE");
            MenuItem fritzbox6890LTE = new MenuItem("FRITZ!Box 6890 LTE");
            MenuItem fritzbox7050 = new MenuItem("FRITZ!Box 7050");
            MenuItem fritzbox7112 = new MenuItem("FRITZ!Box 7112");
            MenuItem fritzbox7113 = new MenuItem("FRITZ!Box 7113");
            MenuItem fritzbox7140 = new MenuItem("FRITZ!Box 7140");
            MenuItem fritzbox7141 = new MenuItem("FRITZ!Box 7141");
            MenuItem fritzbox7170 = new MenuItem("FRITZ!Box 7170");
            MenuItem fritzbox7170V2 = new MenuItem("FRITZ!Box 7170 V2");
            MenuItem fritzbox7170SL = new MenuItem("FRITZ!Box 7170 SL");
            MenuItem fritzbox7170SLV2 = new MenuItem("FRITZ!Box 7170 SL V2");
            MenuItem fritzbox7240 = new MenuItem("FRITZ!Box 7240");
            MenuItem fritzbox7270V1 = new MenuItem("FRITZ!Box 7270 V1");
            MenuItem fritzbox7270V2 = new MenuItem("FRITZ!Box 7270 V2");
            MenuItem fritzbox7270V3 = new MenuItem("FRITZ!Box 7270 V3");
            MenuItem fritzbox7272 = new MenuItem("FRITZ!Box 7272");
            MenuItem fritzbox7312 = new MenuItem("FRITZ!Box 7312");
            MenuItem fritzbox7320 = new MenuItem("FRITZ!Box 7320");
            MenuItem fritzbox7330SL = new MenuItem("FRITZ!Box 7330 SL");
            MenuItem fritzbox7340 = new MenuItem("FRITZ!Box 7340");
            MenuItem fritzbox7360V1 = new MenuItem("FRITZ!Box 7360 V1");
            MenuItem fritzbox7360V2 = new MenuItem("FRITZ!Box 7360 V2");
            MenuItem fritzbox7360V3 = new MenuItem("FRITZ!Box 7360 V3");
            MenuItem fritzbox7362SL = new MenuItem("FRITZ!Box 7362 SL");
            MenuItem fritzbox7369 = new MenuItem("FRITZ!Box 7369");
            MenuItem fritzbox7412 = new MenuItem("FRITZ!Box 7412");
            MenuItem fritzbox7520 = new MenuItem("FRITZ!Box 7520");
            MenuItem fritzbox7560 = new MenuItem("FRITZ!Box 7560");
            MenuItem fritzbox7570VDSL = new MenuItem("FRITZ!Box 7570 VDSL");
            MenuItem fritzbox7580 = new MenuItem("FRITZ!Box 7580");
            MenuItem fritzbox7581 = new MenuItem("FRITZ!Box 7581");
            MenuItem fritzbox7582 = new MenuItem("FRITZ!Box 7582");
            MenuItem fritzbox7583 = new MenuItem("FRITZ!Box 7583");
            MenuItem fritzboxFon = new MenuItem("FRITZ!Box Fon");
            MenuItem fritzboxFonAta = new MenuItem("FRITZ!Box Fon ata");
            MenuItem fritzboxFonWLAN = new MenuItem("FRITZ!Box Fon WLAN");
            MenuItem fritzboxSL = new MenuItem("FRITZ!Box SL");
            MenuItem fritzboxSLWLAN = new MenuItem("FRITZ!Box SL WLAN");
            MenuItem fritzboxFon7150 = new MenuItem("FRITZ!Box Fon 7150");


            Menu routerFAQSpeedportItem = new Menu("Speedport Übersicht");

            MenuItem speedport200 = new MenuItem("Speedport 200");
            MenuItem speedport201 = new MenuItem("Speedport 201");
            MenuItem speedport221 = new MenuItem("Speedport 221");
            MenuItem speedport300 = new MenuItem("Speedport 300");
            MenuItem speedport300HS = new MenuItem("Speedport 300 HS");
            MenuItem speedport400P = new MenuItem("Speedport 400P");
            MenuItem speedport500V = new MenuItem("Speedport 500V");
            MenuItem speedportW303V = new MenuItem("Speedport W 303V");
            MenuItem speedportW303VTypA = new MenuItem("Speedport W 303V Typ A");
            MenuItem speedportW303VTypB = new MenuItem("Speedport W 303V Typ B");
            MenuItem speedportW500 = new MenuItem("Speedport W 500");
            MenuItem speedportW500V = new MenuItem("Speedport W 500V");
            MenuItem speedportW501V = new MenuItem("Speedport W 501V");
            MenuItem speedportW502V = new MenuItem("Speedport W 502V");
            MenuItem speedportW503V = new MenuItem("Speedport W 503V");
            MenuItem speedportW503VTypA = new MenuItem("Speedport W 503V Typ A");
            MenuItem speedportW503VTypC = new MenuItem("Speedport W 503V Typ C");
            MenuItem speedportW504V = new MenuItem("Speedport W 504V");
            MenuItem speedportW700V = new MenuItem("Speedport W 700V");
            MenuItem speedportW701V = new MenuItem("Speedport W 701V");
            MenuItem speedportW720V = new MenuItem("Speedport W 720V");
            MenuItem speedportW721V = new MenuItem("Speedport W 721V");
            MenuItem speedportW722V = new MenuItem("Speedport W 722V");
            MenuItem speedportW722VTypA = new MenuItem("Speedport W 722V Typ A");
            MenuItem speedportW722VTypB = new MenuItem("Speedport W 722V Typ B");
            MenuItem speedportHSPA = new MenuItem("Speedport HSPA");
            MenuItem speedportPro = new MenuItem("Speedport Pro");
            MenuItem digitalisierungsboxBasic = new MenuItem("Digitalisierungsbox Basic");
            MenuItem digitalisierungsboxSmart = new MenuItem("Digitalisierungsbox Smart");
            MenuItem digitalisierungsboxStandard = new MenuItem("Digitalisierungsbox Standard");
            MenuItem digitalisierungsboxPremium = new MenuItem("Digitalisierungsbox Premium");
            MenuItem zyxel5501 = new MenuItem("Zyxel 5501");
            MenuItem zyxel6501 = new MenuItem("Zyxel 6501");

            MenuItem speedportW723VTypA = new MenuItem("Speedport W 723V Typ A");
            MenuItem speedportW723VTypB = new MenuItem("Speedport W 723V Typ B");
            MenuItem speedportW724VTypA = new MenuItem("Speedport W 724V Typ A");
            MenuItem speedportW724VTypB = new MenuItem("Speedport W 724V Typ B");
            MenuItem speedportW724VTypC = new MenuItem("Speedport W 724V Typ C");
            MenuItem speedportW900V = new MenuItem("Speedport W 900V");
            MenuItem speedportW920V = new MenuItem("Speedport W 920V");
            MenuItem speedportW921V = new MenuItem("Speedport W 921V");
            MenuItem speedportW922V = new MenuItem("Speedport W 922V");
            MenuItem speedportW925V = new MenuItem("Speedport W 925V");
            MenuItem speedportEntry = new MenuItem("Speedport Entry");
            MenuItem speedportEntry2 = new MenuItem("Speedport Entry 2");
            MenuItem speedportHybrid = new MenuItem("Speedport Hybrid");
            MenuItem speedportLTE = new MenuItem("Speedport LTE");
            MenuItem speedportLTE2 = new MenuItem("Speedport LTE II");


            MenuItem speedportSmart1 = new MenuItem(" Speedport Smart");
            MenuItem speedportSmart2 = new MenuItem(" Speedport Smart 2");
            MenuItem speedportSmart3 = new MenuItem(" Speedport Smart 3");
            Menu speedportAndere = new Menu(" Andere");

            Menu telekomHilftItem = new Menu("Telekom hilft");
            MenuItem telekomHilftEmail = new MenuItem("E-Mail");
            MenuItem telekomHilftTelefon = new MenuItem("Telefon");
            MenuItem telekomHilftDSL = new MenuItem("DSL");
            MenuItem telekomHilftEntertain = new MenuItem("MagentaTV");
            MenuItem telekomHilftNorton = new MenuItem("Norton Security");
            MenuItem telekomHilftAGB = new MenuItem("Telekom AGB");


            MenuItem winsItem = new MenuItem("WINS");

            Menu google = new Menu("Google");
            MenuItem googleSearch = new MenuItem("Suche");
            MenuItem googlePictures = new MenuItem("Bilder");

            MenuItem winnersCircleItem = new MenuItem("Winners Circle");
            MenuItem csp3VoucherItem = new MenuItem("CSP3 / Voucher");
            MenuItem tMapItem = new MenuItem("T-Map");
            MenuItem aspaItem = new MenuItem("ASPA");
            MenuItem retourenItem = new MenuItem("Retourenschein");
            MenuItem testMailItem = new MenuItem("Testmailaccount");

            ActionListener listener = e -> Platform.runLater(() -> {
                if (MultiWindowsHandler.getSize() == 0) {
                    MainGui gui = new MainGui();
                    mainGui.setShouldBeHidden(true);
                    gui.start(new Stage());
                } else {
                    Platform.runLater(() -> {
                        MultiWindowsHandler.getOpenStage().hide();
                        MultiWindowsHandler.remove();
                    });

                }

            });

            //}
            exportAlsZwischenablageItem.addActionListener(e -> {
                /*if(verwaltung.getZahlAnrufe()!=0 &
                        verwaltung.getRückrufeListe().size()!=0
                        & verwaltung.getPausensituationen().size()!=0
                        & verwaltung.getReasonString().size()!=0){*/

                String f;
                f = null;
                verwaltung.restore();

                try (ByteArrayOutputStream a = new ByteArrayOutputStream()) {
                    verwaltung.exportAlsTxt(a);
                    f = a.toString();

                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(f), null);
            });

            exportAlsTxtItem.addActionListener(e -> Platform.runLater(() -> {
                MainGui gui = new MainGui();
                verwaltung.restore();
                FileChooser fc = new FileChooser();
                fc.setTitle("Wählen sie einen Speicherort aus");
                java.util.Calendar s1 = java.util.Calendar.getInstance();
                fc.setInitialFileName("Terminbericht vom " + Calendar.getCurrentDate());
                try {
                    File file = fc.showSaveDialog(stage);
                    if (file != null)
                        verwaltung.exportAlsTxt(new FileOutputStream(file + ".txt"));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }));

            öffnenItem.addActionListener(e -> verwaltung.openFile());

            resetItem.addActionListener(e -> {
                if ((JOptionPane.showConfirmDialog(null,
                        "Möchten Sie wirklich alle Daten löschen?", "Frage", JOptionPane.YES_NO_OPTION)) == 0) {
                    verwaltung.reset();
                }


            });

            restartItem.addActionListener(e -> {
                Platform.exit();
                tray.remove(trayIconProgramm);
                tray.remove(trayIconCalls);

                Platform.runLater(() -> new MainGui().start(new Stage()));
            });

            anrufeViewItem.addActionListener(event -> {
                if (MultiWindowsHandler.getSize() == 0) {

                } else {
                    Platform.runLater(() -> {
                        MultiWindowsHandler.getOpenStage().hide();
                        MultiWindowsHandler.remove();
                    });

                }
                Platform.runLater(() -> {
                    MainGui gui = new MainGui();
                    mainGui.setShouldBeHidden(true);
                    mainGui.setName("anrufeView");
                    stage = new Stage();
                    gui.start(stage);
                });


            });

            exportAlsEmailItem.addActionListener(e -> {
                if (!Desktop.isDesktopSupported()) {
                    return;
                }
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.mail(new URI("mailto:?subject=Terminbericht" + StringEncoder.urlEncode(" vom " + Calendar.getCurrentDate()) + "&body=" + StringEncoder.urlEncode(verwaltung.toString())));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }

            });

            rückrufeListeViewItem.addActionListener(event -> {
                if (MultiWindowsHandler.getSize() == 0) {

                } else {
                    Platform.runLater(() -> {
                        MultiWindowsHandler.getOpenStage().hide();
                        MultiWindowsHandler.remove();
                    });


                }
                Platform.runLater(() -> {
                    MainGui gui = new MainGui();
                    mainGui.setShouldBeHidden(true);
                    mainGui.setName("rückrufeView");
                    stage = new Stage();
                    gui.start(stage);
                });

            });

            reasoncodeViewItem.addActionListener(event -> {
                if (MultiWindowsHandler.getSize() == 0) {

                } else {
                    Platform.runLater(() -> {
                        MultiWindowsHandler.getOpenStage().hide();
                        MultiWindowsHandler.remove();
                    });

                }

                Platform.runLater(() -> {

                    MainGui gui = new MainGui();
                    mainGui.setShouldBeHidden(true);
                    mainGui.setName("reasonCodeView");
                    gui.start(new Stage());
                });


            });

            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIconProgramm);
                tray.remove(trayIconCalls);
            });

            anrufEntfernenItem.addActionListener(e -> {
                if (verwaltung.getZahlAnrufe() > 0) {
                    if (MultiWindowsHandler.getSize() == 0) {
                        Platform.runLater(() -> {
                            MainGui gui = new MainGui();
                            mainGui = gui;
                            verwaltung.restore();
                            verwaltung.setZahlAnrufe(verwaltung.getZahlAnrufe() - 1);
                            verwaltung.sichern();
                            refreshToolTip();
                        });
                    } else {

                    }
                }

            });

            anrufHinzufügenItem.addActionListener(e -> {
                if (MultiWindowsHandler.getSize() == 0) {
                    Platform.runLater(() -> {
                        MainGui gui = new MainGui();
                        mainGui = gui;
                        verwaltung.restore();
                        verwaltung.setZahlAnrufe(verwaltung.getZahlAnrufe() + 1);
                        verwaltung.sichern();
                        refreshToolTip();
                    });
                }

            });

            coHiAGB.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&ved=2ahUKEwjBivegnJjhAhWB2qQKHU17Cj8QFjAAegQIAhAC&url=https%3A%2F%2Ftelekomhilft.telekom.de%2Friokc95758%2Fattachments%2Friokc95758%2F710%2F648623%2F1%2F43978.pdf&usg=AOvVaw1lOaYj-oVzjsyz678zfL9O"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            highspeedDetailsS.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/zuhause/tarife-und-optionen/internet/magenta-zuhause-s");
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            highspeedDetailsM.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/zuhause/tarife-und-optionen/internet/magenta-zuhause-m");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            highspeedDetailsL.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/zuhause/tarife-und-optionen/internet/magenta-zuhause-l");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            highspeedDetailsXL.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/zuhause/tarife-und-optionen/internet/magenta-zuhause-xl");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            minimalLeistungenDetailsS.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/is-bin/intershop.static/WFS/EKI-PK-Site/EKI-PK/de_DE/downloads/produktinformationsblatt/pib-pk-festnetz-magentazuhause-s.pdf");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            minimalLeistungenDetailsM.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/is-bin/intershop.static/WFS/EKI-PK-Site/EKI-PK/de_DE/downloads/produktinformationsblatt/pib-pk-festnetz-magentazuhause-m.pdf");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            minimalLeistungenDetailsL.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/is-bin/intershop.static/WFS/EKI-PK-Site/EKI-PK/de_DE/downloads/produktinformationsblatt/pib-pk-festnetz-magentazuhause-l.pdf");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            minimalLeistungenDetailsXL.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/is-bin/intershop.static/WFS/EKI-PK-Site/EKI-PK/de_DE/downloads/produktinformationsblatt/pib-pk-festnetz-magentazuhause-xl.pdf");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            highspeedAGB.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/dlp/agb/pdf/45529.pdf?");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });


            trayIconCalls.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1) {
                        if (MultiWindowsHandler.getSize() == 0) {
                            Platform.runLater(() -> {
                                verwaltung.restore();
                                verwaltung.setZahlAnrufe(verwaltung.getZahlAnrufe() + 1);
                                verwaltung.sichern();
                                refreshToolTip();
                            });
                        } else {
                            verwaltung.setZahlAnrufe(verwaltung.getZahlAnrufe() + 1);
                            verwaltung.sichern();
                            verwaltung.restore();
                            restart();
                            //((BorderPane) stage.getScene().getRoot()).getCenter().co(new anrufeView(stage, mainpane, this).getAnrufe())
                            refreshToolTip();
                        }

                    }

                }

            });

            coHiLeistungsübersichtItem.addActionListener(e -> FileWebOpener.openFileAsJPGStream("/computerhilfe/Computerhilfe_Leistungsübersicht.jpg"));

            coHiS.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.telekom.de/zuhause/tarife-und-optionen/zubuchoptionen/computerhilfe-s"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            coHiS.addActionListener(event -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.telekom.de/zuhause/tarife-und-optionen/zubuchoptionen/computerhilfe-s"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            coHiM.addActionListener(event -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.telekom.de/zuhause/tarife-und-optionen/zubuchoptionen/computerhilfe-m"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            coHiL.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.telekom.de/zuhause/tarife-und-optionen/zubuchoptionen/computerhilfe-l"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            routerFAQMenu.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.router-faq.de"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            routerFAQFritzBoxItem.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.router-faq.de/?id=fbinfo"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            routerFAQSpeedportItem.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.router-faq.de/?id=spinfo"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            highspeedLeistungen.addActionListener(e -> FileWebOpener.openFileAsJPGStream("/magenta/zuhause/MagentaZuhause_Leistungsübersicht.jpg"));

            telekomHilftItem.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.telekom.de/hilfe/festnetz-internet-tv?samChecked=true"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            winsItem.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://mywins.telekom.de/"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            });

            fritzBox7390.addActionListener(new routerFAQActionListener());
            fritzbox7490.addActionListener(new routerFAQActionListener());
            fritzbox7590.addActionListener(new routerFAQActionListener());
            fritzBox7330.addActionListener(new routerFAQActionListener());
            fritzBox7430.addActionListener(new routerFAQActionListener());
            fritzBox7530.addActionListener(new routerFAQActionListener());

            fritzbox.addActionListener(new routerFAQActionListener());
            fritzbox2030.addActionListener(new routerFAQActionListener());
            fritzbox2031.addActionListener(new routerFAQActionListener());
            fritzbox2070.addActionListener(new routerFAQActionListener());
            fritzbox2110.addActionListener(new routerFAQActionListener());
            fritzbox2170.addActionListener(new routerFAQActionListener());
            fritzbox3020.addActionListener(new routerFAQActionListener());
            fritzbox3030.addActionListener(new routerFAQActionListener());
            fritzbox3050.addActionListener(new routerFAQActionListener());
            fritzbox3070.addActionListener(new routerFAQActionListener());
            fritzbox3130.addActionListener(new routerFAQActionListener());
            fritzbox3131.addActionListener(new routerFAQActionListener());
            fritzbox3131.addActionListener(new routerFAQActionListener());
            fritzbox3170.addActionListener(new routerFAQActionListener());
            fritzbox3270.addActionListener(new routerFAQActionListener());
            fritzbox3270v3.addActionListener(new routerFAQActionListener());
            fritzbox3272.addActionListener(new routerFAQActionListener());
            fritzbox3370.addActionListener(new routerFAQActionListener());
            fritzbox3390.addActionListener(new routerFAQActionListener());
            fritzbox3490.addActionListener(new routerFAQActionListener());
            fritzbox4020.addActionListener(new routerFAQActionListener());
            fritzbox4040.addActionListener(new routerFAQActionListener());
            fritzbox5010.addActionListener(new routerFAQActionListener());
            fritzbox5012.addActionListener(new routerFAQActionListener());
            fritzbox5050.addActionListener(new routerFAQActionListener());
            fritzbox5113.addActionListener(new routerFAQActionListener());
            fritzbox5124.addActionListener(new routerFAQActionListener());
            fritzbox5140.addActionListener(new routerFAQActionListener());
            fritzbox5490.addActionListener(new routerFAQActionListener());
            fritzbox5491.addActionListener(new routerFAQActionListener());
            fritzbox6320Cable.addActionListener(new routerFAQActionListener());
            fritzbox6320V2Cable.addActionListener(new routerFAQActionListener());
            fritzbox6340Cable.addActionListener(new routerFAQActionListener());
            fritzbox6360Cable.addActionListener(new routerFAQActionListener());
            fritzbox6430Cable.addActionListener(new routerFAQActionListener());
            fritzbox6490Cable.addActionListener(new routerFAQActionListener());
            fritzbox6590Cable.addActionListener(new routerFAQActionListener());
            fritzbox6591Cable.addActionListener(new routerFAQActionListener());
            fritzbox6810LTE.addActionListener(new routerFAQActionListener());
            fritzbox6820LTE.addActionListener(new routerFAQActionListener());
            fritzbox6840LTE.addActionListener(new routerFAQActionListener());
            fritzbox6842LTE.addActionListener(new routerFAQActionListener());
            fritzbox6890LTE.addActionListener(new routerFAQActionListener());
            fritzbox7050.addActionListener(new routerFAQActionListener());
            fritzbox7112.addActionListener(new routerFAQActionListener());
            fritzbox7113.addActionListener(new routerFAQActionListener());
            fritzbox7140.addActionListener(new routerFAQActionListener());
            fritzbox7141.addActionListener(new routerFAQActionListener());
            fritzbox7170.addActionListener(new routerFAQActionListener());
            fritzbox7170SL.addActionListener(new routerFAQActionListener());
            fritzbox7170SLV2.addActionListener(new routerFAQActionListener());
            fritzbox7170V2.addActionListener(new routerFAQActionListener());
            fritzbox7240.addActionListener(new routerFAQActionListener());
            fritzbox7270V1.addActionListener(new routerFAQActionListener());
            fritzbox7270V2.addActionListener(new routerFAQActionListener());
            fritzbox7270V3.addActionListener(new routerFAQActionListener());
            fritzbox7272.addActionListener(new routerFAQActionListener());
            fritzbox7312.addActionListener(new routerFAQActionListener());
            fritzbox7320.addActionListener(new routerFAQActionListener());
            fritzbox7330SL.addActionListener(new routerFAQActionListener());
            fritzbox7340.addActionListener(new routerFAQActionListener());
            fritzbox7360V1.addActionListener(new routerFAQActionListener());
            fritzbox7360V2.addActionListener(new routerFAQActionListener());
            fritzbox7360V3.addActionListener(new routerFAQActionListener());
            fritzbox7362SL.addActionListener(new routerFAQActionListener());
            fritzbox7369.addActionListener(new routerFAQActionListener());
            fritzbox7412.addActionListener(new routerFAQActionListener());
            fritzbox7520.addActionListener(new routerFAQActionListener());
            fritzbox7560.addActionListener(new routerFAQActionListener());
            fritzbox7570VDSL.addActionListener(new routerFAQActionListener());
            fritzbox7580.addActionListener(new routerFAQActionListener());
            fritzbox7581.addActionListener(new routerFAQActionListener());
            fritzbox7582.addActionListener(new routerFAQActionListener());
            fritzbox7583.addActionListener(new routerFAQActionListener());
            fritzboxFon.addActionListener(new routerFAQActionListener());
            fritzboxFonAta.addActionListener(new routerFAQActionListener());
            fritzboxFonWLAN.addActionListener(new routerFAQActionListener());
            fritzboxSL.addActionListener(new routerFAQActionListener());
            fritzboxSLWLAN.addActionListener(new routerFAQActionListener());
            fritzboxFon7150.addActionListener(new routerFAQActionListener());

            speedport200.addActionListener(new routerFAQActionListener());
            speedport201.addActionListener(new routerFAQActionListener());
            speedport221.addActionListener(new routerFAQActionListener());
            speedport300.addActionListener(new routerFAQActionListener());
            speedport300HS.addActionListener(new routerFAQActionListener());
            speedport400P.addActionListener(new routerFAQActionListener());
            speedport500V.addActionListener(new routerFAQActionListener());
            speedportW303V.addActionListener(new routerFAQActionListener());
            speedportW303VTypA.addActionListener(new routerFAQActionListener());
            speedportW303VTypB.addActionListener(new routerFAQActionListener());
            speedportW500.addActionListener(new routerFAQActionListener());
            speedportW500V.addActionListener(new routerFAQActionListener());
            speedportW501V.addActionListener(new routerFAQActionListener());
            speedportW502V.addActionListener(new routerFAQActionListener());
            speedportW503V.addActionListener(new routerFAQActionListener());
            speedportW503VTypA.addActionListener(new routerFAQActionListener());
            speedportW503VTypC.addActionListener(new routerFAQActionListener());
            speedportW504V.addActionListener(new routerFAQActionListener());
            speedportW700V.addActionListener(new routerFAQActionListener());
            speedportW701V.addActionListener(new routerFAQActionListener());
            speedportW720V.addActionListener(new routerFAQActionListener());
            speedportW721V.addActionListener(new routerFAQActionListener());
            speedportW722VTypB.addActionListener(new routerFAQActionListener());
            speedportW722VTypA.addActionListener(new routerFAQActionListener());
            speedportW723VTypA.addActionListener(new routerFAQActionListener());
            speedportW723VTypB.addActionListener(new routerFAQActionListener());
            speedportW724VTypA.addActionListener(new routerFAQActionListener());
            speedportW724VTypB.addActionListener(new routerFAQActionListener());
            speedportW724VTypC.addActionListener(new routerFAQActionListener());
            speedportW900V.addActionListener(new routerFAQActionListener());
            speedportW920V.addActionListener(new routerFAQActionListener());
            speedportW921V.addActionListener(new routerFAQActionListener());
            speedportW922V.addActionListener(new routerFAQActionListener());
            speedportW925V.addActionListener(new routerFAQActionListener());
            speedportHybrid.addActionListener(new routerFAQActionListener());
            speedportEntry.addActionListener(new routerFAQActionListener());
            speedportEntry2.addActionListener(new routerFAQActionListener());
            speedportLTE.addActionListener(new routerFAQActionListener());
            speedportLTE2.addActionListener(new routerFAQActionListener());
            speedportSmart1.addActionListener(new routerFAQActionListener());
            speedportSmart2.addActionListener(new routerFAQActionListener());
            speedportSmart3.addActionListener(new routerFAQActionListener());
            digitalisierungsboxBasic.addActionListener(new routerFAQActionListener());
            digitalisierungsboxPremium.addActionListener(new routerFAQActionListener());
            digitalisierungsboxSmart.addActionListener(new routerFAQActionListener());
            digitalisierungsboxStandard.addActionListener(new routerFAQActionListener());
            zyxel5501.addActionListener(new routerFAQActionListener());
            zyxel6501.addActionListener(new routerFAQActionListener());

            telekomHilftEmail.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/e-mail");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            telekomHilftDSL.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/anschluss-verfuegbarkeit");
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/geraete-zubehoer/heimnetzwerk-powerline-wlan/wlan");
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/ip-basierter-anschluss");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }

            });

            telekomHilftEntertain.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/magentatv");
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/tv");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }

            });

            telekomHilftNorton.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/sicherheit");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            telekomHilftAGB.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("http://www.telekom.de/agb");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            googleSearch.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.google.com");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            googlePictures.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.google.de/imghp?hl=de&tab=wi&authuser=0");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            magentaTVLeistungsübersicht.addActionListener(e -> FileWebOpener.openFileAsJPGStream("/magenta/tv/MagentaTV_Leistungsübersicht.jpg"));

            magentaTVHDSender.addActionListener(e -> FileWebOpener.openFileAsPDFStream("/magenta/tv/MagentaTV.pdf"));


            telekomHilftTelefon.addActionListener(e -> {
                try {
                    FileWebOpener.openLink("https://www.telekom.de/hilfe/festnetz-internet-tv/telefonieren-einstellungen");
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            });

            sksWmsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://skswms-web.telekom.de");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            timeboardItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://www.timeboard.de/etime/Default.aspx");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            svtItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://www.telekom-kwl.de/teilnehmer/");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            winnersCircleItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://winners-circle.telekom.de");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            csp3VoucherItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://csp3.detemobil.net");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            onlineStatistikenItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://posfa-cc.telekom.de");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            irrpItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://itc-cc.telekom.de");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            tMapItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://t-map-internal.telekom.de/tmap/resources/apps/portal_intranet/index.html?ex=%7B%22xmax%22%3A1855666%2C%22ymax%22%3A7383652%2C%22xmin%22%3A454118%2C%22ymin%22%3A5967428%2C%22wkid%22%3A3857%7D&region=de&lang=de&initCoverageLayoutIdx=0");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            aspaItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://aspa.telekom.de/cgi-bin-mp/aspa/cgidb/aspa/index.hto");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            retourenItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://retoure.telekom-dienste.de/miete/rent/service");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            testMailItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FileWebOpener.openLink("https://accounts.login.idm.telekom.com/idmip?openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.return_to=https%3A%2F%2Ftipi.api.t-online.de%2Fsrp-auth%2FoneIdm%2Fverify%3FreturnToUrl%3Dhttp%3A%2F%2Fwww.t-online.de%2F-%2Fid_62530878%2Ftid_tsr-landingpage-popup%2Findex&openid.realm=https%3A%2F%2Ftipi.api.t-online.de&openid.assoc_handle=Sba296ffc-2ef5-4f7a-bae4-e9fb5a5da428&openid.mode=checkid_setup&openid.ns.ext1=http%3A%2F%2Fopenid.net%2Fsrv%2Fax%2F1.0&openid.ext1.mode=fetch_request&openid.ext1.type.attr1=urn%3Atelekom.com%3Aall&openid.ext1.required=attr1&openid.ns.ext2=http%3A%2F%2Fidm.telekom.com%2Fopenid%2Foauth2%2F1.0&openid.ext2.client_id=10LIVESAM30000004901PORTAL00000000000000&openid.ext2.scopes=W3sic2NvcGUiOiJzcGljYSJ9XQ%3D%3D&openid.ns.ext3=http%3A%2F%2Fidm.telekom.com%2Fopenid%2Fext%2F2.0&openid.ext3.logout_endpoint=https%3A%2F%2Ftipi.api.t-online.de%2Fsrp-auth%2FoneIdm%2Flogout&openid.ns.ext4=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fui%2F1.0&openid.ext4.mode=popup");
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }
            });


            final PopupMenu popup = new PopupMenu();
            final PopupMenu popup2 = new PopupMenu();
            popup.add(öffnenItem);
            popup.add(speichernItem);
            speichernItem.add(exportAlsEmailItem);
            speichernItem.add(exportAlsZwischenablageItem);
            speichernItem.add(exportAlsTxtItem);
            popup.addSeparator();
            popup.add(produkteItem);
            produkteItem.add(computerhilfeItem);
            produkteItem.add(highspeedItem);
            produkteItem.add(magentaTVItem);
            magentaTVItem.add(magentaTVLeistungsübersicht);
            magentaTVItem.add(magentaTVHDSender);
            computerhilfeItem.add(coHiLeistungsübersichtItem);
            computerhilfeItem.add(coHiWeitereInfosItem);
            computerhilfeItem.add(coHiAGB);
            coHiWeitereInfosItem.add(coHiS);
            coHiWeitereInfosItem.add(coHiM);
            coHiWeitereInfosItem.add(coHiL);
            highspeedItem.add(highspeedLeistungen);
            highspeedItem.add(minimalLeistungenDetails);
            minimalLeistungenDetails.add(minimalLeistungenDetailsS);
            minimalLeistungenDetails.add(minimalLeistungenDetailsM);
            minimalLeistungenDetails.add(minimalLeistungenDetailsL);
            minimalLeistungenDetails.add(minimalLeistungenDetailsXL);
            highspeedItem.add(highspeedDetails);
            highspeedDetails.add(highspeedDetailsS);
            highspeedDetails.add(highspeedDetailsM);
            highspeedDetails.add(highspeedDetailsL);
            highspeedDetails.add(highspeedDetailsXL);
            highspeedItem.add(highspeedAGB);
            popup.add(seitenMenu);
            seitenMenu.add(timeboardItem);
            seitenMenu.add(sksWmsItem);
            seitenMenu.add(irrpItem);
            seitenMenu.add(onlineStatistikenItem);
            seitenMenu.add(svtItem);
            seitenMenu.add(google);
            google.add(googleSearch);
            google.add(googlePictures);
            seitenMenu.add(routerFAQMenu);
            routerFAQMenu.add(routerFAQFritzBoxItem);
            routerFAQFritzBoxItem.add(fritzBox7330);
            routerFAQFritzBoxItem.add(fritzBox7390);
            routerFAQFritzBoxItem.add(fritzBox7430);
            routerFAQFritzBoxItem.add(fritzbox7490);
            routerFAQFritzBoxItem.add(fritzBox7530);
            routerFAQFritzBoxItem.add(fritzbox7590);
            routerFAQFritzBoxItem.add(fritzboxAndere);
            fritzboxAndere.add(fritzbox);
            fritzboxAndere.add(fritzbox2030);
            fritzboxAndere.add(fritzbox2031);
            fritzboxAndere.add(fritzbox2070);
            fritzboxAndere.add(fritzbox2110);
            fritzboxAndere.add(fritzbox2170);
            fritzboxAndere.add(fritzbox3020);
            fritzboxAndere.add(fritzbox3030);
            //TODO Add fritzbox entries to java.awt.popup (Traymenu):
            fritzboxAndere.add(fritzbox3050);
            fritzboxAndere.add(fritzbox3070);
            fritzboxAndere.add(fritzbox3130);
            fritzboxAndere.add(fritzbox3131);
            fritzboxAndere.add(fritzbox3131);
            fritzboxAndere.add(fritzbox3170);
            fritzboxAndere.add(fritzbox3270);
            fritzboxAndere.add(fritzbox3270v3);
            fritzboxAndere.add(fritzbox3272);
            fritzboxAndere.add(fritzbox3370);
            fritzboxAndere.add(fritzbox3390);
            fritzboxAndere.add(fritzbox3490);
            fritzboxAndere.add(fritzbox4020);
            fritzboxAndere.add(fritzbox4040);
            fritzboxAndere.add(fritzbox5010);
            fritzboxAndere.add(fritzbox5012);
            fritzboxAndere.add(fritzbox5050);
            fritzboxAndere.add(fritzbox5113);
            fritzboxAndere.add(fritzbox5124);
            fritzboxAndere.add(fritzbox5140);
            fritzboxAndere.add(fritzbox5490);
            fritzboxAndere.add(fritzbox5491);
            fritzboxAndere.add(fritzbox6320Cable);
            fritzboxAndere.add(fritzbox6320V2Cable);
            fritzboxAndere.add(fritzbox6340Cable);
            fritzboxAndere.add(fritzbox6360Cable);
            fritzboxAndere.add(fritzbox6430Cable);
            fritzboxAndere.add(fritzbox6490Cable);
            fritzboxAndere.add(fritzbox6590Cable);
            fritzboxAndere.add(fritzbox6591Cable);
            fritzboxAndere.add(fritzbox6810LTE);
            fritzboxAndere.add(fritzbox6820LTE);
            fritzboxAndere.add(fritzbox6840LTE);
            fritzboxAndere.add(fritzbox6842LTE);
            fritzboxAndere.add(fritzbox6890LTE);
            fritzboxAndere.add(fritzbox7050);
            fritzboxAndere.add(fritzbox7112);
            fritzboxAndere.add(fritzbox7113);
            fritzboxAndere.add(fritzbox7140);
            fritzboxAndere.add(fritzbox7141);
            fritzboxAndere.add(fritzbox7170);
            fritzboxAndere.add(fritzbox7170SL);
            fritzboxAndere.add(fritzbox7170SLV2);
            fritzboxAndere.add(fritzbox7170V2);
            fritzboxAndere.add(fritzbox7240);
            fritzboxAndere.add(fritzbox7270V1);
            fritzboxAndere.add(fritzbox7270V2);
            fritzboxAndere.add(fritzbox7270V3);
            fritzboxAndere.add(fritzbox7272);
            fritzboxAndere.add(fritzbox7312);
            fritzboxAndere.add(fritzbox7320);
            fritzboxAndere.add(fritzbox7330SL);
            fritzboxAndere.add(fritzbox7340);
            fritzboxAndere.add(fritzbox7360V1);
            fritzboxAndere.add(fritzbox7360V2);
            fritzboxAndere.add(fritzbox7360V3);
            fritzboxAndere.add(fritzbox7362SL);
            fritzboxAndere.add(fritzbox7369);
            fritzboxAndere.add(fritzbox7412);
            fritzboxAndere.add(fritzbox7520);
            fritzboxAndere.add(fritzbox7560);
            fritzboxAndere.add(fritzbox7570VDSL);
            fritzboxAndere.add(fritzbox7580);
            fritzboxAndere.add(fritzbox7581);
            fritzboxAndere.add(fritzbox7582);
            fritzboxAndere.add(fritzbox7583);
            fritzboxAndere.add(fritzboxFon);
            fritzboxAndere.add(fritzboxFonAta);
            fritzboxAndere.add(fritzboxFonWLAN);
            fritzboxAndere.add(fritzboxSL);
            fritzboxAndere.add(fritzboxSLWLAN);
            fritzboxAndere.add(fritzboxFon7150);

            routerFAQMenu.add(routerFAQSpeedportItem);
            routerFAQSpeedportItem.add(speedportW723VTypA);
            routerFAQSpeedportItem.add(speedportW723VTypB);
            routerFAQSpeedportItem.add(speedportW724VTypA);
            routerFAQSpeedportItem.add(speedportW724VTypB);
            routerFAQSpeedportItem.add(speedportW724VTypC);
            routerFAQSpeedportItem.add(speedportW900V);
            routerFAQSpeedportItem.add(speedportW920V);
            routerFAQSpeedportItem.add(speedportW921V);
            routerFAQSpeedportItem.add(speedportW922V);
            routerFAQSpeedportItem.add(speedportW925V);
            routerFAQSpeedportItem.add(speedportLTE);
            routerFAQSpeedportItem.add(speedportLTE2);
            routerFAQSpeedportItem.add(speedportHybrid);
            routerFAQSpeedportItem.add(speedportEntry);
            routerFAQSpeedportItem.add(speedportEntry2);
            routerFAQSpeedportItem.add(speedportSmart1);
            routerFAQSpeedportItem.add(speedportSmart2);
            routerFAQSpeedportItem.add(speedportSmart3);
            routerFAQSpeedportItem.add(speedportAndere);
            speedportAndere.add(speedport200);
            speedportAndere.add(speedport201);
            speedportAndere.add(speedport221);
            speedportAndere.add(speedport300);
            speedportAndere.add(speedport300HS);
            speedportAndere.add(speedport400P);
            speedportAndere.add(speedport500V);
            speedportAndere.add(speedportW303V);
            speedportAndere.add(speedportW303VTypA);
            speedportAndere.add(speedportW303VTypB);
            speedportAndere.add(speedportW500);
            speedportAndere.add(speedportW500V);
            speedportAndere.add(speedportW501V);
            speedportAndere.add(speedportW502V);
            speedportAndere.add(speedportW503V);
            speedportAndere.add(speedportW503VTypA);
            speedportAndere.add(speedportW503VTypC);
            speedportAndere.add(speedportW504V);
            speedportAndere.add(speedportW700V);
            speedportAndere.add(speedportW701V);
            speedportAndere.add(speedportW720V);
            speedportAndere.add(speedportW721V);
            speedportAndere.add(speedportW722VTypA);
            speedportAndere.add(speedportW722VTypB);
            speedportAndere.add(speedportHSPA);
            speedportAndere.add(speedportPro);
            speedportAndere.add(digitalisierungsboxBasic);
            speedportAndere.add(digitalisierungsboxSmart);
            speedportAndere.add(digitalisierungsboxStandard);
            speedportAndere.add(digitalisierungsboxPremium);
            speedportAndere.add(zyxel5501);
            speedportAndere.add(zyxel6501);

            seitenMenu.add(telekomHilftItem);
            telekomHilftItem.add(telekomHilftDSL);
            telekomHilftItem.add(telekomHilftEntertain);
            telekomHilftItem.add(telekomHilftTelefon);
            telekomHilftItem.add(telekomHilftEmail);
            telekomHilftItem.add(telekomHilftNorton);
            telekomHilftItem.add(telekomHilftAGB);
            seitenMenu.add(winsItem);
            seitenMenu.add(winnersCircleItem);
            seitenMenu.add(csp3VoucherItem);
            seitenMenu.add(testMailItem);
            seitenMenu.add(tMapItem);
            seitenMenu.add(aspaItem);
            seitenMenu.add(retourenItem);

            popup.addSeparator();
            popup.add(anrufEntfernenItem);
            popup.add(anrufHinzufügenItem);
            popup.add(rückrufeListeViewItem);
            popup.add(anrufeViewItem);
            popup.addSeparator();
            popup.add(reasoncodeViewItem);
            popup.addSeparator();
            //popup.add(openItem);
            popup.add(resetItem);
            // popup.add(restartItem);
            popup.add(exitItem);
            trayIconProgramm.setPopupMenu(popup);
            trayIconCalls.setPopupMenu(popup2);

            // add the application tray icon to the system tray.
            tray.add(trayIconProgramm);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createPane(String name) {
        //stage = new Stage();
        mainpane = new BorderPane();
        //mainpane.setTop(createMenuBar(stage));
        stage.setScene(new Scene(mainpane, 330, 150));
        switch (name) {
            case "anrufeView":
                new anrufeView(stage, mainpane);
                break;
            case "reasonCodeView":
                new ReasoncodeView(stage, mainpane, this);
                break;
            case "rückrufeView":
                new RückrufeView(stage, mainpane, this);
                break;
        }


    }

    private void createApp(boolean trayisCreated, Stage primaryStage, String name) {

        if (backup.exists()) {          //Restore changes
            try {
                if (!new ObjectInputStream(new FileInputStream(backup)).readObject().equals(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) + "." + java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + "." + java.util.Calendar.getInstance().get(java.util.Calendar.YEAR))) {
                    backup.delete();
                } else {
                    verwaltung.restore();
                }
            } catch (IOException | ClassNotFoundException ignored) {
                ignored.printStackTrace();
            }
        }

        if (name.equals("kein")) {
            createtray();
            return;
        }

        primaryStage.setResizable(false);

        Platform.setImplicitExit(false);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        if (shouldBeHidden) {
            primaryStage.initStyle(StageStyle.UTILITY);
            primaryStage.setOpacity(0);
            primaryStage.setHeight(0);
            primaryStage.setWidth(0);
            primaryStage.show();
        }

        Stage mainStage = new Stage();
        stage = mainStage;
        mainStage.setResizable(false);
        MultiWindowsHandler.add();
        MultiWindowsHandler.setStage(mainStage);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/ProgrammIcon.png")));
        stage.setTitle("BreakTool v.1.4.1");


        if (System.getProperty("os.name").startsWith("Windows")) {
            stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 350);

        } else {
            stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 340);

        }
        stage.setY(primaryScreenBounds.getMinX() + 10);
        mainStage.initOwner(primaryStage);
        mainStage.initStyle(StageStyle.DECORATED);
        createPane(name);
        mainStage.setAlwaysOnTop(true);
        mainStage.show();
        mainStage.setOnCloseRequest(new CloseEventhandler());
        if (!trayisCreated) {
            SwingUtilities.invokeLater(this::createtray);


        }


    }

    public boolean isShouldBeHidden() {
        return shouldBeHidden;
    }

    public void setShouldBeHidden(boolean shouldBeHidden) {
        this.shouldBeHidden = shouldBeHidden;
    }

    public SystemTray getTray() {
        return tray;
    }

    public void setTray(SystemTray tray) {
        MainGui.tray = tray;
    }

    public TrayIcon getTrayIcon() {
        return trayIconProgramm;
    }

    public void setTrayIcon(TrayIcon trayIcon) {
        trayIconProgramm = trayIcon;
    }

    public BorderPane getMainpane() {
        return mainpane;
    }

    public void setMainpane(BorderPane mainpane) {
        this.mainpane = mainpane;
    }

    public File getBackupFolder() {
        return backupFolder;
    }

    public void setBackupFolder(File backupFolder) {
        this.backupFolder = backupFolder;
    }

    public File getBackup() {
        return backup;
    }

    public MainGui getMainGui() {
        return mainGui;
    }

    Stage getStage() {
        return stage;
    }

    private void restart() {
        Platform.runLater(() -> {
            MultiWindowsHandler.getOpenStage().close();
            MultiWindowsHandler.remove();


        });
    }

    private class CloseEventhandler implements EventHandler {

        @Override
        public void handle(Event event) {
            event.consume();
            MultiWindowsHandler.remove();
            stage.hide();

        }

    }

    private class routerFAQActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuItem menu = (MenuItem) e.getSource();
            String name = menu.getLabel();
            if (name.contains("Speedport") || name.contains("Zyxel") || name.contains("Digitalisierungsbox")) {
                try {
                    FileWebOpener.openLink(FileWebOpener.routerFAQCase(name, "Telekom"));
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            } else if (name.contains("FRITZ!Box")) {
                try {
                    FileWebOpener.openLink(FileWebOpener.routerFAQCase(name, "AVM"));
                } catch (URISyntaxException | IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }


}

