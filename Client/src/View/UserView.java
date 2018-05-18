package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Show;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.awt.event.ActionEvent;

public class UserView {

	JFrame frame;
	private JTextField textField;
	public static Show s;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView window = new UserView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 301, 128);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(20, 33, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textField.getText();
				String APIUrl=  "http://localhost:8080/show/post/findName";
			    String response=  Utility.excutePost(APIUrl, name);
			    //System.out.println(response);
			    Gson gson = new Gson();
				Type listType = new TypeToken<Show>(){}.getType();
				s = (Show) gson.fromJson(response, listType);
				
				if(s!=null) {
					frame.setVisible(false);
					ShowView r= new ShowView();
			        r.frame.setVisible(true);
			        System.out.println(s.getName());
				}
				else
					JOptionPane.showMessageDialog(null, "There is no show");
			    
			}
		});
		btnSearch.setBounds(155, 32, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Login r= new Login();
		        r.frame.setVisible(true);
			}
		});
		btnBack.setBounds(205, 8, 70, 13);
		frame.getContentPane().add(btnBack);
	}
}
