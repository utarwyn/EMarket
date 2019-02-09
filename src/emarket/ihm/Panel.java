package emarket.ihm;

import javax.swing.*;

public abstract class Panel extends JPanel {

	private String title;


	public Panel(String title) {
		this.title  =  title;

		this.setOpaque(false);
		this.init();
	}


	public String getTitle() { return this.title; }


	public abstract void init();

}
