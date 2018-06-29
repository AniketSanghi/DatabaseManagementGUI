import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import DatabaseDriver.*;

public class UniversalGUI implements ActionListener
{
	JFrame jframe;
	JToolBar north,south;
	JButton open,e1,e2,e3,create;
	JLabel databasenamelabel;
	JTextField databasename;
	JTextArea textarea;
	JTabbedPane jtp;
	DatabaseDriver dd = new DatabaseDriver();

	void initFrame()
	{
		jframe = new JFrame("Universal GUI");
		jframe.setSize(new Dimension(500,500));
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
	}

	void initJToolBar()
	{
		north = new JToolBar("North");
		north.setFloatable(false);
		south = new JToolBar("South");
		south.setFloatable(false);
	}

	void initJButton()
	{
		open = new JButton("Open");
		e1 = new JButton("Execute");
		e2 = new JButton("Execute Query");
		e3 = new JButton("Execute Update");
		create = new JButton("Create");
	}

	void initJLabel()
	{
		databasenamelabel = new JLabel("DataBase Name: ");
	}

	void initJTextField()
	{
		databasename = new JTextField(20);
	}

	void initJTabbedPane()
	{
		jtp = new JTabbedPane();
	}

	void initJTextArea()
	{
		textarea = new JTextArea();
	}

	void putButtonOnToolBar()
	{
		north.add(open);
		north.add(e1);
		north.add(e2);
		north.add(e3);
	}

	void putComponentsOnSouthToolBar()
	{
		south.add(databasenamelabel);
		south.add(databasename);
		south.add(create);
	}

	void putJTextAreaOnTabbedPane()
	{
		jtp.addTab("Query",new JScrollPane(textarea));
	}

	public UniversalGUI()
	{
		initFrame();
		initJToolBar();
		initJButton();
		initJLabel();
		initJTextField();
		initJTextArea();
		initJTabbedPane();

		putButtonOnToolBar();
		putComponentsOnSouthToolBar();
		putJTextAreaOnTabbedPane();

		open.addActionListener(this);
		e1.addActionListener(this);
		e2.addActionListener(this);
		e3.addActionListener(this);
		create.addActionListener(this);

		jframe.add(north,BorderLayout.NORTH);
		jframe.add(south,BorderLayout.SOUTH);
		jframe.add(jtp,BorderLayout.CENTER);

		jframe.setVisible(true);
	}


	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Open"))
		{
			try
			{
				dd.open(databasename.getText());
				JOptionPane.showMessageDialog(jframe,"Database Open Successfully");
				open.setText("Close");
			}
			catch(Exception e)
			{
				System.out.println(e);
				JOptionPane.showMessageDialog(jframe,"Error in opening Database");
			}
		}
		else if(ae.getActionCommand().equals("Close"))
		{
			try
			{
				dd.close();
				JOptionPane.showMessageDialog(jframe,"Database Closed Successfully");

				open.setText("Open");
			}
			catch(Exception e)
			{
				System.out.println(e);
				JOptionPane.showMessageDialog(jframe,"Error in closing Database");

			}
		}
		else if(ae.getActionCommand().equals("Create"))
		{
			try
			{
				dd.open(databasename.getText());
				JOptionPane.showMessageDialog(jframe,"Database already exists! Connection Established!");
				open.setText("Close");
			}
			catch(Exception e)
			{
				try 
				{
					dd.create(databasename.getText());
					JOptionPane.showMessageDialog(jframe,"Database Successfully Created");
					open.setText("Close");
				}
				catch(Exception ee)
				{
					System.out.println(e);
					JOptionPane.showMessageDialog(jframe,"Error in creating Database");
				}
			}
		}
		else if(ae.getActionCommand().equals("Execute"))
		{
			try
			{
				Boolean status = dd.execute(textarea.getText());
				JOptionPane.showMessageDialog(jframe,"Error Status: " + status);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(jframe,"Error In executing Query");
				System.out.println(e);
			}
		}
		
		else if(ae.getActionCommand().equals("Execute Update"))
		{
			try
			{
				int status = dd.executeUpdate(textarea.getText());
				JOptionPane.showMessageDialog(jframe,"Error Status: " + status);
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(jframe,"Error In executing Query");
				System.out.println(e);
			}
		}
		else if(ae.getActionCommand().equals("Execute Query"))
		{
			try
			{
				dd.executeQuery(textarea.getText());
				String header[]=dd.getColumnNames(20);
				String data[][]=dd.getData();
				if(data==null)
				{
					JOptionPane.showMessageDialog(jframe,"Table is Empty");
					return ;
				}
				JPanel jpan = new JPanel();
				String data1 = "<html><table border=2><tr>";
				for(int i=0;i<header.length;++i)
					data1+="<td>"+header[i]+"</td>";
				data1+="</tr>";
				for(int i=0;i<data.length;++i)
				{
					data1+="<tr>";
					for(int j=0;j<data[i].length;j++)
						data1+="<td>"+data[i][j]+"</td>";
					data1+="</tr>";
				}
				data1+="</table>";

				jpan.add(new JLabel(data1));
				
				JOptionPane.showMessageDialog(jframe,jpan);


			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(jframe,"Error In executing Query");
				System.out.println(e);
			}
		}

	}
	public static void main(String args[])throws Exception
	{
		new UniversalGUI();
	}

}