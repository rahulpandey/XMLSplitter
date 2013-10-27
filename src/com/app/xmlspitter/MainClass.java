    package com.app.xmlspitter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainClass {

	public static void main(String[] args) {
			
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.printf("%s", e.getMessage().toString());
		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				
				XMLSplitter splitter = new XMLSplitter();
				splitter.setSize(550, 330);
				splitter.setVisible(true);
				splitter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			}
		});

	}

}
;