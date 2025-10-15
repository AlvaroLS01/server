package com.comerzzia.core.basketcalculator.util.fechas;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Fecha implements Comparable<Fecha> { 

	/** Patrón para fechas de tipo dd/MM/yyyy */
	public final static String PATRON_FECHA_CORTA = "dd/MM/yyyy";
	
	/** Patrón para fechas de tipo dd/MM/yyyy - HH:mm */
	public final static String PATRON_FECHA_HORA = "dd/MM/yyyy - HH:mm";
	
	/** Patrón para fechas de tipo dd/MM/yyyy - HH:mm:ss */
	public final static String PATRON_FECHA_HORA_SEG = "dd/MM/yyyy - HH:mm:ss";

	/** Patrón para fechas de tipo yyyyMMdd_HHmmss */
	public final static String PATRON_MARCA_TIEMPO = "yyyyMMdd_HHmmss";
	
	private Date date;
	
	/** Constructor vacío. Inicializa la fecha con el día de hoy. */
	public Fecha() {
		date = new Date();
	}
	
	/** Devuelve una nueva instancia Fecha partir del date indicado. Si el parámetro Date 
	 * es null, se devuelve NULL.
	 * @param date
	 * @return
	 */
	public static Fecha getFecha(Date date){
		return getFecha(date, null);
	}

	/** Devuelve una nueva instancia Fecha partir del date indicado. Si el parámetro Date 
	 * es null, se devuelve el defaultValue indicado.
	 * @param date
	 * @param defaultValue
	 * @return
	 */
	public static Fecha getFecha(Date date, Fecha defaultValue){
		if (date != null){
			return new Fecha(date);
		}
		return defaultValue;
	}

	
	/** Devuelve una nueva instancia Fecha partir del String indicado (formato dd/MM/yyyy). Si la cadena es NULL
	 * o vacía, o con formato incorrecto, se devuelve null.
	 * @param date
	 * @return
	 */
	public static Fecha getFecha(String date){
		if (date != null && !date.isEmpty()){
			Fecha fecha = new Fecha(date);
			if (fecha.getDate()!=null){
				return fecha;
			}
		}
		return null;
	}

	
	public static Fecha getFecha(String date, String patron){
		if (date != null && !date.isEmpty()){
			Fecha fecha = new Fecha(date, patron);
			if (fecha.getDate()!=null){
				return fecha;
			}
		}
		return null;
	}
	
	
	/** Devuelve una nueva instancia Fecha partir del String indicado (formato dd/MM/yyyy). Si la cadena es NULL
	 * o vacía, o con formato incorrecto, se devuelve el defaultValue indicado.
	 * @param date
	 * @param defaultValue
	 * @return
	 */
	public static Fecha getFecha(String date, Fecha defaultValue){
		Fecha fecha = getFecha(date);
		if (fecha == null){
			return defaultValue;
		}
		return fecha;
	}	
	

	/** Devuelve una nueva instancia Fecha partir del String indicado (formato dd/MM/yyyy). Si la cadena es NULL
	 * o vacía, o con formato incorrecto, se devuelve null.
	 * @param date
	 * @param hora
	 * @return
	 */
	public static Fecha getFechaHora(String date, String hora){
		if (date != null && !date.isEmpty()){
			Fecha fecha = new Fecha(date + " - " + hora, PATRON_FECHA_HORA);
			if (fecha.getDate()!=null){
				return fecha;
			}
		}
		return null;
	}
	
	/** Devuelve una nueva instancia Fecha partir del String indicado (formato dd/MM/yyyy). Si la cadena es NULL
	 * o vacía, o con formato incorrecto, se devuelve el defaultValue indicado.
	 * @param date
	 * @param defaultValue
	 * @return
	 */
	public static Fecha getFechaHora(String date, String hora, Fecha defaultValue){
		Fecha fecha = getFechaHora(date, hora);
		if (fecha == null){
			return defaultValue;
		}
		return fecha;
	}		
	
	
	
	/** Constructor a partir de una cadena. Parsea la cadena indicada y construye una fecha. Si se produce algún error
	 * de parseo, la fecha se queda a null. Internamente se llama al constructor Fecha(String fecha, String patron)
	 * con patron PATRON_FECHA_CORTA
	 * @param fecha Cadena con una fecha en formato PATRON_FECHA_CORTA
	 */
	public Fecha(String fecha){
		date = getDate(fecha, PATRON_FECHA_CORTA);
	}

	/** Constructor a partir de una cadena. Parsea la cadena indicada y construye una fecha utilizando el patrón indicado.
	 * Si se produce algún error de parseo, la fecha se queda a null;
	 * @param fecha Cadena con una fecha en formato correcto según el patrón indicado
	 * @param patrón Cadena con un patrón utilizado para el parseo. 
	 */
	public Fecha(String fecha, String patron){
		date = getDate(fecha, patron);
	}

	/** Constructor a partir de un date. 
	 * @param date Objeto de tipo Date con el que se inicializará la fecha
	 */
	public Fecha(Date date) {
		this.date = date;
	}

	/** Método estático que valida una cadena que representa una fecha utilizando el patrón PATRON_FECHA_CORTA.
	 * Internamente llama al método validarFecha(String fecha, String patron) con patrón PATRON_FECHA_CORTA.
	 * @param fecha Cadena que representa una fecha
	 * @return Boolean indicando si la fecha es válida o no
	 */
	public static boolean validarFecha(String fecha){
        return validarFecha(fecha, PATRON_FECHA_CORTA);
	}

	/** Método estático que valida una cadena que representa una fecha utilizando el patrón indicado.
	 * Se indica a la case formateadora "SimpleDateFormat" que se ciña a una fecha estricta y no sume días
	 * si el número de días es mayor para el mes indicado.
	 * @param fecha Cadena que representa una fecha
	 * @param patron Patrón utilizado para la validación de la fecha
	 * @return Boolean indicando si la fecha es válida o no
	 */
	public static boolean validarFecha(String fecha, String patron){
        if (fecha != null && fecha.length()> 0){
	        try {
	            DateFormat df = new SimpleDateFormat(patron, Locale.getDefault());
	            df.setLenient(false);
	            df.parse(fecha);
	        }
	        catch (ParseException e) {
	        	return false;
	        }
        }
        return true;
	}

	
	/** Devuelve la fecha actual como un objeto Date. */
	public Date getDate() {
		return date;
	}

	/** Cambia la fecha actual por la indicada.*/
	public void setDate(Date date) {
		this.date = date;
	}
	
	/** Devuelve una cadena con la representación de la fecha actual utilizando el patrón PATRON_FECHA_CORTA. */ 
	public String getString(){
		return getString(PATRON_FECHA_CORTA);
	}

	/** Devuelve una cadena con la representación de la fecha actual utilizando el patrón PATRON_FECHA_HORA. */ 
	public String getStringHora(){
		return getString(PATRON_FECHA_HORA);
	}

	/** Devuelve una cadena con la representación de la fecha actual utilizando el patrón PATRON_FECHA_HORA_SEG. */ 
	public String getStringHoraSeg(){
		return getString(PATRON_FECHA_HORA_SEG);
	}

	/** Devuelve una cadena con la representación de la fecha actual utilizando el patrón PATRON_MARCA_TIEMPO. */ 
	public String getStringMarcaTiempo(){
		return getString(PATRON_MARCA_TIEMPO);
	}
	
	/** Devuelve una cadena con la representación de la fecha actual utilizando el patrón indicado. */
	public String getString(String patron){
        Format formatter = new SimpleDateFormat(patron);
        if (date != null) {
            return formatter.format(date);
        }
       return null;
	}

	public java.sql.Date getSQL() {
		return new java.sql.Date(date.getTime());
	}
	
	public Integer getDia(){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/** Este método ha sido sustituido por getMesNumero() que devuelve el 
	 * mes en un intervalo del 1 al 12, en lugar del 0 al 11.	 */
	@Deprecated 
	public Integer getMes(){ 
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}
	
	/** Devuelve el mes de la fecha en un valor del 1 al 12 */
	public Integer getMesNumero(){  
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH)+1;
	}

	public Integer getAño(){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	/** Devuelve el nombre del dia de la semana. */
	public String getNombreDia(){
		SimpleDateFormat formateador = new SimpleDateFormat("EEEE"); 
		return formateador.format(date);
	}
	/** Devuelve el nombre del dia de la semana. */
	public String getNombreDia(Locale locale){
		SimpleDateFormat formateador = new SimpleDateFormat("EEEE",locale); 
		return formateador.format(date);
	}
	
	/** Devuelve el nombre del mes. */
	public String getNombreMes(){
		SimpleDateFormat formateador = new SimpleDateFormat("MMMMM"); 
		return formateador.format(date);
		
	}
	/** Devuelve el nombre del mes. */
	public String getNombreMes(Locale locale){
		SimpleDateFormat formateador = new SimpleDateFormat("MMMMM", locale);
		return formateador.format(date);
	}
	
	public void sumaDias(int dias){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, dias);
		date = c.getTime();
	}
	public void sumaMeses(int meses){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, meses);
		date = c.getTime();
	}
	public void sumaAños(int años){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, años);
		date = c.getTime();
	}
	public void sumaHoras(int horas){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, horas);
		date = c.getTime();
	}
	
	public void sumaMinutos(int minutos){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutos);
		date = c.getTime();
	}
	
	public int diferenciaDias(Fecha fecha){
		Long ms = date.getTime() - fecha.getDate().getTime();
		return Math.abs(new Long(ms / 1000 / 60 / 60 / 24).intValue());
	}
	public boolean despues(Fecha fecha){
		return this.date.after(fecha.getDate());
	}
	public boolean antes(Fecha fecha){
		return this.date.before(fecha.getDate());
	}
	public boolean despuesOrEquals(Fecha fecha){
		return this.date.after(fecha.getDate()) || equals(fecha);
	}
	public boolean antesOrEquals(Fecha fecha){
		return this.date.before(fecha.getDate()) || equals(fecha);
	}

	/** Compara el parámetro pasado con la Fecha. La comparación se hace a través del objeto interno date, 
	 * de modo que el resultado será el mismo que this.date.equals(o.getDate()).
	 * El método acepta tanto un objeto Fecha como un objeto Date.	 */
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o instanceof Fecha){
			Fecha f = (Fecha)o;
			return this.date.equals(f.getDate());
		}
		if (o instanceof Date){
			return this.date.equals(o);
		}
		return false;
	}

	/** Método deprecated. Utilizar equalsFecha(Fecha f). Actualmente este método sólo hace un
	 * casting del parámetro pasado y llama al otro.	 */
	@Deprecated
	public boolean equalsFecha(Object o){
		Fecha f = (Fecha)o;
		return equalsFecha(f);
	}

	/** Devuelve true comparando únicamente fecha e ignorando la hora de ambos objetos. */
	public boolean equalsFecha(Fecha fecha){
		return fecha.getAño().equals(getAño()) && fecha.getMesNumero().equals(getMesNumero()) && fecha.getDia().equals(getDia());
	}
	
	/** Método deprecated. Utilizar equals() */
	@Deprecated
	public boolean equalsFechaHora(Fecha fecha){
		return equals(fecha);	
	}
	
	@Deprecated
	/** NO UTILIZAR */
	public boolean despuesOrEqualsFechaHora(Fecha fecha){
		return this.date.after(fecha.getDate()) || this.date.equals(fecha) ;	
	}

	@Deprecated
	/** NO UTILIZAR */
	public boolean antesOrEqualsFechaHora(Fecha fecha){
		return  this.date.before(fecha.getDate()) || this.date.equals(fecha);	
	}
	
	public String toString(){
		return getString();
	}
	public Timestamp getTimestamp(){
		return new Timestamp(getDate().getTime());
	}
	
	private Date getDate(String fecha, String patron){
        if (fecha != null && fecha.length()> 0){
	        try {
	            DateFormat df = new SimpleDateFormat(patron, Locale.getDefault());
	            df.setLenient(false);
	            return df.parse(fecha);
	        }
	        catch (ParseException e) {
	        }
        }
        return null;
	}


	@Override
	public int compareTo(Fecha fecha) {
		return getDate().compareTo(fecha.getDate());
	}

////	// test
//	public static void main(String[] args) {
//		Fecha f = new Fecha("12/12/2011");
//		Fecha f2 = new Fecha("17/12/2011");
//		System.out.println(f2.getNombreMes());
//		System.out.println(f.getMesNumero());
//				
//	}
	

}
