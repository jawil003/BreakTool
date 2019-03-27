package mainApp.toolClasses;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWebOpener {

    public static void openLink(String link) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(link));
    }

    public static void openFileAsJPGStream(String path) {
        InputStream imageStream = FileWebOpener.class.getClass().getResourceAsStream(path);
        Path p = null;
        try {
            p = Files.createTempFile("tempIMG", ".jpg");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        openFile(p, imageStream);
    }

    public static void openFileAsPDFStream(String path) {
        InputStream imageStream = FileWebOpener.class.getClass().getResourceAsStream(path);
        Path p = null;
        try {
            p = Files.createTempFile("tempPDF", ".pdf");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        openFile(p, imageStream);

    }


    private static void openFile(Path p, InputStream stream) {
        try (FileOutputStream out = new FileOutputStream(p.toFile())) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception i) {
            i.printStackTrace();
        }
        try {
            Desktop.getDesktop().open(p.toFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public static String routerFAQCase(String value, String kind) {
        String routerModel = "";
        char c = ' ';
        if (kind.equals("Telekom")) {
            kind = "sp";
            c = 's';
            switch (value) {
                case "Speedport LTE II":
                    routerModel = "speedportlte2";
                    break;
                case "Digitalisierungsbox Standard":
                    routerModel = "digitalboxstandard";
                    break;
                case "Digitalisierungsbox Basic":
                    routerModel = "digitalboxbasic";
                    break;
                case "Digitalisierungsbox Premium":
                    routerModel = "digitalboxpremium";
                    break;
                case "Digitalisierungsbox Smart":
                    routerModel = "digitalboxsmart";
                    break;
                case "Zyxel 5501":
                    routerModel = "zyxelsl5501";
                    break;
                case "Zyxel 6501":
                    routerModel = "zyxelsl6501";
                    break;
                default:
                    routerModel = value.replaceAll("\\s+", "").toLowerCase();
                    break;
            }
        } else if (kind.equals("AVM")) {
            kind = "fb";
            c = 'f';
            switch (value) {
                case "FRITZ!Box":
                    routerModel="fbfb";
                    break;
                case "FRITZ!Box 3020":
                    routerModel="fbwlan3020";
                    break;
                case "FRITZ!Box 3030":
                    routerModel="fbwlan3030";
                    break;
                case "FRITZ!Box 7330":
                    routerModel = "fbfonwlan7330";
                case "FRITZ!Box 7390":
                    routerModel = "fbfonwlan7390";
                    break;
                default:
                    routerModel = value.replaceAll("\\s+", "").toLowerCase().replace("fritz!box", "fb");
                    break;
            }

        }
        return "https://www.router-faq.de/?id=" + kind + "info&hw" + c + "=" + routerModel + "#" + routerModel;
    }

}
