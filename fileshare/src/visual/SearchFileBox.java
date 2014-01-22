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

import network.FileSearchClient;
import network.FileSearchQuery;
import network.NetworkManager;
import model.Fileshare;


public class SearchFileBox extends JFrame {

	private static final long serialVersionUID = -5724407693366871196L;
	private static SearchFileBox instance = null;
	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Create the frame.
	 */
	private SearchFileBox() {
		setTitle("Search for a file");
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
				String filename = textField.getText();
				NetworkManager sender = Fileshare.getInstance().getUser().getManager();
				FileSearchQuery query = new FileSearchQuery(sender.getAdress(), filename, 3);
				FileSearchClient cl = new FileSearchClient(sender.getNeighbours(), query);
				System.out.println(query);
				System.out.println(cl.getContent());
				//TODO zrobic okienko z wyswietlonymi wynikami zapytania
				FilePickerWindow.getInstance(cl.getContent()).setVisible(true);
				dispose();
			}
		});
		this.pack();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public static SearchFileBox getInstance(){
		if(instance == null)
			instance = new SearchFileBox();
		return instance;
	}
	
}
