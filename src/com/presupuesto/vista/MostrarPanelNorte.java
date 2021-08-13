package com.presupuesto.vista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MostrarPanelNorte {
	
	public JPanel MostrarPanelNorte(String fechaCompleta) {
		JPanel panelNorte = new JPanel();
		Box boxNorte = Box.createHorizontalBox();	
		JLabel fechaNorte = new JLabel();
		fechaNorte.setForeground(Color.white);
		fechaNorte.setFont(new Font("Sans Serif", Font.BOLD, 15));
		fechaNorte.setText(fechaCompleta.toUpperCase());
		boxNorte.add(fechaNorte);
		panelNorte.add(boxNorte);
		panelNorte.setBackground(new java.awt.Color(19,104,131));
		return panelNorte;
	}

}
