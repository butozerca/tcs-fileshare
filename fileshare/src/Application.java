import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;


public class Application extends JFrame {

	private static final long serialVersionUID = -6130863801226700401L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Application() {
		setTitle("Fileshare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JTextPane textPane = new JTextPane();
		textPane.setToolTipText("Your username");
		textPane.setText("No Username");
		textPane.setEditable(false);
		textPane.setBackground(Color.WHITE);
		panel.add(textPane);
		
		JButton setusernamebtn = new JButton("change");
		setusernamebtn.setToolTipText("Click to change your username");
		panel.add(setusernamebtn);
		setusernamebtn.addActionListener(new ChangeUsernameHandler());
		
	}

}
