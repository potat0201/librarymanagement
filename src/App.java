import javax.swing.SwingUtilities;
import library.gui.LoginGUI;

public class App {
    public static void main(String[] args) {
        // Khởi chạy GUI trên EDT
        SwingUtilities.invokeLater(() -> {
            LoginGUI frame = new LoginGUI();
            frame.setVisible(true);
        });
    }
}
