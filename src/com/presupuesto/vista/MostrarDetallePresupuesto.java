package com.presupuesto.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import com.presupuesto.controlador.ResultSetTableModel;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;



public class MostrarDetallePresupuesto extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTable tablaDetalles;
	public ResultSetTableModel modeloTabla;
	private JYearChooser yearChooser;
	private JMonthChooser monthChooser;
	
	public MostrarDetallePresupuesto() throws ClassNotFoundException, SQLException
	{
		super( "Detalle Presupuesto" ); 

		try {
			
			//INICIO CONSTRUCCION DEL PANEL OCCIDENTE 
			JPanel panelOccidente = new JPanel();	
			Box boxOccidente = Box.createVerticalBox();	
			
			Border raisedbevel, loweredbevel;
			raisedbevel = BorderFactory.createRaisedBevelBorder();
			loweredbevel = BorderFactory.createLoweredBevelBorder();
			Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);		
			Border redline = BorderFactory.createLineBorder(new java.awt.Color(255,98,9));
			compound = BorderFactory.createCompoundBorder(redline, compound);
								
			JPanel panelFiltro = new JPanel();
			panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtro por Año"));
			Box boxFiltro = Box.createVerticalBox();			
			JPanel panelBotonFiltro = new JPanel();	
			yearChooser = new JYearChooser();		
			monthChooser = new JMonthChooser();
			JButton botonFiltro = new JButton("Filtrar");
			botonFiltro.setBackground(Color.black);
			botonFiltro.setForeground(Color.white);
			botonFiltro.setBorder(compound);	
			botonFiltro.setMaximumSize(new Dimension(200,50));
			botonFiltro.setPreferredSize(new Dimension(100,30));			
			panelBotonFiltro.add(botonFiltro);
			boxFiltro.add(yearChooser);
			boxFiltro.add(monthChooser);
			boxFiltro.add(panelBotonFiltro);		
			panelFiltro.add(boxFiltro);	
			
			JPanel panelPresupuesto = new JPanel();
			panelPresupuesto.setBorder(BorderFactory.createTitledBorder("Totales"));
			panelPresupuesto.setLayout( new GridLayout( 3, 1, 0, 0) );
			
			/*Panel panelActivos = new JPanel();
			panelActivos.setBackground(new java.awt.Color(151,206,120));
			JLabel labelActivos = new JLabel(valorTotalIngresos);
			panelActivos.add(labelActivos);
			JPanel panelPasivos = new JPanel();
			panelPasivos.setBackground(new java.awt.Color(206,120,120));
			JLabel labelPasivos = new JLabel(valorTotalGastos);
			panelPasivos.add(labelPasivos);
			JPanel patrimonio = new JPanel();
			patrimonio.setBackground(new java.awt.Color(120,162,206));
			JLabel labelPatrimonio = new JLabel(valorDif);
			patrimonio.add(labelPatrimonio);		
				
			panelPresupuesto.add(panelActivos);
			panelPresupuesto.add(panelPasivos);
			panelPresupuesto.add(patrimonio);*/
			
			boxOccidente.add(panelFiltro);	
			boxOccidente.add(panelPresupuesto);
			panelOccidente.add(boxOccidente);
			//FIN CONSTRUCCION DEL PANEL OCCIDENTE
					
			modeloTabla = new ResultSetTableModel("DETALLES");
			JTable tabla = new JTable( modeloTabla );
			Font fuente = new Font("Sans Serif", Font.BOLD, 12);
			Font fuente2 = new Font("Sans Serif", Font.BOLD, 12);			
			tabla.getTableHeader().setFont(fuente);
			tabla.getTableHeader().setBackground(Color.black);
			tabla.getTableHeader().setForeground(Color.white);
			tabla.setFont(fuente2);		
			
			add( new JScrollPane( tabla ), BorderLayout.CENTER);
			add( panelOccidente , BorderLayout.WEST);
			
		    setSize(600, 400);		
			setResizable( true );
		    setLocationRelativeTo(null);
		    setVisible(true);
			
		} catch (SQLException excepcionSql) {
			JOptionPane.showMessageDialog( null, excepcionSql.getMessage(), 
			"Error en base de datos", JOptionPane.ERROR_MESSAGE );

			modeloTabla.desconectarDeBaseDatos();

			System.exit( 1 );
		}
	}

}
