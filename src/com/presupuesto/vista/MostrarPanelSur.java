package com.presupuesto.vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.presupuesto.controlador.ResultSetTableModel;

public class MostrarPanelSur extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ResultSetTableModel mostrarResultadosConsulta;
	
	public MostrarPanelSur(String fecha, String tipo){
		try {
			mostrarResultadosConsulta = new ResultSetTableModel(fecha);
			String valorTotal = mostrarResultadosConsulta.consultarTotalesIngresoGasto(fecha, tipo) ;

			this.setBackground(new java.awt.Color(19,104,131));
			this.setLayout( new GridLayout( 1, 2, 0, 0) );
			JLabel labelTotal = new JLabel("TOTAL: " + valorTotal);
			labelTotal.setForeground(Color.white);
			labelTotal.setFont(new Font("Sans Serif", Font.BOLD, 14));
			this.add(labelTotal);

			Box general = Box.createVerticalBox();
		    general.add(this);
		    this.updateUI();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}
	
}
