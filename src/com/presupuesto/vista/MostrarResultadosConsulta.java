package com.presupuesto.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

import com.presupuesto.controlador.ResultSetTableModel;
import com.toedter.calendar.JCalendar;


public class MostrarResultadosConsulta extends JFrame 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ResultSetTableModel modeloTabla;
	private JCalendar calendar;
	static String[] listaMeses = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
	public MostrarPanelSur mostrarPanelSur;
	public MostrarPanelNorte mostrarPanelNorte;
	public MostrarPanelCentral mostrarPanelCentral;
	public JTable tablaResultados;
	public JPanel panelNorte;

	public MostrarResultadosConsulta() throws ClassNotFoundException, SQLException 
	{
		super( "Gestion del presupuesto Mensual" );

		try
		{
			modeloTabla = new ResultSetTableModel("GASTO");
			
	    	Calendar dato = Calendar.getInstance();
	    	int dia  = dato.get(Calendar.DAY_OF_MONTH) ;
	    	int mes  = dato.get(Calendar.MONTH) + 1;
	    	int anio = dato.get(Calendar.YEAR);
			Month mesNorte = LocalDate.now().getMonth();
			String nombreMes = mesNorte.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));		
	    	String fecha = dia+"/"+ mes+"/" +anio;
			String fechaCompleta = dia + " DE " + nombreMes + " DEL " + anio;
		
			Border raisedbevel, loweredbevel;
			raisedbevel = BorderFactory.createRaisedBevelBorder();
			loweredbevel = BorderFactory.createLoweredBevelBorder();
			Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);		
			Border redline = BorderFactory.createLineBorder(new java.awt.Color(255,98,9));
			compound = BorderFactory.createCompoundBorder(redline, compound);
					
			//INICIO CONTRUCCION DE PANEL NORTE
			panelNorte =new MostrarPanelNorte(fechaCompleta);
			//FIN CONTRUCCION DE PANEL NORTE
			
			//INICIO CONSTRUCCION DEL PANEL SUR		
			mostrarPanelSur = new MostrarPanelSur(fecha, "GASTO");
			//FIN CONSTRUCCION DEL PANEL SUR
						
			//INICIO CONTRUCCION DEL PANEL CENTRAL 
			mostrarPanelCentral = new MostrarPanelCentral();
			tablaResultados = mostrarPanelCentral.MostrarPanelCentral(modeloTabla);
			//FIN CONTRUCCION DEL PANEL CENTRAL 
			
			//INICIO CONTRUCCION DE PANEL ORIENTE
			JPanel panelOriente = new JPanel();
			Box boxOriente = Box.createVerticalBox();		
						
			JPanel panelInsertar = new JPanel();
			panelInsertar.setBorder(BorderFactory.createTitledBorder("Insertar Registros"));
			Box box = Box.createVerticalBox();			
			JButton botonNuevoRegistro= new JButton("Insertar Nuevo Gasto");
			botonNuevoRegistro.setBackground(Color.black);
			botonNuevoRegistro.setForeground(Color.white);
			botonNuevoRegistro.setBorder(compound);
			botonNuevoRegistro.setMaximumSize(new Dimension(200,50));
			botonNuevoRegistro.setPreferredSize(new Dimension(150,30));
			JButton botonNuevoIngreso = new JButton("Insertar Nuevo Ingreso");
			botonNuevoIngreso.setBackground(Color.black);
			botonNuevoIngreso.setForeground(Color.white);
			botonNuevoIngreso.setBorder(compound);
			botonNuevoIngreso.setMaximumSize(new Dimension(200,50));
			botonNuevoIngreso.setPreferredSize(new Dimension(150,30));
			box.add(botonNuevoRegistro);
			box.add(botonNuevoIngreso);
			panelInsertar.add(box);
			
			JPanel panelInformacion = new JPanel();
			panelInformacion.setBorder(BorderFactory.createTitledBorder("Información"));
			Box boxInformacion = Box.createVerticalBox();	
			JButton botonInformacion= new JButton("Detalles Presupuesto");
			botonInformacion.setBackground(Color.black);
			botonInformacion.setForeground(Color.white);
			botonInformacion.setBorder(compound);
			botonInformacion.setMaximumSize(new Dimension(200,50));
			botonInformacion.setPreferredSize(new Dimension(150,30));
			boxInformacion.add(botonInformacion);
			panelInformacion.add(boxInformacion);
			
			boxOriente.add(panelInsertar);
			boxOriente.add(panelInformacion);
			
			panelOriente.add(boxOriente);
			//FIN CONTRUCCION DE PANEL ORIENTE

			//INICIO CONSTRUCCION DEL PANEL OCCIDENTE 
			JPanel panelOccidente = new JPanel();	
			Box boxOccidente = Box.createVerticalBox();	
								
			JPanel panelFiltro = new JPanel();
			panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtros por Fecha"));
			Box boxFiltro = Box.createVerticalBox();			
			JPanel panelBotonFiltro = new JPanel();	
			calendar = new JCalendar();			
			JButton botonIngresos = new JButton("Ingresos");
			botonIngresos.setBackground(Color.black);
			botonIngresos.setForeground(Color.white);
			botonIngresos.setBorder(compound);	
			botonIngresos.setMaximumSize(new Dimension(200,50));
			botonIngresos.setPreferredSize(new Dimension(100,30));
			JButton botonGastos = new JButton("Gastos");
			botonGastos.setBackground(Color.black);
			botonGastos.setForeground(Color.white);
			botonGastos.setBorder(compound);
			botonGastos.setMaximumSize(new Dimension(200,50));
			botonGastos.setPreferredSize(new Dimension(100,30));
			panelBotonFiltro.add(botonIngresos);
			panelBotonFiltro.add(botonGastos);
			boxFiltro.add(calendar);
			boxFiltro.add(panelBotonFiltro);		
			panelFiltro.add(boxFiltro);	
			
			JPanel panelPresupuesto = new JPanel();
			panelPresupuesto.setBorder(BorderFactory.createTitledBorder("Totales"));
			panelPresupuesto.setLayout( new GridLayout( 3, 1, 0, 0) );
			
			String valorTotalIngresos = modeloTabla.consultarTotalIngresos();
			String valorTotalGastos = modeloTabla.consultarTotalGastos();
			String valorDif = modeloTabla.consultarDifIngresoGasto();
			JPanel panelActivos = new JPanel();
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
			panelPresupuesto.add(patrimonio);
			
			boxOccidente.add(panelFiltro);	
			boxOccidente.add(panelPresupuesto);
			panelOccidente.add(boxOccidente);
			//FIN CONSTRUCCION DEL PANEL OCCIDENTE 
		
			//SE ADICIONA LOS ELEMENTOS AL FRAME
			add( panelNorte , BorderLayout.NORTH);
			add( panelOriente , BorderLayout.WEST);
			add( new JScrollPane( tablaResultados ), BorderLayout.CENTER);
			add( mostrarPanelSur, BorderLayout.SOUTH);
			add( new JScrollPane( panelOccidente ), BorderLayout.EAST);
					
			botonIngresos.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed( ActionEvent e ) 
					{
						int dia = (calendar.getCalendar().get(Calendar.DAY_OF_MONTH));
				    	int mes = (calendar.getCalendar().get(Calendar.MONTH) + 1);
				    	int anio = (calendar.getCalendar().get(Calendar.YEAR));
				    	String fecha = dia+"/"+ mes+"/" +anio;
						String fechaCompleta = dia + " DE " + listaMeses[mes-1] + " DEL " + anio;
				    	
						try {
							panelNorte.removeAll();
							panelNorte = new MostrarPanelNorte(fechaCompleta);
							add( panelNorte, BorderLayout.NORTH);
							SwingUtilities.updateComponentTreeUI(panelNorte);
							
							modeloTabla.consultarIngreso(fecha);
							tablaResultados =  mostrarPanelCentral.MostrarPanelCentral(modeloTabla);
							SwingUtilities.updateComponentTreeUI(tablaResultados);
							
							mostrarPanelSur.removeAll();
							mostrarPanelSur = new MostrarPanelSur(fecha, "INGRESO");
							add( mostrarPanelSur, BorderLayout.SOUTH);
							SwingUtilities.updateComponentTreeUI(mostrarPanelSur);
																						    		    	
						} catch (IllegalStateException | SQLException | ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}			
			);

			botonGastos.addActionListener(
				new ActionListener() 
				{

					public void actionPerformed( ActionEvent e ) 
					{
						int dia = (calendar.getCalendar().get(Calendar.DAY_OF_MONTH));
				    	int mes = (calendar.getCalendar().get(Calendar.MONTH) + 1);
				    	int anio = (calendar.getCalendar().get(Calendar.YEAR));
				    	String fecha = dia+"/"+ mes+"/" +anio;
						String fechaCompleta = dia + " DE " + listaMeses[mes-1] + " DEL " + anio;
						
						try {
							panelNorte.removeAll();
							panelNorte = new MostrarPanelNorte(fechaCompleta);
							add( panelNorte, BorderLayout.NORTH);
						    SwingUtilities.updateComponentTreeUI(panelNorte);
							
							modeloTabla.consultarGasto(fecha);
							tablaResultados =  mostrarPanelCentral.MostrarPanelCentral(modeloTabla);
							SwingUtilities.updateComponentTreeUI(tablaResultados);
							
							mostrarPanelSur.removeAll();
							mostrarPanelSur = new MostrarPanelSur(fecha, "GASTO");
							add( mostrarPanelSur, BorderLayout.SOUTH);
							SwingUtilities.updateComponentTreeUI(mostrarPanelSur);
							
						} catch (IllegalStateException | SQLException | ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}			
			);
				
			botonNuevoRegistro.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed( ActionEvent e ) 
					{
						try {
							new MostrarVentanaGastos();			
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				}					
			);
			
			botonInformacion.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed( ActionEvent e ) 
					{
						//verificarDatos();		
						try {
							new MostrarDetallePresupuesto();
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				}					
			);
							
			botonNuevoIngreso.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed( ActionEvent e ) 
					{
						try {
							new MostrarVentanaIngresos();								
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				}					
			);
						
			setSize(1000,500);
			setLocationRelativeTo(null);
			setVisible( true );
			
		} 
		catch ( ClassNotFoundException noEncontroClase ) 
		{
			JOptionPane.showMessageDialog( null,
			"No se encontro controlador de base de datos", "No se encontro el controlador",
			JOptionPane.ERROR_MESSAGE );

			System.exit( 1 ); 
		}
		catch ( SQLException excepcionSql ) 
		{
			JOptionPane.showMessageDialog( null, excepcionSql.getMessage(), 
			"Error en base de datos", JOptionPane.ERROR_MESSAGE );

			modeloTabla.desconectarDeBaseDatos();

			System.exit( 1 );
		}

		setDefaultCloseOperation( DISPOSE_ON_CLOSE );

		addWindowListener(

			new WindowAdapter() 
			{
				public void windowClosed( WindowEvent evento )
				{
					modeloTabla.desconectarDeBaseDatos();
					System.exit( 0 );
				}
			} 
		);
	}
	
	public void actualizarComponentes() {
		SwingUtilities.updateComponentTreeUI(this);
	}
	
    public void verificarDatos() {

        TableModel tablaModelo;
        tablaModelo = (TableModel) tablaResultados.getModel();

        boolean avanzar = true;

        int registro = tablaResultados.getSelectedRow();
        int columna = tablaResultados.getSelectedColumn();


        if (registro == -1) {
            avanzar = false;
        } else if (columna == -1) {
            avanzar = false;
        }

        if (avanzar) {
            String strResultado = tablaModelo.getValueAt(
            		tablaResultados.getSelectedRow(),
            		tablaResultados.getSelectedColumn()).toString();
            JOptionPane.showMessageDialog(null, "Dato seleccionado : " + strResultado);
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un registro");
        }
    }

	public static void main( String args[] ) throws ClassNotFoundException, SQLException 
	{
		new MostrarResultadosConsulta();
	} 
	
}