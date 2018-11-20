package labirynt;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author Jaroslaw Jedruszczak
 * @version 1
 * @since 30.01.20176
 *
 */

/**
 * 
 * 
 * Klasa rozszerzajaca JPanel o funkcje doubleBuffera
 */
public class OknoGry  extends JPanel{

	private static final long serialVersionUID = 1L;

	    /*
	     * Konstruktor maluje tlo panelu
	     */
		OknoGry()
		{
			this.setBackground(Color.BLUE);
		}
		@Override
		/*
		 * Metoda dodajaca funkcje doubleBuffera
		 */
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);		
				Labirynt.gra.repaint(g);
			
			
		}
		
		
	
}
