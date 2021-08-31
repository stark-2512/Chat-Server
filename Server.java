import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*; 
class server implements ActionListener
{
	Frame f;
	Label l1;
	Button b1;
	Socket s1,s2;
	ServerSocket soc;
	DataInputStream din;
	DataOutputStream dou;
	PreparedStatement ps;
	ResultSet rs;
	Connection co;
	server()
	{
		f=new Frame("Server");
		l1=new Label("Server is Running");
		b1=new Button("Stop");
		
		f.setLayout(null);
		f.setSize(400,200);
		l1.setFont(new Font("Times New Roman", 1, 24));
		b1.setFont(new Font("Times New Roman", 1, 22));
		f.setBackground(new Color(204, 204, 204));
		
		f.add(l1);
		f.add(b1);
		
		l1.setBounds(80,50,300,40);
		b1.setBounds(140,120,80,40);
		b1.addActionListener(this);
		f.setVisible(true);
		
		//Connection Setup
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				co=DriverManager.getConnection("jdbc:mysql://localhost:3306/chatserver","root",null);

			}catch(Exception e)
			{
				System.out.print("connnection "+e);
			}
		}
		
		
		try
		{
			soc=new ServerSocket(200);
			
			while(true)
			{
				
				String msg="";
				String name="";
				String port="";
				
				s1=soc.accept();
				din=new DataInputStream(s1.getInputStream());
				String re=din.readUTF();	
				din.close();
				
				if(!re.equals(null))
				{
					StringTokenizer st=new StringTokenizer(re,":");
					
					name=st.nextToken();
					msg=st.nextToken();
					
					ps=co.prepareStatement("select Port from port where Name=?");
					ps.setString(1,name);
					rs=ps.executeQuery();
					while(rs.next())
					{
						port = port + rs.getString(1);
					}
					ps.close();
					rs.close();
					
					s2=new Socket("localhost",Integer.parseInt(port));
					dou=new DataOutputStream(s2.getOutputStream());
					dou.writeUTF(msg);
				
					
				}
			}
		}catch(Exception ee)
		{
			System.out.print(ee);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			System.exit(0);
		}
	}
	
	public static void main(String ar[])
	{
		new server();
	}
	
}