package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Show;
import model.ShowToSee;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class PremiumView {

	JFrame frame;
	private JTextField textField;
	public static Show s;
	private JTextField textField_1;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PremiumView window = new PremiumView();
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
	public PremiumView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 335, 245);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(42, 40, 86, 20);
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
					ShowP r= new ShowP();
			        r.frame.setVisible(true);
			        System.out.println(s.getName());
				}
				else
					JOptionPane.showMessageDialog(null, "There is no show");
			}
		});
		btnSearch.setBounds(178, 39, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		textField_1 = new JTextField();
		textField_1.setBounds(42, 108, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAddShow = new JButton("Add Show");
		btnAddShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textField_1.getText();
				JSONObject reqObj= new JSONObject();
				System.out.println(Login.nameUser);
				reqObj = obj3(name,Login.nameUser);
				 String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/showToSee/post/save";
				    String response=  Utility.excutePost(APIUrl, reqString);
				    System.out.println(response);
			}
		});
		btnAddShow.setBounds(178, 107, 89, 23);
		frame.getContentPane().add(btnAddShow);
		
		JButton btnViewShows = new JButton("View Shows");
		btnViewShows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String APIUrl = "http://localhost:8080/showToSee/post/find";
				String response=  Utility.excutePost(APIUrl, Login.nameUser);
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<ShowToSee>>(){}.getType();
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
		btnViewShows.setBounds(82, 160, 134, 23);
		frame.getContentPane().add(btnViewShows);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Login r= new Login();
		        r.frame.setVisible(true);
			}
		});
		btnBack.setBounds(220, 11, 68, 13);
		frame.getContentPane().add(btnBack);
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj3(String show,String user){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("showName", show);
		jsonobj.put("userName", user);
				
		return jsonobj;
	}
}
