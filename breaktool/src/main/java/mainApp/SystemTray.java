package mainApp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

@SuppressWarnings("ALL")
public class SystemTray {
    private static java.awt.SystemTray systemTray;
    private static LinkedList<TrayIcon> trayIcons;

    public SystemTray(){
        if(systemTray==null)
            systemTray = getInstance();

        if(trayIcons==null)
            trayIcons = new LinkedList<>();
    }

    public static java.awt.SystemTray getInstance(){
        return java.awt.SystemTray.getSystemTray();
    }

    public void addImageAsTrayIcon(String path) throws AWTException, IOException {
        Image image = ImageIO.read(getClass().getResourceAsStream(path));
        TrayIcon trayIcon = new TrayIcon(image);
        systemTray.add(trayIcon);
        trayIcons.add(trayIcon);

    }

    public void addMenutoTrayIcon(int positionInList){
        TrayIcon trayIcon = trayIcons.get(positionInList);
    }


}
