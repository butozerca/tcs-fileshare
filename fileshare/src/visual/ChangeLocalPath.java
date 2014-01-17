package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import model.Fileshare;

public class ChangeLocalPath extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5724407693366871196L;
	private static ChangeLocalPath instance = null;
	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	private ChangeLocalPath(final JTextPane textPane) {
		setTitle("Change your local path");
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
				if(validatePath(textField.getText())){
					Fileshare.getInstance().getUser().setPath(textField.getText());
					System.out.println("Local path changed to " + textField.getText());
					textPane.setText(Fileshare.getInstance().getUser().getPath());
				}
				dispose();
			}
		});
		this.pack();
		this.setAlwaysOnTop(true);
	}
	
	public static ChangeLocalPath getInstance(JTextPane textPane){
		if(instance == null)
			instance = new ChangeLocalPath(textPane);
		return instance;
	}
	
	private boolean validatePath(String text){
		if(Pattern.compile("([a-zA-Z0-9]+/)+").matcher(text).matches())
			return true;
		return false;
	}
	
}
