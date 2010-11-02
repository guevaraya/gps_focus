package com.china.earth.ui;

/* ------------------------------------------------------------------------- */
/* Copyright (C) 2010 guevara.ya@gmail.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

/* ------------------------------------------------------------------------- */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.china.domain.Demo;
import com.china.domain.Earth;
import com.china.domain.Point;


public class TabCalibrate extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -185793775687838976L;
	JTable table;
	public static DefaultTableModel tableModel;
	List<Earth> earthList = new ArrayList<Earth>();
	JTextField aveBear;
	private JFrame	mother;
	public static Object[] columnName = {
		"地  名", 
		"纬  度1", "精  度1",
		"纬  度2", "精  度2",
		"距  离", "偏 向 角" 
		};
	public TabCalibrate(JFrame frame){
		mother = frame;

	/* Tab Calibrate */
	Object[][] data = {
			{ "德庆镇党委", new Double(91.3651), new Double(29.6719), 0, 0, 0, 0 },
			{ "德庆镇党委", new Double(91.3667), new Double(29.6693), 0, 0, 0, 0 },
			{ "铁索桥", new Double(91.3760), new Double(29.6774), 0, 0, 0, 0 },
			{ "铁索桥", new Double(91.3776), new Double(29.6746), 0, 0, 0, 0 },
			{ "x西藏大学", new Double(91.1454), new Double(29.6490), 0, 0, 0, 0 },
			{ "x西藏大学", new Double(91.1470), new Double(29.6463), 0, 0, 0, 0 },

	};
	tableModel = new DefaultTableModel(data, columnName);
	table = new JTable(tableModel);
	// table.setPreferredScrollableViewportSize(new Dimension(100, 30));
	JScrollPane scroll = new JScrollPane(table);
	JPanel north = new JPanel();
	north.add(scroll);
	JPanel center = new JPanel();
	aveBear = new JTextField("");
	aveBear.setColumns(15);
	center.add(new JLabel("平均偏向角：(°)"));
	center.add(aveBear);
	JPanel south = new JPanel();
	JButton caliButton = new JButton("计算");
	caliButton.addActionListener(new CaliAction());
	south.add(caliButton);
	JButton nextButton = new JButton("下一步");
	nextButton.addActionListener(new NextAction());
	south.add(nextButton);
	JButton saveButton = new JButton("保存");
	saveButton.addActionListener(new SaveAction());
	south.add(saveButton);
	
	this.setLayout(new BorderLayout());
	this.add(north, BorderLayout.NORTH);
	this.add(center, BorderLayout.CENTER);
	this.add(south, BorderLayout.SOUTH);
	this.setOpaque(true);
}

class CaliAction implements ActionListener {
	public void actionPerformed (ActionEvent e)
	{
		
		Integer row = 0,col = 0;
		Double sumBear = 0.0;
		for(row = 0; row < tableModel.getRowCount(); row++){
		
			col = 0;
			String name =  table.getValueAt(row, col++).toString();
			Point p1 = new Point(table.getValueAt(row, col++).toString(),
								table.getValueAt(row, col++).toString());
			Point p2 = new Point(table.getValueAt(row, col++).toString(),
								table.getValueAt(row, col++).toString());
			earthList.add(new Earth(name,p1 ,p2,
					table.getValueAt(row, col++).toString(),
					table.getValueAt(row, col++).toString()));
			}
		row=0;
		for(Earth el: earthList){
			Double dis = el.getDistance();
			Double bear = el.getBearing();
			table.setValueAt(dis,  row, 5);
			table.setValueAt(bear,  row, 6);
			row++;
			sumBear += bear;
			
		}
		sumBear/=row;
		aveBear.setText(sumBear.toString());
		Demo.statBar.setText("校准完成");
		earthList.clear();
	}
}

 class NextAction implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		Demo.Tabs.setSelectedIndex(1);
		Demo.statBar.setText("跳转..");
	}
}
 class SaveAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			  fileChooser.setCurrentDirectory(new File("."));
			File file;
			int option = fileChooser.showSaveDialog(mother);
			if (option == JFileChooser.APPROVE_OPTION) {
				 file = fileChooser.getSelectedFile();
				try {
					FileWriter writer = new FileWriter(file);
					BufferedWriter bufWriter = new BufferedWriter(writer);
					String data;
					int col;
					Integer row ;
					data =  "";
					for(col = 0; col <Earth.LEN; col++){
						data = data.concat((String) TabCalibrate.columnName[col]);
						data = data.concat(",");
					}
					bufWriter.write(data);
					bufWriter.newLine();
					for(row = 0; row < tableModel.getRowCount(); row++){
						col = 0;
						data =  "";
						for(col = 0; col <Earth.LEN; col++){
							data = data.concat(table.getValueAt(row, col).toString());
							data = data+",";
						}
					
						bufWriter.write(data);
						bufWriter.newLine();
					}
					bufWriter.close();
					writer.close();
					Demo.statBar.setText("成功保存到"+file.getPath());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
			} else {
				System.out.println("save file cancel");
			}
			
		}
	}
}
