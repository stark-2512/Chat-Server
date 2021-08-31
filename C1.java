import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
class  client1 implements ActionListener,ItemListener
{
	String se;
	int re;
	Socket s1,s2,s3;
	DataInputStream din;
	DataOutputStream dou,dou1;
	ServerSocket soc;
	Frame f;
	TextArea ta;
	Label l1,l2;
	TextField t1;
	Button b1,b2;
	Choice c1;
	Connection co;
	PreparedStatement ps;
	ResultSet rs;
	client1()
	{
		c1=new Choice();
		s1=new Socket();
		s2=new Socket();
		f=new Frame("Chat");
		ta=new TextArea();
		l1=new Label("Recieved Message");
		l2=new Label("Type Message");
		b1=new Button("Send");
		b2=new Button("X");
		t1=new TextField();
		
		f.add(c1);
		f.add(l1);
		f.add(l2);
		f.add(b1);
		f.add(t1);
		f.add(ta);
		f.add(b2);
		c1.add("Select");
		l1.setFont(new Font("Times New Roman", 1, 22));
		l2.setFont(new Font("Times New Roman", 1, 22));
		b1.setFont(new Font("Times New Roman", 1, 18));
		b2.setFont(new Font("Times New Roman", 1, 18));
		ta.setFont(new Font("Times New Roman", 0, 22));
		t1.setFont(new Font("Times New Roman", 0, 22));
		c1.setFont(new Font("Times New Roman", 0, 22));
		f.setBackground(new Color(204, 204, 204));
		f.setLayout(null);
		f.setSize(540,840);
		
		l1.setBounds(70,80,400,40);
		ta.setBounds(70,140,400,500);
		l2.setBounds(70,660,200,40);
		t1.setBounds(70,720,320,40);
		b1.setBounds(410,720,60,40);
		b2.setBounds(485,45,40,40);
		c1.setBounds(300,660,170,40);
		ta.setEditable(false);
		b1.addActionListener(this);
		b2.addActionListener(this);
		c1.addItemListener(this);
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
		//Online Show
		{
			try
			{
				s3=new Socket("localhost",601);
				dou1=new DataOutputStream(s3.getOutputStream());
						
				String s1="";
				s1="Elon:1";
				
				dou1.writeUTF(s1);
				
				
			}catch(Exception f)
			{
				System.out.print("client "+f);
			}
		}
		//Network Detector
		{
			try
			{
				s2=new Socket("localhost",201);
				dou=new DataOutputStream(s2.getOutputStream());
						
				String s="";
				s="Elon:1";
				
				dou.writeUTF(s);
				
				
			}catch(Exception f)
			{
				System.out.print("client "+f);
			}
		}
		try
		{
			
			
				ps=co.prepareStatement("select Name from port where Status=?");
				ps.setString(1,"1");
				rs=ps.executeQuery();
				while(rs.next())
				{
					String sta2="";
					sta2=rs.getString(1);
					c1.add(sta2);
				}
				ps.close();
				rs.close();
				
				soc=new ServerSocket(110);
				while(true)
				{
				s1=soc.accept();
				din=new DataInputStream(s1.getInputStream());
				ta.append("Client : "+din.readUTF()+"\n");	
				din.close();
			}
		}catch(Exception ee)
		{
			System.out.print("Idhar"+ee);
		}
		
		
	}
	
	public void itemStateChanged(ItemEvent f)
	{
		if(f.getSource()==c1)
		{
			
			se=c1.getSelectedItem();
			
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b2)
		{
			//Network Detector
		{
			try
			{
				s2=new Socket("localhost",201);
				dou=new DataOutputStream(s2.getOutputStream());
						
				String s="";
				s="Elon:0";
				
				dou.writeUTF(s);
				
				
			}catch(Exception f)
			{
				System.out.print("client "+f);
			}
		}
			System.exit(0);
		}
		
		else if(e.getSource()==b1)
		{
			try
			{
				s2=new Socket("localhost",200);
				dou=new DataOutputStream(s2.getOutputStream());
						
				String s="";
				s=se+":"+t1.getText();
				
				dou.writeUTF(s);
				
				
			}catch(Exception f)
			{
				System.out.print("client "+f);
			}
			t1.setText("");
		}
	}			
	
	public static void main(String ar[])
	{
		new client1();
	}
}