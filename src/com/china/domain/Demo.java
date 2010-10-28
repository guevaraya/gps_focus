package com.china.domain;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello,world!");
		new TOrientateFrame("Calculate Distance/Bearing");
	}
}

class TOrientateFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4398852471661924273L;
	private static  String SPLIT = "\\s+"; 
	JLabel point1, point2, distence, distence_value, bearing, bearing_value;
	TextField latitude1, latitude2, longitude1, longitude2;
	JButton button1;
	private JMenuBar	mb;
	private JMenu[] Menus = {
			new JMenu("文件"),
			new JMenu("选项"),
			new JMenu("帮助")
	};
	private JMenuItem[] Items = {
			new JMenuItem("打开"),
			new JMenuItem("退出"),
			new JMenuItem("设置"),
			new JMenuItem("关于"),
	};
	private JTabbedPane Tabs = new JTabbedPane();;	
	JTable table;

	private ButtonListener Bl = new ButtonListener();
	private JLabel statBar;


	private List<Earth> earthList = new ArrayList<Earth>();
	/**
	 * @param name
	 * @throws Exception
	 */
	/**
	 * @param name
	 * @throws Exception
	 */
	public TOrientateFrame(String name) throws Exception {
		super(name);
		/* Init Menubar */
		Items[0].addActionListener(new OpenAction());
		Items[1].addActionListener(new ExitAction());
		Menus[0].add(Items[0]);
		Menus[0].add(Items[1]);

		Menus[1].add(Items[2]);
		Items[3].addActionListener(new AboutAction());
		Menus[2].add(Items[3]);
		
		mb = new JMenuBar();
		mb.add(Menus[0]);
		mb.add(Menus[1]);
		mb.add(Menus[2]);
		setJMenuBar(mb);
		
		/* Tab Calibrate */
		JPanel Calibrate = new JPanel(); 
		Object[] columnName = {"地名","纬度","精度","距离","偏向角"};
		Object[][] data = {
				{"德庆镇党委", new Double(91.3651), new Double (29.6719),0,0}, 
				{"德庆镇党委", new Double(91.3667 ), new Double (29.6693),0,0},
				{"铁索桥", new Double(91.3760), new Double (29.6774),0,0},
				{"铁索桥", new Double(91.3776 ), new Double (29.6746),0,0},
				{"x西藏大学", new Double(91.1454), new Double (29.6490),0,0},		
				{"x西藏大学", new Double(91.1470), new Double (29.6463),0,0},

			};
		table = new JTable(data, columnName);
	//	table.setPreferredScrollableViewportSize(new Dimension(100, 30));
		JScrollPane scroll = new JScrollPane(table);
		JPanel north = new JPanel();
		north.add(scroll);
		JPanel center = new JPanel();
		center.add(new JLabel("平均偏向角："));
		center.add(new JLabel("xxxx  °"));
		JPanel south = new JPanel();
		south.add(new JButton("计算"));
		south.add(new JButton("下一步"));
	
		Calibrate.setLayout(new BorderLayout());
		Calibrate.add(north,BorderLayout.NORTH);
		Calibrate.add(center,BorderLayout.CENTER);
		Calibrate.add(south,BorderLayout.SOUTH);
		Calibrate.setOpaque(true);
		Tabs.add("校准",Calibrate);
		
		/* Tab Calibrate */
		JPanel[] p = new JPanel[5];
		GridBagConstraints gridbagcn = new GridBagConstraints();
		gridbagcn.gridheight = 1;
		gridbagcn.gridwidth = 1;
		gridbagcn.gridx = 0;
		gridbagcn.gridy = 0;
		gridbagcn.weightx = 0;
		gridbagcn.weightx = 0;
		gridbagcn.anchor = GridBagConstraints.WEST;
		point1 = new JLabel("point1:");

		latitude1 = new TextField("29.40158 ");
		longitude1 = new TextField("91.22004 ");
		
		p[0] = new JPanel(new GridBagLayout());
		p[0].add(point1,gridbagcn);
		gridbagcn.gridx+=1;
		p[0].add(latitude1,gridbagcn);
		gridbagcn.gridx++;
		p[0].add(longitude1,gridbagcn);
		
		point2 = new JLabel("point2:");
		latitude2 = new TextField("29.40313 ");
		longitude2 = new TextField("91.21908 ");
		p[1] = new JPanel(new FlowLayout());
		p[1].add(point2);
		p[1].add(latitude2);
		p[1].add(longitude2);
		
		distence = new JLabel("distence：");
		distence_value = new JLabel("-");
		p[2] = new JPanel(new FlowLayout());
		p[2].add(distence);
		p[2].add(distence_value);
		bearing = new JLabel("bearing:");
		bearing_value = new JLabel("-");
		p[3] = new JPanel(new FlowLayout());
		p[3].add(bearing);
		p[3].add(bearing_value);
		
		button1 = new JButton("Calculate");
		p[4] = new JPanel(new FlowLayout());
		p[4].add(button1);
		
		JPanel Calculate = new JPanel();
		Calculate.setLayout(new GridLayout(5, 1));
		for(int i=0; i < 5;i++)
			Calculate.add(p[i]);
		Tabs.add("计算",Calculate);

		this.add(Tabs);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	this.setSize(420, 380);
		this.setVisible(true);
		this.pack();
		button1.addActionListener(Bl);
		// TimeUnit.SECONDS.sleep(10);
		// label.setText("Hello,kit!");
	}
	public class OpenAction implements ActionListener {
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed (ActionEvent e){
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showOpenDialog(TOrientateFrame.this);
		if(option == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			try {
				FileReader  reader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(reader);
				String data;
				
				while((data = bufReader.readLine())!=null){
					int i = 0;
					String[] subData = data.split(TOrientateFrame.SPLIT,Earth.LEN);
				//for(i=0; i<7; i++)
				//	System.out.println("["+i+"]= "+subData[i]);
					String name = subData[0];
					Point p1 = new Point(subData[1],subData[2]);
					Point p2 = new Point(subData[3],subData[4]);
					earthList.add(new Earth(name,p1 ,p2, Double.parseDouble(subData[5]), Double.parseDouble(subData[6])));
				}
				int row = 0;
				for(Earth el : earthList){
					int col = 0;
					table.setValueAt(el.getName(),row,col++);
					/*Set Point one/two  */
					table.setValueAt(el.getPointOne().lat, row, col++);
					table.setValueAt(el.getPointOne().lon, row, col++);
			//		table.setValueAt(el.getPointTwo().lat, row, col++);
			//		table.setValueAt(el.getPointTwo().lon, row, col++);
					/* Set distance and bearing */
					table.setValueAt(el.getDistance(), row, col++);					
					table.setValueAt(el.getBearing(), row, col++);
					row++;
				}
					
			bufReader.close();
			reader.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			
			System.out.println("select filename:"+file.getPath());
		}else{
			System.out.println("open file cancel");
		}
		}
	}
	public static class ExitAction implements ActionListener {
		public void actionPerformed (ActionEvent e){
			System.out.println("GoodBye \n curel world");
			System.exit(0);
		}
		
	};
	 class AboutDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AboutDialog(JFrame parent){
			super(parent, "关于",true);
			setLocationRelativeTo(parent);
			setLayout(new GridLayout(3,1));
			JLabel L1 = new JLabel(" Copyright 2010-2012. All rights reserved ");
			L1.setHorizontalAlignment(JLabel.CENTER);
			JLabel L2 = new JLabel("Guevara.ya@gmail.com ");
			L2.setHorizontalAlignment(JLabel.CENTER);
			add(L1);
			add(L2);
			JButton ok = new JButton("确定");
			ok.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			JPanel p = new JPanel();
			p.add(ok);
			add(p);
			pack();
		}
	}
	
	protected  class AboutAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
		AboutDialog  about =new AboutDialog(TOrientateFrame.this);
		about.setVisible(true);
		}
		
		
	} 
	
	protected static class CloseHandler	extends WindowAdapter{
		public void windowClosing(final WindowEvent event)
		{
			System.exit(0);

		}

	} 
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			Double lat = Double.parseDouble(latitude1.getText());
			Double lon = Double.parseDouble(longitude1.getText());
			Point p1 = new Point(lat, lon);
			lat = Double.parseDouble(latitude2.getText());
			lon = Double.parseDouble(longitude2.getText());
			Point p2 = new Point(lat, lon);
			Point p3 = p1.destination(p1.distance(p2),p1.bearing(p2));
			distence_value.setText(Double.toString(p1.distance(p2)) + " Km");
			bearing_value.setText(Double.toString(p3.lat) + " ---"+ Double.toString(p3.lon));
		}
	}
	
}


