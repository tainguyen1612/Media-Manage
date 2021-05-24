import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class ManageMedias {

	private JFrame frame;
	private JTextField txtName;
	private JTextField txtType;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtSearchID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageMedias window = new ManageMedias();
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
	public ManageMedias() {
		initialize();
		Connect();
		table_load();
	}
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtSearchRecord;
	private JTextField txtPriceValue;
	
	public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/ManageMedia", "root","taipro123");
        }
        catch (ClassNotFoundException ex) 
        {
          ex.printStackTrace();
        }
        catch (SQLException ex) 
        {
        	   ex.printStackTrace();
        }

    }
	
	public void table_load()
    {
    	try 
    	{
	    pst = con.prepareStatement("select * from Medias");
	    rs = pst.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
    	} 
    	catch (SQLException e) 
    	 {
    		e.printStackTrace();
	  } 
    }
	
	public void tableSortByName() {
		try {
			pst = con.prepareStatement("select * from Medias order by Name");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void tableSortByPrice() {
		try {
			pst = con.prepareStatement("select * from Medias order by Price");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 509, 398);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Manage Media");
		lblNewLabel.setBounds(174, 6, 107, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Register", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 36, 214, 143);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setBounds(6, 49, 61, 16);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Type");
		lblNewLabel_3.setBounds(6, 77, 61, 16);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Price");
		lblNewLabel_4.setBounds(6, 105, 61, 16);
		panel.add(lblNewLabel_4);
		
		txtName = new JTextField();
		txtName.setBounds(64, 44, 130, 26);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtType = new JTextField();
		txtType.setBounds(64, 72, 130, 26);
		panel.add(txtType);
		txtType.setColumns(10);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(64, 100, 130, 26);
		panel.add(txtPrice);
		txtPrice.setColumns(10);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,type,price;
//				id = txtID.getText();
				name = txtName.getText();
				type = txtType.getText();
				price = txtPrice.getText();
							
				 try {
					pst = con.prepareStatement("insert into Medias(Name,Type,Price)values(?,?,?)");
//					pst.setString(0, id);
					pst.setString(1, name);
					pst.setString(2, type);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
					table_load();
						           
					txtName.setText("");
					txtType.setText("");
					txtPrice.setText("");
					txtName.requestFocus();
				   }
			 
				catch (SQLException e1) 
			        {
						e1.printStackTrace();
			       }
			}
		});
		btnAdd.setBounds(20, 191, 85, 29);
		frame.getContentPane().add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_load();
			}
		});
		btnClear.setBounds(115, 223, 88, 29);
		frame.getContentPane().add(btnClear);
		
		JScrollPane tblTable = new JScrollPane();
		tblTable.setBounds(246, 34, 239, 186);
		frame.getContentPane().add(tblTable);
		
		table = new JTable();
		tblTable.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Find Record", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(20, 252, 214, 64);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("ID");
		lblNewLabel_5.setBounds(6, 29, 61, 16);
		panel_1.add(lblNewLabel_5);
		
		txtSearchID = new JTextField();
		txtSearchID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = txtSearchID.getText();

		                pst = con.prepareStatement("select Name,Type,Price from Medias where ID = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();

		            if(rs.next()==true)
		            {
		              
		                String name = rs.getString(1);
		                String edition = rs.getString(2);
		                String price = rs.getString(3);
		                
		                txtName.setText(name);
		                txtType.setText(edition);
		                txtPrice.setText(price);
		                
		                
		            }   
		            else
		            {
		            	txtName.setText("");
		            	txtType.setText("");
		                txtPrice.setText("");
		                JOptionPane.showMessageDialog(null, "Not Appear Record ID" + id  );
		                 
		            }
		            
		        } 
			
			 catch (SQLException ex) {
		           
		        }
			}
		});
		txtSearchID.setBounds(65, 24, 130, 26);
		panel_1.add(txtSearchID);
		txtSearchID.setColumns(10);
		
		JButton txtUpdate = new JButton("Update");
		txtUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name,type,price,id;
				
				
				name = txtName.getText();
				type = txtType.getText();
				price = txtPrice.getText();
				id  = txtSearchID.getText();
				
				 try {
						pst = con.prepareStatement("update Medias set Name= ?,Type=?,Price=? where ID =?");
						pst.setString(1, name);
			            pst.setString(2, type);
			            pst.setString(3, price);
			            pst.setString(4, id);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Update!!!!!");
			            table_load();
			           
			            txtName.setText("");
			            txtType.setText("");
			            txtPrice.setText("");
			            txtSearchID.setText("");
			            txtName.requestFocus();
					}
 
		            catch (SQLException e1) {
						
						e1.printStackTrace();
					}
			}
		});
		txtUpdate.setBounds(115, 191, 88, 29);
		frame.getContentPane().add(txtUpdate);
		
		JButton txtDelete = new JButton("Delete");
		txtDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String id;
				id  = txtSearchID.getText();
				
				 try {
						pst = con.prepareStatement("delete from Medias where ID =?");
				
			            pst.setString(1, id);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Delete!!!!!");
			            table_load();
			           
			            txtName.setText("");
			            txtType.setText("");
			            txtPrice.setText("");
			            txtSearchID.setText("");
			            txtName.requestFocus();
					}
			
			     catch (SQLException e1) {
						
						e1.printStackTrace();
				}
			}
		});
		txtDelete.setBounds(20, 223, 85, 29);
		frame.getContentPane().add(txtDelete);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Sort By", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(246, 232, 239, 58);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("Name");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableSortByName();
			}
		});
		btnNewButton.setBounds(6, 23, 80, 29);
		panel_2.add(btnNewButton);
		
		JButton btnSortPrice = new JButton("Price");
		btnSortPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableSortByPrice();
			}
		});
		btnSortPrice.setBounds(86, 23, 67, 29);
		panel_2.add(btnSortPrice);
		
		JButton btnDefault = new JButton("Default");
		btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_load();
			}
		});
		btnDefault.setBounds(153, 23, 80, 29);
		panel_2.add(btnDefault);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Search Records", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(20, 314, 214, 50);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		txtSearchRecord = new JTextField();
		txtSearchRecord.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String search = txtSearchRecord.getText();
				String query = "SELECT * from Medias WHERE Name LIKE '%" + search + "%'";
		        try {
		        	rs = pst.executeQuery(query);
		            table.setModel(DbUtils.resultSetToTableModel(rs));
		        } catch (SQLException ex) {
		        	JOptionPane.showMessageDialog(null,"Got an exception!");
		        };
			};
		});
		txtSearchRecord.setBounds(6, 18, 202, 26);
		panel_3.add(txtSearchRecord);
		txtSearchRecord.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "List of vehicles > X price", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(246, 302, 239, 62);
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		txtPriceValue = new JTextField();
		txtPriceValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				int search =  Integer.parseInt(txtPriceValue.getText());
				String query = "SELECT * from Medias WHERE Price > '" + search + "'";
		        try {
		        	rs = pst.executeQuery(query);
		            table.setModel(DbUtils.resultSetToTableModel(rs));
		        } catch (SQLException ex) {
		        	JOptionPane.showMessageDialog(null,"Got an exception!");
		        };
			}
		});
		txtPriceValue.setBounds(6, 19, 227, 26);
		panel_4.add(txtPriceValue);
		txtPriceValue.setColumns(10);
	}
}
