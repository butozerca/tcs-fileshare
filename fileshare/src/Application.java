import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;


public class Application extends JFrame {

	private static final long serialVersionUID = -6130863801226700401L;
	private JPanel contentPane;
	private JTextField localPathText;
	

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
		
		final JTextPane textPane = new JTextPane();
		textPane.setToolTipText("Your username");
		textPane.setText(Fileshare.getInstance().getUser().getUsername());
		textPane.setEditable(false);
		textPane.setBackground(Color.WHITE);
		
		JButton setusernamebtn = new JButton("change");
		setusernamebtn.setToolTipText("Click to change your username");
		
		localPathText = new JTextField();
		localPathText.setEditable(false);
		localPathText.setToolTipText("Path to your shared folder");
		localPathText.setText(Fileshare.getInstance().getUser().getPath());
		localPathText.setColumns(20);
		
		JButton changelocalpathbtn = new JButton("change");
		changelocalpathbtn.setToolTipText("Click to change your local path");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(localPathText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(181)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(changelocalpathbtn)
						.addComponent(setusernamebtn))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(9))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(setusernamebtn)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(localPathText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(changelocalpathbtn))
					.addContainerGap(187, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setusernamebtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeUsername.getInstance(textPane).setVisible(true);
			}
			
		});
		
		this.pack();
	}
}
