package gemiSavasi;

import javax.swing.*;

public class Gemi
{
	ImageIcon gemiResmi;
	private int can;
		
	public Gemi(ImageIcon gemiResmi, int can)
	{
		this.gemiResmi = gemiResmi;
		this.can = can;		
	}

	public int getCan() {
		return can;
	}

	public void setCan(int can) {
		if(this.can != 0)
			this.can = can;
	}
	
	public ImageIcon getResim() {
		return gemiResmi;
	}
}
