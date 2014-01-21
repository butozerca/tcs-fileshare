package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextPane;

public class FilePickerWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4347183349817954684L;
	private JPanel contentPane;
	private static FilePickerWindow instance;
	private ArrayList<JCheckBox> lista;

	/**
	 * Create the frame.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<String> al = new ArrayList<String>();
					al.add("aaaa");
					al.add("bbbb");
					FilePickerWindow frame = new FilePickerWindow(al);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private FilePickerWindow(ArrayList<String> fileAdresses) {
		setTitle("Pick files you wish to download");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton = new JButton("Cancel");
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener (){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JButton btnNewButton_1 = new JButton("OK");
		
		JTextPane txtpnFilesFound = new JTextPane();
		txtpnFilesFound.setBackground(Color.WHITE);
		txtpnFilesFound.setEditable(false);
		txtpnFilesFound.setText("Files found:");
		
		
		
		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);
		verticalBox.add(txtpnFilesFound);
		
		if(fileAdresses != null){
			lista = new ArrayList<>();
			for(String s : fileAdresses){
				JCheckBox jcb = new JCheckBox();
				jcb.setText(s);
				lista.add(jcb);
				verticalBox.add(jcb);
			}
			
			btnNewButton_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < lista.size(); ++i){
						if(lista.get(i).isSelected()){
							System.out.println("pobieranie: " + lista.get(i).getText());
							//TODO: odpal pobieranie
						}
					}
					dispose();
				}
			});
			
		}
		else
			btnNewButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		panel.add(btnNewButton_1);
		this.pack();
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public static FilePickerWindow getInstance(ArrayList<String> fileAdresses){
		if(instance == null)
			instance = new FilePickerWindow(fileAdresses);
		return instance;
	}

}
