package com.presupuesto.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MostrarPanelNorte extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel fechaNorte;

	public MostrarPanelNorte(String fechaCompleta) {
		Box boxNorte = Box.createHorizontalBox();	
		fechaNorte = new JLabel();
		fechaNorte.setForeground(Color.white);
		fechaNorte.setFont(new Font("Sans Serif", Font.BOLD, 15));
		fechaNorte.setText(fechaCompleta.toUpperCase());
		boxNorte.add(fechaNorte);
		this.add(boxNorte);
		this.setBackground(new java.awt.Color(19,104,131));
		this.updateUI();
	}

}
