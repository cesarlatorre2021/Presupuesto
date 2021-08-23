package com.presupuesto.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.presupuesto.controlador.ResultSetTableModel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;

import javax.swing.BorderFactory;
import javax.swing.Box;

public class MostrarVentanaGastos extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResultSetTableModel resultSetTableModel;

	private JLabel fecha;
	private JLabel categoria;
	private JLabel descripcion;
	private JTextField campoDescripcion;
	private JLabel valor;
	private JTextField campoValor;
	private JPanel panelMostrar;
	private JButton botonInsertar;
	private JComboBox<Object> campoCategoria;
	private JDateChooser dateChooser;
	private SimpleDateFormat formateador ;
	public MostrarResultadosConsulta mostrarResultadosConsulta;

	public MostrarVentanaGastos() throws ClassNotFoundException, SQLException
	{
		super( "Insertar Gasto" ); 
		
		String[] listaCategorias = { "Alimentación", "Deudas", "Transporte", "Ropa", "Mecato", "Tecnología", "Servicios", "Otros", "Viajes", "Ahorro", "Inversión"};
		resultSetTableModel = new ResultSetTableModel("GASTO");
		Date dato = new Date();
	    formateador = new SimpleDateFormat("dd/MM/yyyy");
		
		JPanel panelGasto = new JPanel();
		Box boxGasto =Box.createVerticalBox();
		
		panelMostrar = new JPanel();
		panelMostrar.setBorder(BorderFactory.createTitledBorder("Insertar Nuevo Gasto"));
		setLayout( new FlowLayout( FlowLayout.CENTER, 5, 5 ) );
		panelMostrar.setLayout( new GridLayout( 4, 2, 4, 4 ) );
		
		dateChooser = new JDateChooser(null, dato , dato.toString(), new JSpinnerDateEditor());			
		dateChooser.setDateFormatString("dd/MM/yyyy");
		fecha = new JLabel();
		fecha.setText( "Fecha" );
		
		categoria = new JLabel();
		campoCategoria = new JComboBox<Object>(listaCategorias);
		categoria.setText( "Categoria:" );
		
		descripcion = new JLabel();
		campoDescripcion = new JTextField( 10 );
		descripcion.setText( "Descripcion:" );
		
		valor = new JLabel();
		campoValor = new JTextField( 10 );
		valor.setText( "Valor:" );
			
		panelMostrar.add( fecha );
		panelMostrar.add( dateChooser );
		panelMostrar.add( categoria );
		panelMostrar.add( campoCategoria );
		panelMostrar.add( descripcion );
		panelMostrar.add( campoDescripcion );
		panelMostrar.add( valor );
		panelMostrar.add( campoValor );
				
		JPanel panelBoton = new JPanel();
		botonInsertar = new JButton();
		botonInsertar.setText( "Insertar Gasto" );

		panelGasto.add(panelMostrar);
		panelBoton.add(botonInsertar);
		
		boxGasto.add(panelGasto);
		boxGasto.add(panelBoton);
		
		add(boxGasto);
			
	    setSize(400, 250);		
		setResizable( true );
	    setLocationRelativeTo(null);
	    setVisible(true);
	   	
		botonInsertar.addActionListener(
			new ActionListener()
			{
				public void actionPerformed( ActionEvent evt )
				{
					botonInsertarActionPerformed( evt );
					try {
				    	Calendar fecha = Calendar.getInstance();
				    	int dia = fecha.get(Calendar.DAY_OF_MONTH) ;
				    	int mes = fecha.get(Calendar.MONTH) + 1;
				    	int anio = fecha.get(Calendar.YEAR);
				    	String mesFecha = dia+"/"+ mes+"/" +anio;
						mostrarResultadosConsulta = new MostrarResultadosConsulta();
						mostrarResultadosConsulta.modeloTabla.consultarGasto(mesFecha);
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}

					setVisible(false);
				}
			}
		);

	}
	
	private void botonInsertarActionPerformed( ActionEvent evt ) 
	{
		
		int resultado = resultSetTableModel.insertarGasto(formateador.format(dateChooser.getDate()), campoCategoria.getSelectedItem().toString(), campoDescripcion.getText(), campoValor.getText());
	
		if ( resultado == 1 ) {
			JOptionPane.showMessageDialog( this, "Se agrego gasto!","Se agrego gasto", JOptionPane.PLAIN_MESSAGE );

		}else {
			JOptionPane.showMessageDialog( this, "No se agrego registro!","Error", JOptionPane.PLAIN_MESSAGE );
		}
	}
	
}
	