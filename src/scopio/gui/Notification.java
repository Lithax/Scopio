package scopio.gui;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.Toolkit;
import java.awt.AWTException;

public class Notification {
    public static void notify(String caption, String content, MessageType messageType) {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(Notification.class.getResource("../resources/img/scopio_logo_small.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Scopio");
        trayIcon.setImageAutoSize(true);
        trayIcon.setImage(image);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }

        trayIcon.displayMessage(caption, content, messageType);
    }
}
