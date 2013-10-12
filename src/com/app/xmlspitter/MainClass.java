package com.app.xmlspitter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainClass {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				XMLSplitter splitter = new XMLSplitter();
				splitter.setSize(550, 330);
				splitter.setVisible(true);
				splitter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});

	}

}
