package visual;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;


public class JoinNetworkBox extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5724407693366871196L;
	private static JoinNetworkBox instance = null;
	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	private JoinNetworkBox() {
		setTitle("Join a network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		
		JButton btnOK = new JButton("OK");
		panel.add(btnOK);
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: jakos podlaczyc to do sieci
				dispose();
			}
		});
		this.pack();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public static JoinNetworkBox getInstance(){
		if(instance == null)
			instance = new JoinNetworkBox();
		return instance;
	}
	
}