package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Show;
import model.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class AdminView {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminView window = new AdminView();
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
	public AdminView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 470, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblShow = new JLabel("Show");
		lblShow.setBounds(59, 21, 46, 14);
		frame.getContentPane().add(lblShow);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(10, 46, 46, 14);
		frame.getContentPane().add(lblId);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 71, 46, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblNewLabel = new JLabel("Description");
		lblNewLabel.setBounds(10, 101, 72, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(10, 126, 46, 14);
		frame.getContentPane().add(lblDate);
		
		JLabel lblRating = new JLabel("Rating");
		lblRating.setBounds(10, 151, 46, 14);
		frame.getContentPane().add(lblRating);
		
		textField = new JTextField();
		textField.setBounds(92, 43, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(92, 68, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(92, 98, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(92, 123, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(92, 148, 86, 20);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnAdd = new JButton("Create");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textField_1.getText();
				String description = textField_2.getText();
				String date =textField_3.getText();
				float rating =Float.parseFloat(textField_4.getText());
				String type = textField_10.getText();
				if(type.equals("movie") || type.equals("theatre") || type.equals("sport event")) {
					JSONObject reqObj= new JSONObject();
					
					reqObj = obj2(name,description,date,rating,type);
					 String reqString= reqObj.toString();
					    String APIUrl=  "http://localhost:8080/crudShow/post/save";
					    String response=  Utility.excutePost(APIUrl, reqString);
					    System.out.println(response);
				}
				else
					JOptionPane.showMessageDialog(null, "Type invalid");
			}
			
		});
		btnAdd.setBounds(10, 227, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String APIUrl = "http://localhost:8080/crudShow/get/read";
				String response = null;
				try {
					response = UtilityGet.getHTML(APIUrl);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<Show>>(){}.getType();
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) gson.fromJson(response, listType);
				Reflaction r=new Reflaction();
				JTable t=new JTable();
				t=r.createTable(list);
				JFrame frame = new JFrame("Shows");
			    frame.setSize(500,120);
			    frame.setLocationRelativeTo(null);
			    JPanel panel = new JPanel();
			    JScrollPane jsp = new JScrollPane(t);
			    panel.setLayout(new BorderLayout());
			    panel.add(jsp,BorderLayout.CENTER);
			    frame.setContentPane(panel);
			    frame.setVisible(true);
			}
		});
		btnRead.setBounds(121, 227, 89, 23);
		frame.getContentPane().add(btnRead);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(textField.getText());
				String date =textField_3.getText();
				float rating =Float.parseFloat(textField_4.getText());
				String type = textField_10.getText();
				if(id<0)
					JOptionPane.showMessageDialog(null, "Id invalid");
				else
					if (!(type.equals("movie") || type.equals("theatre") || type.equals("sport event")))
						JOptionPane.showMessageDialog(null, "Type invalid");
					else {
						JSONObject reqObj= new JSONObject();
						reqObj = obj3(id,date,rating,type);
						 String reqString= reqObj.toString();
						 String APIUrl=  "http://localhost:8080/show/post/edit";
						 String response=  Utility.excutePost(APIUrl, reqString);
						 System.out.println(response);
					}
				
			}
		});
		btnUpdate.setBounds(10, 274, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(textField.getText());
				if(id<0)
					JOptionPane.showMessageDialog(null, "Id invalid");
				else {
					JSONObject reqObj= new JSONObject();
					reqObj = obj(id);
					 String reqString= reqObj.toString();
					 String APIUrl=  "http://localhost:8080/crudShow/post/delete";
					 String response=  Utility.excutePost(APIUrl, reqString);
					 System.out.println(response);
				}
				
			}
		});
		btnDelete.setBounds(121, 274, 89, 23);
		frame.getContentPane().add(btnDelete);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(307, 21, 46, 14);
		frame.getContentPane().add(lblUser);
		
		JLabel lblId_1 = new JLabel("Id");
		lblId_1.setBounds(242, 46, 46, 14);
		frame.getContentPane().add(lblId_1);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(242, 71, 61, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(242, 101, 61, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(242, 126, 46, 14);
		frame.getContentPane().add(lblName_1);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(242, 151, 61, 14);
		frame.getContentPane().add(lblAddress);
		
		textField_5 = new JTextField();
		textField_5.setBounds(317, 43, 86, 20);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(317, 68, 86, 20);
		frame.getContentPane().add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(317, 98, 86, 20);
		frame.getContentPane().add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(317, 123, 86, 20);
		frame.getContentPane().add(textField_8);
		textField_8.setColumns(10);
		
		textField_9 = new JTextField();
		textField_9.setBounds(317, 148, 86, 20);
		frame.getContentPane().add(textField_9);
		textField_9.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(10, 184, 46, 14);
		frame.getContentPane().add(lblType);
		
		textField_10 = new JTextField();
		textField_10.setBounds(92, 179, 86, 20);
		frame.getContentPane().add(textField_10);
		textField_10.setColumns(10);
		
		JLabel lblPremium = new JLabel("Premium");
		lblPremium.setBounds(242, 184, 61, 14);
		frame.getContentPane().add(lblPremium);
		
		textField_11 = new JTextField();
		textField_11.setBounds(317, 179, 86, 20);
		frame.getContentPane().add(textField_11);
		textField_11.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = textField_6.getText();
				String password = textField_7.getText();
				String name =textField_8.getText();
				String address =textField_9.getText();
				boolean b = Boolean.parseBoolean(textField_11.getText());
				JSONObject reqObj= new JSONObject();
				
				reqObj = prepareReqJsonObj(username,password,name,address,b);
				 String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/crud/post/save";
				    String response=  Utility.excutePost(APIUrl, reqString);
				    System.out.println(response);
				    
			}
		});
		btnCreate.setBounds(242, 227, 89, 23);
		frame.getContentPane().add(btnCreate);
		
		JButton btnRead_1 = new JButton("Read");
		btnRead_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String APIUrl = "http://localhost:8080/crud/get/read";
				String response = null;
				try {
					response = UtilityGet.getHTML(APIUrl);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<User>>(){}.getType();
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) gson.fromJson(response, listType);
				Reflaction r=new Reflaction();
				JTable t=new JTable();
				t=r.createTable(list);
				JFrame frame = new JFrame("Update");
			    frame.setSize(500,120);
			    frame.setLocationRelativeTo(null);
			    JPanel panel = new JPanel();
			    JScrollPane jsp = new JScrollPane(t);
			    panel.setLayout(new BorderLayout());
			    panel.add(jsp,BorderLayout.CENTER);
			    frame.setContentPane(panel);
			    frame.setVisible(true);
			}
		});
		btnRead_1.setBounds(356, 227, 89, 23);
		frame.getContentPane().add(btnRead_1);
		
		JButton btnUpdate_1 = new JButton("Update");
		btnUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(textField_5.getText());
				String username = textField_6.getText();
				String password = textField_7.getText();
				if(id<0)
					JOptionPane.showMessageDialog(null, "Id invalid");
				else {
					JSONObject reqObj= new JSONObject();
					reqObj = obj1(id,username,password);
					 String reqString= reqObj.toString();
					 String APIUrl=  "http://localhost:8080/user/post/edit";
					 String response=  Utility.excutePost(APIUrl, reqString);
					 System.out.println(response);
				}
				
			}
		});
		btnUpdate_1.setBounds(242, 274, 89, 23);
		frame.getContentPane().add(btnUpdate_1);
		
		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(textField_5.getText());
				if(id<0)
					JOptionPane.showMessageDialog(null, "Id invalid");
				else {
					JSONObject reqObj= new JSONObject();
					reqObj = obj(id);
					String reqString= reqObj.toString();
					String APIUrl=  "http://localhost:8080/crud/post/delete";
					String response=  Utility.excutePost(APIUrl, reqString);
					System.out.println(response);
				}
				
			}
		});
		btnDelete_1.setBounds(356, 274, 89, 23);
		frame.getContentPane().add(btnDelete_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Login r= new Login();
		        r.frame.setVisible(true);
			}
		});
		btnBack.setBounds(373, 11, 72, 14);
		frame.getContentPane().add(btnBack);
		
		
	}
	@SuppressWarnings("unchecked")
	public JSONObject prepareReqJsonObj(String s1, String s2, String s3, String s4, boolean p){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("username", s1);
		jsonobj.put("password", s2);
		jsonobj.put("name", s3);
		jsonobj.put("address", s4);
		jsonobj.put("premium", p);
		
		return jsonobj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj2(String s1, String s2, String s3, float s4, String s5){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("name", s1);
		jsonobj.put("description", s2);
		jsonobj.put("date", s3);
		jsonobj.put("rating", s4);
		jsonobj.put("type", s5);
		
		return jsonobj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject obj(int i){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("id", i);
	
		
		return jsonobj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj1(int i, String u,String p){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("id", i);
		jsonobj.put("username", u);
		jsonobj.put("password", p);
		
		return jsonobj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj3(int i, String d,float r,String t){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("id", i);
		jsonobj.put("date", d);
		jsonobj.put("rating", r);
		jsonobj.put("type", t);
		
		return jsonobj;
	}
}
