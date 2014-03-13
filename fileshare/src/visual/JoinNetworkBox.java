package visual;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import model.Fileshare;
import model.ServerAddress;
import network.AddNodeClient;
import queries.Phase0Query;

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
				String host = adressField.getText();
				int port = Integer.parseInt(portFieldAdd.getText());
				try {
					Phase0Query query = new Phase0Query(Fileshare.getInstance().getUser().getManager().getMyAddress());
					AddNodeClient client = new AddNodeClient(query, host, port);
					client.setConnectionTimeout(5000);
					client.sendQuery();
				} catch(Exception e1) {
					e1.printStackTrace();
					System.out.println("failed");
				}
				dispose();
			}
		});
		this.pack();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
