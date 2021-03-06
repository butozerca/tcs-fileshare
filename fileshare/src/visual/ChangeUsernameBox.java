package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import model.Fileshare;
/**
 * Frame allowing a user to change his username.
 * @author michal2
 *
 */
public class ChangeUsernameBox extends JFrame {

	private static final long serialVersionUID = -5724407693366871196L;
	private static ChangeUsernameBox instance = null;
	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	private ChangeUsernameBox(final JTextPane textPane) {
		setTitle("Change your username");
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
				if(validateName(textField.getText())){
					Fileshare.getInstance().getUser().setUsername(textField.getText());
					System.out.println("Username changed to " + textField.getText());
					textPane.setText(Fileshare.getInstance().getUser().getUsername());
				}
				dispose();
			}
		});
		this.pack();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	/**
	 * Returns the current instance of this frame. New one if none exists.
	 * @param textPane
	 * @return
	 */
	public static ChangeUsernameBox getInstance(JTextPane textPane){
		if(instance == null)
			instance = new ChangeUsernameBox(textPane);
		return instance;
	}
	private boolean validateName(String text){
		if(Pattern.compile("[a-zA-Z0-9]+").matcher(text).matches())
			return true;
		return false;
	}
	
}
