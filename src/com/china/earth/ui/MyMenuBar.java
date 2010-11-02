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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.china.domain.Demo;
import com.china.domain.Earth;

public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = -3664413533024211563L;
	JButton button1;
	private JMenu[] Menus = { new JMenu("文件"),
							new JMenu("选项"), 
							new JMenu("帮助") };
	private JMenuItem[] Items = { new JMenuItem("打开"), 
									new JMenuItem("退出"),
									new JMenuItem("设置"),
									new JMenuItem("关于"), };

	private JFrame	mother;
	public MyMenuBar(JFrame frame){
		mother = frame;
		/* Init Menubar */
		Items[0].addActionListener(new OpenAction());
		Items[1].addActionListener(new ExitAction());
		Menus[0].add(Items[0]);
		Menus[0].add(Items[1]);

		Menus[1].add(Items[2]);
		Items[3].addActionListener(new AboutAction());
		Menus[2].add(Items[3]);

		
		this.add(Menus[0]);
		this.add(Menus[1]);
		this.add(Menus[2]);
	}

	public class OpenAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					
			JFileChooser fileChooser = new JFileChooser();
			  fileChooser.setCurrentDirectory(new File("."));
			int option = fileChooser.showOpenDialog(mother);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					FileReader reader = new FileReader(file);
					BufferedReader bufReader = new BufferedReader(reader);
					String data;
					int i = 0;
					Integer row = TabCalibrate.tableModel.getRowCount();

					System.out.println(row.toString());
					for (i = 0; i < row; i++)
						TabCalibrate.tableModel.removeRow(0);
					row = 0;
					while ((data = bufReader.readLine()) != null) {
						
						String[] subData = data.split(Earth.SPLIT,
								Earth.LEN + 1);
						// for(i=0; i<subData.length; i++)
						// System.out.print(" {"+ subData[i]);
						// System.out.println("subData.length"+ subData.length);
						TabCalibrate.tableModel.addRow(subData);
						for(i = subData.length-1; i < Earth.LEN; i++)
							TabCalibrate.tableModel.setValueAt(0, row, i);
						row++;
					}
					Demo.statBar.setText("成功导入" + row + "条记录");
					Demo.Tabs.setSelectedIndex(0);

					bufReader.close();
					reader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}

				System.out.println("select filename:" + file.getPath());
			} else {
				System.out.println("open file cancel");
			}
		}
	}

	

	public static class ExitAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("GoodBye \n curel world");
			System.exit(0);
		}

	};

	class AboutDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AboutDialog(JFrame parent) {
			super(parent, "关于", true);
			setLocationRelativeTo(parent);
			setLayout(new GridLayout(3, 1));
			JLabel L1 = new JLabel(" Copyright 2010-2012. All rights reserved ");
			L1.setHorizontalAlignment(JLabel.CENTER);
			JLabel L2 = new JLabel("Guevara.ya@gmail.com ");
			L2.setHorizontalAlignment(JLabel.CENTER);
			add(L1);
			add(L2);
			JButton ok = new JButton("确定");
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					dispose();
				}
			});
			JPanel p = new JPanel();
			p.add(ok);
			add(p);
			pack();
		}
	}

	protected class AboutAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			AboutDialog about = new AboutDialog(mother);
			about.setVisible(true);
		}

	}

	protected static class CloseHandler extends WindowAdapter {
		public void windowClosing(final WindowEvent event) {
			System.exit(0);

		}

	}
}
