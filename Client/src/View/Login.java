package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.User;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.awt.event.ActionEvent;

public class Login {

	JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	public static int idUser;
	public static String nameUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("Username");
		lblLogin.setBounds(43, 52, 87, 14);
		frame.getContentPane().add(lblLogin);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(43, 111, 87, 14);
		frame.getContentPane().add(lblPassword);

		textField = new JTextField();
		textField.setBounds(145, 49, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(145, 108, 86, 20);
		frame.getContentPane().add(passwordField);

		JRadioButton rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setBounds(88, 173, 109, 23);
		frame.getContentPane().add(rdbtnAdmin);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String name = textField.getText();
				String password = String.valueOf(passwordField.getPassword());
				if(rdbtnAdmin.isSelected()) {
					JSONObject reqObj= new JSONObject();
					
					reqObj = prepareReqJsonObj(name,password);
					
				    String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/admin/post/loginAdmin";
				    String response=  Utility.excutePost(APIUrl, reqString);
				    System.out.println(response);
				    System.out.println(response.length());
				    
				   if(Integer.parseInt(response)!=-1) {
				    	frame.setVisible(false);
						AdminView r= new AdminView();
				        r.frame.setVisible(true);
				   }
				   else
					   JOptionPane.showMessageDialog(null, "Username or password incorect");
				    
				}
				else {
					JSONObject reqObj= new JSONObject();
					
					reqObj = prepareReqJsonObj(name,password);
					
				    String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/user/post/login";

				   String response=  Utility.excutePost(APIUrl, reqString);
				   Gson gson = new Gson();
				   Type listType = new TypeToken<User>(){}.getType();
				   User list = (User) gson.fromJson(response, listType);
				   
				   if(list!=null) {
					   idUser = list.getIdUser();
					   nameUser=list.getName();
					   if(list.isPremium()==false) {
						    frame.setVisible(false);
							UserView r= new UserView();
					        r.frame.setVisible(true);
					   }
					   else {
						   frame.setVisible(false);
						   PremiumView r= new PremiumView();
					       r.frame.setVisible(true);
					   }
				   }
				   else
					   JOptionPane.showMessageDialog(null, "Username or password incorect");
				   

				   System.out.println(reqObj);
				   System.out.println(reqString);
				   System.out.println(response);

				}
				
			}
		});
		btnLogin.setBounds(287, 92, 89, 23);
		frame.getContentPane().add(btnLogin);
	}

	
	@SuppressWarnings("unchecked")
	public JSONObject prepareReqJsonObj(String s1, String s2){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("username", s1);
		jsonobj.put("password", s2);
		
		return jsonobj;
	}
}
