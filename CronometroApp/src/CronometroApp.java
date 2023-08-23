import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CronometroApp extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JLabel tiempoLabel;
    private JButton iniciarButton;
    private JButton detenerButton;

    private boolean enEjecucion = false;
    private long startTime = 0;

    public CronometroApp() {
        setTitle("Cronómetro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        tiempoLabel = new JLabel("00:00:00");
        tiempoLabel.setFont(new Font("Helvetica", Font.PLAIN, 36));
        add(tiempoLabel);

        iniciarButton = new JButton("Iniciar");
        detenerButton = new JButton("Detener");

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!enEjecucion) {
                    startTime = System.currentTimeMillis();
                    enEjecucion = true;
                    iniciarButton.setEnabled(false);
                    detenerButton.setEnabled(true);
                    actualizarCronometro();
                }
            }
        });

        detenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enEjecucion = false;
                iniciarButton.setEnabled(true);
                detenerButton.setEnabled(false);
            }
        });

        detenerButton.setEnabled(false);

        add(iniciarButton);
        add(detenerButton);
    }

    private void actualizarCronometro() {
        if (enEjecucion) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            tiempoLabel.setText(df.format(new Date(elapsedTime)));
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    actualizarCronometro();
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CronometroApp().setVisible(true);
            }
        });
    }
}
