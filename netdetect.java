import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*; 

class netdetect implements ActionListener
{
	Frame f;
	Label l1,l2,l3,l4;
	Button b1;
	Socket s3,s4;
	DataInputStream din1,din2;
	ServerSocket soc1,soc2;
	Connection co;
	PreparedStatement ps;
	netdetect()
	{
	f=new Frame("Server");
	l1=new Label("   Network Detector");
	l2=new Label(" Arjun");
	l3=new Label(" Elon");
	l4=new Label(" Stark");
	b1=new Button("Stop");
	
	f.setLayout(null);
	f.setSize(400,270);
	l1.setFont(new Font("Times New Roman", 1, 24));
	l2.setFont(new Font("Times New Roman", 1, 24));
	l3.setFont(new Font("Times New Roman", 1, 24));
	l4.setFont(new Font("Times New Roman", 1, 24));
	b1.setFont(new Font("Times New Roman", 1, 22));
	f.setBackground(new Color(204, 204, 204));
	l2.setBackground(new Color(255,0,0));
	l3.setBackground(new Color(255,0,0));
	l4.setBackground(new Color(255,0,0));
	
	f.add(l1);
	f.add(l2);
	f.add(l3);
	f.add(l4);
	f.add(b1);
	
	l1.setBounds(80,50,300,40);
	b1.setBounds(140,120,80,40);
	l2.setBounds(60,190,80,30);
	l3.setBounds(160,190,80,30);
	l4.setBounds(260,190,80,30);
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
		//online status
		{
			try
			{
			String sta="";
			while(true)
			{
				
				soc2=new ServerSocket(601);
				din2=new DataInputStream(s4.getInputStream());
				sta=din2.readUTF();
				din2.close();
				if(!sta.equals(""))
				{
					StringTokenizer st2=new StringTokenizer(sta,":");
					String from1=st2.nextToken();
					String conn1=st2.nextToken();
					ps=co.prepareStatement("update port set Status='1' where Name=?");
					ps.setString(1,from1);
					ps.close();
				}

			}	
		}catch(Exception ea)
		{
			System.out.print("ea");
		}

		}
		
		//Network Detector
		{
			try
			{
			String onl="";
			soc1=new ServerSocket(201);
			while(true)
			{
			s3=soc1.accept();
			din1=new DataInputStream(s3.getInputStream());
			onl=din1.readUTF();	
			din1.close();
			StringTokenizer st1=new StringTokenizer(onl,":");
					
			String from=st1.nextToken();
			String conn=st1.nextToken();
			
			if(conn.equals("1"))
			{
				if(from.equals("Elon"))
				{
					l3.setBackground(new Color(0,255,0));
				}
				else if(from.equals("Stark"))
				{
					l4.setBackground(new Color(0,255,0));
				}
				if(from.equals("Arjun"))
				{
					l2.setBackground(new Color(0,255,0));
				}
			}
			else if(conn.equals("0"))
			{
				if(from.equals("Elon"))
				{
					l3.setBackground(new Color(255,0,0));
				}
				else if(from.equals("Stark"))
				{
					l4.setBackground(new Color(255,0,0));
				}
				if(from.equals("Arjun"))
				{
					l2.setBackground(new Color(255,0,0));
				}
			}
			}
			}catch(Exception ef)
			{
				System.out.print("Network Detector  "+ef);
			}
		
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}
	public static void main(String ar[])
	{
		new netdetect();
	}
}