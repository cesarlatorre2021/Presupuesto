package com.presupuesto.vista;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JTable;

import com.presupuesto.controlador.ResultSetTableModel;

public class MostrarPanelCentral{
	
	public JTable MostrarPanelCentral(ResultSetTableModel modeloTabla) throws ClassNotFoundException, SQLException {
		JTable tabla = new JTable( modeloTabla );
		Font fuente = new Font("Sans Serif", Font.BOLD, 12);
		Font fuente2 = new Font("Sans Serif", Font.BOLD, 12);			
		tabla.getTableHeader().setFont(fuente);
		tabla.getTableHeader().setBackground(Color.black);
		tabla.getTableHeader().setForeground(Color.white);
		tabla.setFont(fuente2);
		return tabla;
	}
	
}
