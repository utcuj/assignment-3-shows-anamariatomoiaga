package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Comment;
import model.History;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ShowP {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private String r=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowP window = new ShowP();
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
	public ShowP() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 362, 283);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(PremiumView.s.getName());
		
		JButton btnSelectShow = new JButton("Select Show");
		btnSelectShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JSONObject reqObj= new JSONObject();
				System.out.println(Login.nameUser);
				reqObj = obj3(PremiumView.s.getName(),Login.nameUser);
				 String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/history/post/save";
				    String response=  Utility.excutePost(APIUrl, reqString);
				    System.out.println(response);
			}
		});
		btnSelectShow.setBounds(35, 32, 124, 23);
		frame.getContentPane().add(btnSelectShow);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				PremiumView r= new PremiumView();
		        r.frame.setVisible(true);
			}
		});
		btnBack.setBounds(250, 8, 70, 13);
		frame.getContentPane().add(btnBack);
		
		JButton btnViewDetails = new JButton("View details");
		btnViewDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFrame desc = new JFrame("Description");
		        desc.setSize(300, 100);
		        desc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		                     
		        JPanel text = new JPanel();
		        JLabel desc_text = new JLabel();
		        JLabel desc_text1 = new JLabel();
		        String mytext = PremiumView.s.getDescription();
		        desc_text.setText("Details:"+mytext);
	            text.add(desc_text);
	            if(r!=null) {
	            	desc_text1.setText("Rating:"+r);
		            text.add(desc_text1);
	            }
	            else {
	            	desc_text1.setText("Rating:"+PremiumView.s.getRating());
		            text.add(desc_text1);
	            }
	            
		        JScrollPane scroll = new JScrollPane(text);
	            scroll.setMinimumSize(new Dimension(150, 100));
	            scroll.setPreferredSize(new Dimension(250, 100));
	            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	            
	            
	            desc.getContentPane().add(scroll);
	            desc.setVisible(true);
			}
		});
		btnViewDetails.setBounds(35, 195, 124, 23);
		frame.getContentPane().add(btnViewDetails);
		
		JButton btnViewHistory = new JButton("View history");
		btnViewHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String APIUrl = "http://localhost:8080/history/post/find";
				String response=  Utility.excutePost(APIUrl, Login.nameUser);
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<History>>(){}.getType();
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) gson.fromJson(response, listType);
				Reflaction r=new Reflaction();
				JTable t=new JTable();
				t=r.createTable(list);
				JFrame frame = new JFrame("History");
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
		btnViewHistory.setBounds(196, 195, 124, 23);
		frame.getContentPane().add(btnViewHistory);
		
		textField = new JTextField();
		textField.setBounds(35, 86, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnRating = new JButton("Rating");
		btnRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				float rating = Float.parseFloat(textField.getText());
				JSONObject reqObj= new JSONObject();
				reqObj = obj1(PremiumView.s.getId(),rating);
				 String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/show/post/editRating";
				    String response=  Utility.excutePost(APIUrl, reqString);
				    System.out.println(response);
				    r=response;
			}
		});
		btnRating.setBounds(196, 85, 124, 23);
		frame.getContentPane().add(btnRating);
		
		textField_1 = new JTextField();
		textField_1.setBounds(35, 140, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAddComment = new JButton("Add Comment");
		btnAddComment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String com = textField_1.getText();
				JSONObject reqObj= new JSONObject();
				System.out.println(Login.nameUser);
				reqObj = obj(com,Login.nameUser,PremiumView.s.getName());
				 String reqString= reqObj.toString();
				    String APIUrl=  "http://localhost:8080/comment/post/save";
				    String response=  Utility.excutePost(APIUrl, reqString);
				    System.out.println(response);
			}
		});
		btnAddComment.setBounds(196, 139, 124, 23);
		frame.getContentPane().add(btnAddComment);
		
		JButton btnViewComments = new JButton("View Comments");
		btnViewComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String APIUrl = "http://localhost:8080/comment/post/find";
				String response=  Utility.excutePost(APIUrl, PremiumView.s.getName());
				Gson gson = new Gson();
				Type listType = new TypeToken<ArrayList<Comment>>(){}.getType();
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) gson.fromJson(response, listType);
				Reflaction r=new Reflaction();
				JTable t=new JTable();
				t=r.createTable(list);
				JFrame frame = new JFrame("Comments");
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
		btnViewComments.setBounds(196, 32, 124, 23);
		frame.getContentPane().add(btnViewComments);
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj3(String show,String user){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("showName", show);
		jsonobj.put("userName", user);
				
		return jsonobj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj(String com,String user,String show){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("com", com);
		jsonobj.put("userName", user);
		jsonobj.put("showName", show);
		return jsonobj;
	}
	@SuppressWarnings("unchecked")
	public JSONObject obj1(int id,float rating){
		JSONObject jsonobj = new JSONObject();

		jsonobj.put("id", id);
		jsonobj.put("rating", rating);
		return jsonobj;
	}
}
