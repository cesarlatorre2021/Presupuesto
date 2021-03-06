package com.presupuesto.vista;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

public class MostrarVentanaIngresos extends JFrame {
	
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
	private JDateChooser dateChooser;
	private JComboBox<Object> campoCategoria;
	private SimpleDateFormat formateador ;
	public MostrarResultadosConsulta mostrarResultadosConsulta;

	public MostrarVentanaIngresos() throws ClassNotFoundException, SQLException
	{
		super( "Insertar Ingreso" ); 
		
		String[] listaCategorias = { "Salario", "Inversión", "Devoluciones", "Otros"};
		resultSetTableModel = new ResultSetTableModel("INGRESO"); 
		Date dato = new Date();
	    formateador = new SimpleDateFormat("dd/MM/yyyy");

		JPanel panelIngreso = new JPanel();
		Box boxIngreso =Box.createVerticalBox();
		
		panelMostrar = new JPanel();
		panelMostrar.setBorder(BorderFactory.createTitledBorder("Insertar Nuevo Ingreso"));
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
		botonInsertar.setText( "Insertar Ingreso" );

		panelIngreso.add(panelMostrar);
		panelBoton.add(botonInsertar);
		
		boxIngreso.add(panelIngreso);
		boxIngreso.add(panelBoton);
		
		add(boxIngreso);
			
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
						mostrarResultadosConsulta.modeloTabla.consultarIngreso(mesFecha);
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
		
		int resultado = resultSetTableModel.insertarIngreso( formateador.format(dateChooser.getDate()), campoCategoria.getSelectedItem().toString(), campoDescripcion.getText(), campoValor.getText());
	
		if ( resultado == 1 ) {
			JOptionPane.showMessageDialog( this, "Se agrego ingreso!","Se agrego ingreso", JOptionPane.PLAIN_MESSAGE );

		}else {
			JOptionPane.showMessageDialog( this, "No se agrego ingreso!","Error", JOptionPane.PLAIN_MESSAGE );
		}
	}

}
