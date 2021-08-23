package com.presupuesto.controlador;

//Un objeto TableModel que suministra datos ResultSet a un objeto JTable.
import java.sql.Connection;
import java.sql.Statement;
import java.util.Calendar;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

public class ResultSetTableModel extends AbstractTableModel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  Connection conexion;
	private Statement instruccion;
	private ResultSet conjuntoResultados;
	private ResultSetMetaData metaDatos;
	private int numeroDeFilas;
	
	private boolean conectadoABaseDatos = false;
	private PreparedStatement insertarNuevoGasto = null;
	private PreparedStatement insertarNuevoIngreso = null;
	private PreparedStatement consultaTotalesTabla = null;
	private PreparedStatement consultaTotalIngresos = null;
	private PreparedStatement consultaTotalGastos = null;
	
	static final String CONTROLADOR   = "oracle.jdbc.driver.OracleDriver";
	static final String URL_BASEDATOS = "jdbc:oracle:thin:@localhost:1521:orcl";
	static final String NOMBREUSUARIO = "traiding_db";
	static final String CONTRASENIA   = "123456";

    public ResultSetTableModel(String tipoConsulta) throws SQLException, ClassNotFoundException {
    	
    	Class.forName( CONTROLADOR );
    	conexion = DriverManager.getConnection( URL_BASEDATOS, NOMBREUSUARIO, CONTRASENIA );
    	
    	Calendar dato = Calendar.getInstance();
    	int dia  = dato.get(Calendar.DAY_OF_MONTH) ;
    	int mes  = dato.get(Calendar.MONTH) + 1;
    	int anio = dato.get(Calendar.YEAR);
    	
    	String fecha = dia+"/"+ mes+"/" +anio;

    	instruccion = conexion.createStatement( 
    			ResultSet.TYPE_SCROLL_INSENSITIVE,
    			ResultSet.CONCUR_READ_ONLY );
    	
    	conectadoABaseDatos = true;
     
    	if(tipoConsulta == "GASTO") {
    		consultarGasto(fecha);
    	}else if(tipoConsulta == "INGRESO") {
    		consultarIngreso(fecha);
    	}else if(tipoConsulta == "DETALLES") {
    		consultarDetalles();
    	}
	} 
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass( int columna ) throws IllegalStateException{
	
		if ( !conectadoABaseDatos ) 
			throw new IllegalStateException( "No hay conexion a la base de datos" );	 
		try
		{
			String nombreClase = metaDatos.getColumnClassName( columna + 1 );	 
			return Class.forName( nombreClase );
		} 
		catch ( Exception excepcion ) 
		{
			excepcion.printStackTrace();
		} 
	 
		return Object.class; 
	} 
	 

	public int getColumnCount() throws IllegalStateException{

		 if ( !conectadoABaseDatos ) 
			 throw new IllegalStateException( "No hay conexión a la base de datos" );	 
		 try
		 {
			 return metaDatos.getColumnCount();	 
		 } 
		 	catch ( SQLException excepcionSql ) 
		 {
		 	excepcionSql.printStackTrace();
		 }

		 return 0; 
	 }
	 

	 public String getColumnName( int columna ) throws IllegalStateException {

		 if ( !conectadoABaseDatos ) 
			 throw new IllegalStateException( "No hay conexion a la base de datos" );
		 try
		 {
			 return metaDatos.getColumnName( columna + 1 ); 
		 } 
		 catch ( SQLException excepcionSql ) 
		 {
			 excepcionSql.printStackTrace();
		 } 
	
		 return ""; 
	 } 
	 
	 
	 public int getRowCount() throws IllegalStateException{ 

		 if ( !conectadoABaseDatos ) 
			 throw new IllegalStateException( "No hay conexion a la base de datos" );

		 return numeroDeFilas;
	 } 


	 public Object getValueAt( int fila, int columna )  throws IllegalStateException{

		 if ( !conectadoABaseDatos ) 
			 throw new IllegalStateException( "No hay conexion a la base de datos" );
		 
		 try{
			 conjuntoResultados.absolute( fila + 1 );
			 return conjuntoResultados.getObject( columna + 1 );
		 }
		 catch ( SQLException excepcionSql ) 
		 {
			 excepcionSql.printStackTrace();
		 
		 }
	 
		 return ""; 
	 } 
	 
	 public void consultarGasto(String mes) throws SQLException, IllegalStateException {

		 String consulta = "SELECT CATEGORIA\r\n"
					+ "           ,DESCRIPCION\r\n"
					+ "           ,TRIM(TO_CHAR(VALOR, '$999,999,999')) as VALOR \r\n"
					+ "       FROM INFO_GASTOS\r\n"
					+ "      WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(TO_DATE("+ "'" + mes + "'" + "),'DD/MM/YYYY')"
					+ "      ORDER BY FECHA, CATEGORIA";
		 
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );

		 conjuntoResultados = instruccion.executeQuery( consulta );
		 metaDatos = conjuntoResultados.getMetaData();
		 conjuntoResultados.last(); 
		 numeroDeFilas = conjuntoResultados.getRow();
	 
		 fireTableStructureChanged();
	 }
	 
	 public void consultarIngreso(String mes) throws SQLException, IllegalStateException {

		 String consulta = "SELECT CATEGORIA\r\n"
					+ "           ,DESCRIPCION\r\n"
					+ "           ,TRIM(TO_CHAR(VALOR, '$999,999,999')) as VALOR \r\n"
					+ "       FROM INFO_INGRESOS\r\n"
					+ "      WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(TO_DATE("+ "'" + mes + "'" + "),'DD/MM/YYYY')"
					+ "      ORDER BY FECHA, CATEGORIA";
		 
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );

		 conjuntoResultados = instruccion.executeQuery( consulta );
		 metaDatos = conjuntoResultados.getMetaData();
		 conjuntoResultados.last(); 
		 numeroDeFilas = conjuntoResultados.getRow();
	 
		 fireTableStructureChanged();
	 }
	 
	 public void consultarDetalles() throws SQLException, IllegalStateException {

		 String consulta =  " SELECT CATEGORIA, "
				            +"       TRIM(TO_CHAR(SUM(SC.VALOR), '$999,999,999')) TOTAL_GRUPO, "
				            +"       ROUND(100*(SUM(SC.VALOR) / SUM(SUM(SC.VALOR)) OVER ()),2) PORCENTAJE "
				            +"  FROM INFO_GASTOS SC "
				            +" GROUP BY SC.CATEGORIA"
				            +" ORDER BY PORCENTAJE DESC";
	 
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );

		 conjuntoResultados = instruccion.executeQuery( consulta );
		 metaDatos = conjuntoResultados.getMetaData();
		 conjuntoResultados.last(); 
		 numeroDeFilas = conjuntoResultados.getRow();
	 
	 }
	 
	 public String consultarTotalesIngresoGasto(String mes, String tipoConsulta) throws SQLException, IllegalStateException {
		 String consulta = null;
		 if (tipoConsulta == "GASTO") {
			 consulta = "SELECT TRIM(TO_CHAR(SUM(VALOR), '$999,999,999')) as VALOR \r\n"
				 + "       FROM INFO_GASTOS\r\n"
				 + "      WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(TO_DATE("+ "'" + mes + "'" + "),'DD/MM/YYYY')"
				 + "      ORDER BY FECHA, CATEGORIA";
		 }else if(tipoConsulta == "INGRESO") {
			 consulta = "SELECT TRIM(TO_CHAR(SUM(VALOR), '$999,999,999')) as VALOR \r\n"
				 + "       FROM INFO_INGRESOS\r\n"
				 + "      WHERE TO_CHAR(FECHA,'DD/MM/YYYY') = TO_CHAR(TO_DATE("+ "'" + mes + "'" + "),'DD/MM/YYYY')"
				 + "      ORDER BY FECHA, CATEGORIA";
		 }		 
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );
		 
		 consultaTotalesTabla = conexion.prepareStatement(consulta);
		 ResultSet rs = consultaTotalesTabla.executeQuery();
		 rs.next();
		 
		 String resultado;
		 if(rs.getString("VALOR") == null) {
			 resultado = "0";
		 }else {
			 resultado = rs.getString("VALOR");
		 }
		 
		 return resultado;
	 }
	 
	 public String consultarTotalIngresos() throws SQLException, IllegalStateException {
		 String consulta = "SELECT TRIM(TO_CHAR(SUM(VALOR), '$999,999,999')) as VALOR \r\n"
			 + "       FROM INFO_INGRESOS\r\n";
	 	 
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );
		 
		 consultaTotalIngresos = conexion.prepareStatement(consulta);
		 ResultSet rs = consultaTotalIngresos.executeQuery();
		 rs.next();
		 
		 String resultado;
		 if(rs.getString("VALOR") == null) {
			 resultado = "0";
		 }else {
			 resultado = rs.getString("VALOR");
		 }
		 
		 return resultado;
	 }
	 
	 public String consultarTotalGastos() throws SQLException, IllegalStateException {
		 String consulta = "SELECT TRIM(TO_CHAR(SUM(VALOR), '$999,999,999')) as VALOR \r\n"
			 + "       FROM INFO_GASTOS\r\n";
	 	 
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );
		 
		 consultaTotalGastos = conexion.prepareStatement(consulta);
		 ResultSet rs = consultaTotalGastos.executeQuery();
		 rs.next();
		 
		 String resultado;
		 if(rs.getString("VALOR") == null) {
			 resultado = "0";
		 }else {
			 resultado = rs.getString("VALOR");
		 }
		 
		 return resultado;
	 }
	 
	 public String consultarDifIngresoGasto() throws SQLException, IllegalStateException {
		 String consulta =  "SELECT TRIM(TO_CHAR((SELECT SUM(VALOR) "
				 +"          FROM INFO_INGRESOS) -"
				 +"       (SELECT SUM(VALOR) "
				 +"          FROM INFO_GASTOS "
				 +"       ), '$999,999,999.00')) AS VALOR"
				 +"  FROM DUAL";
		 if ( !conectadoABaseDatos )
			 throw new IllegalStateException( "No hay conexion a la base de datos" );
		 
		 consultaTotalGastos = conexion.prepareStatement(consulta);
		 ResultSet rs = consultaTotalGastos.executeQuery();
		 rs.next();
		 
		 String resultado;
		 if(rs.getString("VALOR") == null) {
			 resultado = "0";
		 }else {
			 resultado = rs.getString("VALOR");
		 }
		 
		 return resultado;
	 }
	 
	 public int insertarGasto( String string, String categoria, String descripcion, String string2 ){
		 	 
		 int resultado = 0;		 			
		 String insertarGasto = "INSERT INTO INFO_GASTOS " + 
					            "(ID_GASTOS, FECHA, CATEGORIA, DESCRIPCION, VALOR ) " + 
					            "VALUES ( SEQ_INFO_IDGASTOS.nextval, ?, ?, ?, ? )";
		 
		 try{
			 
			 insertarNuevoGasto = conexion.prepareStatement(insertarGasto);
			 
			 insertarNuevoGasto.setString( 1, string.toString() );
			 insertarNuevoGasto.setString( 2, categoria );
			 insertarNuevoGasto.setString( 3, descripcion );
			 insertarNuevoGasto.setString( 4, string2.toString() );
			  
			 resultado = insertarNuevoGasto.executeUpdate();  
		 } 
		 catch ( SQLException excepcionSql )
		 {
			 excepcionSql.printStackTrace();
			 desconectarDeBaseDatos();
		 } 
		 
		 return resultado;			 
	 }
	 
	 public int insertarIngreso( String string, String categoria, String descripcion, String string2 ){
	 	 
		 int resultado = 0;		 			
		 String insertarIngreso = "INSERT INTO INFO_INGRESOS " + 
					            "(ID_INGRESO, FECHA, CATEGORIA, DESCRIPCION, VALOR ) " + 
					            "VALUES (SEQ_INFO_IDINGRESO.nextval, ?, ?, ?, ? )";
		 
		 try{
			 
			 insertarNuevoIngreso = conexion.prepareStatement(insertarIngreso);
			 
			 insertarNuevoIngreso.setString( 1, string.toString() );
			 insertarNuevoIngreso.setString( 2, categoria );
			 insertarNuevoIngreso.setString( 3, descripcion );
			 insertarNuevoIngreso.setString( 4, string2.toString() );
			  
			 resultado = insertarNuevoIngreso.executeUpdate();  
		 } 
		 catch ( SQLException excepcionSql )
		 {
			 excepcionSql.printStackTrace();
			 desconectarDeBaseDatos();
		 } 
		 
		 return resultado;			 
	 } 
			 
	 public void desconectarDeBaseDatos(){
		 if ( conectadoABaseDatos ){
			 try
			 {
				 conjuntoResultados.close();
				 instruccion.close();
				 conexion.close();
			 }
			 catch ( SQLException excepcionSql )
			 {
				 excepcionSql.printStackTrace();
			 } 
			 finally
			 {
				 conectadoABaseDatos = false;
			 }
		 }
	 }

}
