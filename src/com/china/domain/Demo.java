package com.china.domain;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.china.earth.ui.MyMenuBar;
import com.china.earth.ui.TabCalculate;
import com.china.earth.ui.TabCalibrate;


public class Demo extends JFrame {
	private static final long serialVersionUID = 4398852471661924273L;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello,world!");
		new Demo("Calculate Distance/Bearing");
	}
	
	public static  JTabbedPane Tabs = new JTabbedPane();;
	private TabCalibrate tab0;
	private TabCalculate tab1;
	JTable table;
	DefaultTableModel tableModel;
	JLabel aveBear;
	
	public final static  JLabel statBar = new JLabel("Status:-*-");

	public Demo(String name) throws Exception {
		super(name);

		setJMenuBar(new MyMenuBar(this));
		tab0 = new TabCalibrate(this);
		Tabs.add("Ð£×¼", tab0);
		tab1 = new TabCalculate();	
		Tabs.add("¼ÆËã", tab1);

		this.setLayout(new BorderLayout());
		this.add(Tabs, BorderLayout.NORTH);
		statBar.setHorizontalAlignment(JLabel.LEFT);
		JPanel tmp = new JPanel();
		tmp.setLayout(new FlowLayout(FlowLayout.LEFT));
		tmp.add(statBar);

		this.add(tmp, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setSize(420, 380);
		this.setVisible(true);
		this.pack();
			// TimeUnit.SECONDS.sleep(10);
		// label.setText("Hello,kit!");
	}

	


}
