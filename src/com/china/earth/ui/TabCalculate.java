package com.china.earth.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.china.domain.Point;

public class TabCalculate extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7309088931901104691L;

	public TabCalculate(){
		this.setLayout(new GridLayout(3, 1));
		CalucBlock part1 = new CalucBlock();
		part1.setButton1(CalucBlock.ACTIONONE);
		add(part1);
		CalucBlock part2 = new CalucBlock();
		part2.setButton1(CalucBlock.ACTIONTWO);
		add(part2);
	}
}
class CalucBlock extends JPanel{
	/**
	 * 
	 */
	public static final int 	ACTIONONE	=	1;
	public static final int 	ACTIONTWO	=	2;	
	private static final long serialVersionUID = 1L;
	JLabel point1, point2, distence, bearing ;
	JTextField distence_value,bearing_value;
	JTextField latitude1, latitude2, longitude1, longitude2;
	public CalucBlock() {
		
		
		
			
			this.setLayout(new GridLayout(4, 1));
			JPanel block1 = new JPanel();
			/* Point1 initialization */
			point1 = new JLabel("point1:");
			latitude1 = new JTextField("29.40158 ");
			latitude1.setColumns(6);
			longitude1 = new JTextField("91.22004 ");
			longitude1.setColumns(6);
			block1.add(point1);
			block1.add(latitude1);
			block1.add(longitude1);
			/* Point2 initialization */
			point2 = new JLabel("point2:");
			latitude2 = new JTextField("29.40158 ");
			latitude2.setColumns(6);
			longitude2 = new JTextField("91.21204 ");
			longitude2.setColumns(6);
			block1.add(point2);
			block1.add(latitude2);
			block1.add(longitude2);
			this.add(block1);
			
			JPanel block2 = new JPanel();
			distence = new JLabel("distence(Km)");
			distence_value = new JTextField();
			distence_value.setColumns(12);
			block2.add(distence);
			block2.add(distence_value);
			
			bearing = new JLabel("bearing：(°)");
			bearing_value = new JTextField("");
			bearing_value.setColumns(12);
			block2.add(bearing);
			block2.add(bearing_value);
			this.add(block2);
				
			Border border = BorderFactory.createLineBorder(Color.red);
			Border border1 = BorderFactory.createTitledBorder(border, "距离及角度计算");
			this.setBorder(border1);
	
	}
	
	boolean setButton1(int type){
		JPanel block4 = new JPanel();
		JButton calcu = new JButton("计算");
		if(type == CalucBlock.ACTIONONE){
			calcu.addActionListener(new calcu1Action());
		}else if(type == CalucBlock.ACTIONTWO){
			
			calcu.addActionListener(new calcu2Action());
			latitude2.setText("");
			longitude2.setText("");
			distence_value.setText("0.7749844 ");
			bearing_value.setText("-89.998036288 ");
			setBorder(BorderFactory.createTitledBorder("目标点计算"));
		}else {
			return false;
		}
		
		block4.add(calcu);
		this.add(block4);
		return 	true;
	}
	void setButton2(int type){

		}
	class calcu1Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			Point p1 = new Point(latitude1.getText(), longitude1.getText());
			Point p2 = new Point(latitude2.getText(), longitude2.getText());
			distence_value.setText(Double.toString(p1.distance(p2)));
			bearing_value.setText(Double.toString(p1.bearing(p2)));
		}
	}
	class calcu2Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			Point p1 = new Point(latitude1.getText(), longitude1.getText());
			Point p2 = p1.destination(Double.parseDouble(distence_value.getText()), 
					Double.parseDouble(bearing_value.getText()));
			latitude2.setText(Double.toString(p1.getLat()));
			longitude2.setText(Double.toString(p2.getLon()));
		}
	}
}
