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

import model.AddressBlock;
import model.Fileshare;
import model.NetworkManager;
import model.ServerAddress;

public class SetIPBox extends JFrame {

	private static final long serialVersionUID = -5724407693366871196L;
	private static SetIPBox instance = null;
	private JPanel contentPane;
	private JTextField adressField;
	private JTextField portFieldAdd;
	private JTextField portFieldSearch;
	private JTextField portFieldFile;


	private SetIPBox() {
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
		adressField.setText("adres ip");
		panel.add(adressField);
		adressField.setColumns(12);
		
		portFieldSearch = new JTextField();
		portFieldSearch.setText("port search");
		panel.add(portFieldSearch);
		portFieldSearch.setColumns(10);
		
		portFieldFile = new JTextField();
		portFieldFile.setText("port file");
		panel.add(portFieldFile);
		portFieldFile.setColumns(10);
		
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
				String ip = adressField.getText();
				int searchPort = Integer.parseInt(portFieldSearch.getText());
				int filePort = Integer.parseInt(portFieldFile.getText());
				int addPort = Integer.parseInt(portFieldAdd.getText());
				NetworkManager manager = Fileshare.getInstance().getUser().getManager();
				manager.stopServers();
				manager.setMyAddress(new ServerAddress(ip, searchPort, filePort, addPort));
				manager.setMyBlock(new AddressBlock(1));
				manager.getMyBlock().add(manager.getMyAddress());
				manager.setChildBlock(new AddressBlock[] {null, null});
				manager.setParentBlock(null);
				manager.startServers();
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
	public static SetIPBox getInstance(){
		if(instance == null)
			instance = new SetIPBox();
		return instance;
	}
	
}
