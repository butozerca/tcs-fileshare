package visual;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import network.ServerAddress;

/**
 * A frame which allows user to connect to a network using an adress of a known other host.
 * @author michal2
 *
 */

public class JoinNetworkBox extends JFrame {

	private static final long serialVersionUID = -5724407693366871196L;
	private static JoinNetworkBox instance = null;
	private JPanel contentPane;
	private JTextField adressField;
	private JTextField portFieldSearch;
	private JTextField portFieldFile;
	private JTextField portFieldAdd;


	private JoinNetworkBox() {
		setTitle("Join a network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 161);
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
		
		adressField = new JTextField();
		adressField.setToolTipText("Enter hosts' ip adress");
		adressField.setText("adres ip hosta");
		panel.add(adressField);
		adressField.setColumns(12);
		
		portFieldSearch = new JTextField();
		portFieldSearch.setToolTipText("Enter hosts' search port");
		portFieldSearch.setText("port share");
		panel.add(portFieldSearch);
		portFieldSearch.setColumns(7);
		
		portFieldFile = new JTextField();
		portFieldFile.setToolTipText("Enter hosts' file port");
		portFieldFile.setText("port file");
		panel.add(portFieldFile);
		portFieldFile.setColumns(7);
		
		portFieldAdd = new JTextField();
		portFieldAdd.setToolTipText("Enter hosts' add port");
		portFieldAdd.setText("port add");
		panel.add(portFieldAdd);
		portFieldAdd.setColumns(7);
		
		JButton btnOK = new JButton("OK");
		panel.add(btnOK);
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(verifyAdress(adressField.getText(), portFieldSearch.getText(), portFieldFile.getText())){
					new ServerAddress(adressField.getText(), 
							Integer.parseInt(portFieldSearch.getText()),
							Integer.parseInt(portFieldFile.getText()),
							Integer.parseInt(portFieldAdd.getText()));
				}
				dispose();
			}
		});
		this.pack();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private boolean verifyAdress(String adresip, String portsearch, String portfile){
		if(Pattern.compile("[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]").matcher(adresip).matches() &&
				Pattern.compile("[0-9]+").matcher(portsearch).matches() &&
				Pattern.compile("[0-9]+").matcher(portfile).matches())
			return true;
		else return false;
	}
	
	@SuppressWarnings("unused")
	private ServerAddress convert(String adresip, String portsearch, String portfile, String portadd){
		return new ServerAddress(adresip, Integer.getInteger(portsearch), Integer.getInteger(portfile), Integer.getInteger(portadd));
	}
	/**
	 * Returns the current instance of this frame. New one if none exists.
	 * @return
	 */
	public static JoinNetworkBox getInstance(){
		if(instance == null)
			instance = new JoinNetworkBox();
		return instance;
	}
	
}
