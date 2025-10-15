/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND
 * INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED,
 * DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST,
 * TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK
 * WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE
 * AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.util.format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.efaps.number2words.IConverter;
import org.efaps.number2words.converters.English;
import org.efaps.number2words.converters.German;
import org.efaps.number2words.converters.Spanish;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.AppConfig;

@Component
public class FormatUtil {

	// Log
	private static final Logger log = Logger.getLogger(FormatUtil.class.getName());

	// Instancia
	private static FormatUtil instance;

	// Formateadores
	private static DateFormat formateadorFechas;
	private static DateFormat formateadorHoras;

	private static DecimalFormat formateadorNumeros;
	private static DecimalFormat formateadorImportes;
	private static DecimalFormat formateadorMoneda;

	private static DateFormat dfTicket;
	
	private static IConverter formateadorTexto;

	private static final String SEPARADOR_FECHA_HORA = " - ";
	
	public static Currency getCurrency() {
		return formateadorMoneda.getCurrency();
	}

	/**
	 * Devuelve la instancia única de la clase
	 *
	 * @return
	 */
	public static FormatUtil getInstance() {
		if (instance == null) {
			instance = new FormatUtil();
		}
		return instance;
	}

	/**
	 * Inicializa los formateadores con los valores establecidos en la configuración
	 * 
	 * @param locale
	 *            Locale utilizado
	 */
	public void init(Locale locale) {
		// Formateadores de fechas y horas
		formateadorFechas = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		if (AppConfig.formatoFecha != null) {
			try {
				formateadorFechas = new SimpleDateFormat(AppConfig.formatoFecha, locale);
			}
			catch (IllegalArgumentException e) {
				log.error("El formato de fecha no es válido.");
			}
		}
		formateadorHoras = DateFormat.getTimeInstance(DateFormat.MEDIUM, locale);
		if (AppConfig.formatoHora != null) {
			try {
				formateadorHoras = new SimpleDateFormat(AppConfig.formatoHora, locale);
			}
			catch (IllegalArgumentException e) {
				log.error("El formato de hora no es válido.");
			}
		}

		// Formateadores especiales
		dfTicket = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale);

		DecimalFormatSymbols symbols = generateDecimalFormatSymbols(locale, false);
		
		// Formateador para números
		NumberFormat numberFormatNumeros = NumberFormat.getInstance(locale);
		formateadorNumeros = (DecimalFormat) numberFormatNumeros;
		formateadorNumeros.setParseBigDecimal(true);
		formateadorNumeros.setDecimalFormatSymbols(symbols);

		// Usamos una instancia de Currency para saber los dígitos decimales solamente porque si usasemos esta instancia
		// para formatear no permitiría modificar el separador decimal
		NumberFormat currrencyFormat = NumberFormat.getCurrencyInstance(locale);
		
		// Formateador para importes
		NumberFormat numberFormatImportes = NumberFormat.getInstance(locale);
		formateadorImportes = configureNumberFormatImportes(numberFormatImportes, currrencyFormat);
		formateadorImportes.setDecimalFormatSymbols(symbols);
		
		// Formateador para importes con moneda
		NumberFormat numberFormatMoneda = NumberFormat.getCurrencyInstance(locale);
		formateadorMoneda = configureNumberFormatImportes(numberFormatMoneda, currrencyFormat);
		DecimalFormatSymbols symbolsMoneda = generateDecimalFormatSymbols(locale, true);
		formateadorMoneda.setDecimalFormatSymbols(symbolsMoneda);
		
		formateadorTexto = inicializarFormateadorTexto(locale);

	}
	
	protected IConverter inicializarFormateadorTexto(Locale locale){
        IConverter converter = null;
        if(locale.getLanguage() != null){
        	if(locale.getLanguage().startsWith("de")){
        		 converter = new German();
        	}
        	else if(locale.getLanguage().startsWith("en")){
        		converter = new English();
        	}
        	else if(locale.getLanguage().startsWith("es")){
        		converter = new Spanish();
        	}
        }
        return converter;
    } 

	protected DecimalFormat configureNumberFormatImportes(NumberFormat numberFormatImportes, NumberFormat currrencyFormat) {
		DecimalFormat formateadorImportes = (DecimalFormat) numberFormatImportes;
		formateadorImportes.setMaximumFractionDigits(currrencyFormat.getMaximumFractionDigits());
		formateadorImportes.setMinimumFractionDigits(currrencyFormat.getMinimumFractionDigits());
		formateadorImportes.setRoundingMode(RoundingMode.HALF_UP);
		formateadorImportes.setParseBigDecimal(true);

		if (AppConfig.numeroDecimales != null) {
			formateadorImportes.setMaximumFractionDigits(AppConfig.numeroDecimales);
			formateadorImportes.setMinimumFractionDigits(AppConfig.numeroDecimales);
		}
		return formateadorImportes;
	}

	protected DecimalFormatSymbols generateDecimalFormatSymbols(Locale locale, boolean useCurrency) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		if (!useCurrency) {
			symbols.setCurrencySymbol("");
		}
		if (AppConfig.separadorDecimal != null || AppConfig.separadorGrupos != null) {

			if (AppConfig.separadorDecimal != null) {
				symbols.setDecimalSeparator(AppConfig.separadorDecimal.charAt(0));
			}

			if (AppConfig.separadorGrupos != null) {
				symbols.setGroupingSeparator(AppConfig.separadorGrupos.charAt(0));
			}
		}
		return symbols;
	}
	
	/**
	 * Formatea un número a cadena considerando que este número es un importe
	 * 
	 * @param importe
	 * @return
	 */
	public String formateaImporte(BigDecimal importe) {
		return formateadorImportes.format(importe);
	}

	/**
	 * Formatea un número a cadena considerando que este número es un importe con moneda
	 * 
	 * @param importe
	 * @return
	 */
	public String formateaImporteMoneda(BigDecimal importe) {
		return formateadorMoneda.format(importe);
	}

	/**
	 * Pasa a Bigdecimal un importe
	 * 
	 * @pre texto representa un BigDecimal
	 * @param texto
	 * @return
	 */
	public BigDecimal desformateaImporte(String importe) {
		BigDecimal res = null;
		ParsePosition pos = new ParsePosition(0);
		res = (BigDecimal) formateadorNumeros.parse(importe, pos);
		if(pos.getIndex() != importe.length() || pos.getErrorIndex() != -1){
			return null;
		}else{
			return res;
		}
	}

	/**
	 * Formatea un número a string sin decimales a 0 decimales
	 *
	 * @param numero
	 * @return
	 */
	public String formateaNumero(BigDecimal numero) {
		return formateaNumero(numero, 0);
	}

	/**
	 * Formatea un número a string con el número de decimales que queramos
	 *
	 * @param numero
	 * @param decimales
	 * @return
	 */
	public String formateaNumero(BigDecimal numero, Integer decimales) {
		if (decimales != null && decimales >= 0) {
			formateadorNumeros.setMinimumFractionDigits(decimales);
			formateadorNumeros.setMaximumFractionDigits(decimales);
		}
		else { // Si el número tiene decimales, debería estar configurado. En cualquier caso permitimoe entre 0 y 4
			   // decimales
			formateadorNumeros.setMinimumFractionDigits(0);
			formateadorNumeros.setMaximumFractionDigits(4);
		}
		return formateadorNumeros.format(numero);
	}
	
	public String formateaNumeroRedondeado(BigDecimal numero, Integer decimales) {
		return formateaNumero(numero.setScale(decimales, BigDecimal.ROUND_HALF_UP), decimales);
	}

	public String formateaNumeroSinSeparador(BigDecimal numero) {
		return numero.toString().replace(".", "");
	}

	/**
	 * Obtiene la representación para una fecha corta a parir de un date
	 * 
	 * @param date
	 * @return
	 */
	public String formateaFechaCorta(Date date) {
		return formateadorFechas.format(date);
	}

	/**
	 * Obtiene la representación para una fecha corta a partir de un date
	 * 
	 * @param date
	 * @return
	 */
	public String formateaFecha(Date date) {
		return formateadorFechas.format(date);
	}

	public String formateaFechaHoraTicket(Date date) {

		return dfTicket.format(date);
	}

	/**
	 * Obtiene la representación para una fecha corta a parir de un date
	 * 
	 * @param date
	 * @return
	 */
	public String formateaHora(Date date) {
		return formateadorHoras.format(date);
	}

	/**
	 * Obtiene la representación para una fecha corta a parir de un date
	 * 
	 * @param date
	 * @return
	 */
	public Date desformateaHora(String date) {
		try {
			return formateadorHoras.parse(date);
		}
		catch (ParseException ex) {
			log.error("desformateaFechaCorta()- Error desformateando fecha corta. El campo se ha de validar previamente. F: " + date);
		}
		return null;
	}

	/**
	 * Obtiene la representación para una fecha corta a parir de un date
	 * 
	 * @param date
	 * @return
	 */
	public String formateaFechaHora(Date date) {
		return formateadorFechas.format(date) + SEPARADOR_FECHA_HORA + formateadorHoras.format(date);
	}

	public Date desformateaFechaHora(String date) {
		return desformateaFechaHora(date, false);
	}
	
	/**
	 * Obtiene la representación para una fecha corta a partir de un date
	 * 
	 * @param date
	 * @return
	 */
	public Date desformateaFechaHora(String date, boolean horaActual) {
		try {
			Date fecha;
			Date hora;
			if(!horaActual){
				String[] aux = date.split(SEPARADOR_FECHA_HORA);
				fecha = formateadorFechas.parse(aux[0]);
				hora = formateadorHoras.parse(aux[1]);
			}else{
				fecha = desformateaFecha(date);
				if(fecha == null){
					return null;
				}
				Calendar calendarHora = Calendar.getInstance();
				calendarHora.setTime(new Date());
				hora = calendarHora.getTime();
			}

			Calendar calendarFecha = Calendar.getInstance();
			calendarFecha.setTime(fecha);
			Calendar calendarHora = Calendar.getInstance();
			calendarHora.setTime(hora);
			calendarFecha.set(Calendar.HOUR_OF_DAY, calendarHora.get(Calendar.HOUR_OF_DAY));
			calendarFecha.set(Calendar.MINUTE, calendarHora.get(Calendar.MINUTE));
			calendarFecha.set(Calendar.SECOND, calendarHora.get(Calendar.SECOND));

			return calendarFecha.getTime();
		}
		catch (ParseException ex) {
			log.error("desformateaFechaHora()- Error desformateando fecha corta. El campo se ha de validar previamente. F: " + date);
		}
		return null;
	}

	/**
	 * Parsea un string representando una fecha corta
	 * 
	 * @param date
	 *            string que representa la fecha en formato corto
	 * @return date
	 */
	public Date desformateaFechaCorta(String date) {
		ParsePosition pos = new ParsePosition(0);
		Date parse = formateadorFechas.parse(date, pos);
		if(pos.getIndex() != date.length() || pos.getErrorIndex() != -1){
			return null;
		}else{
			return parse;
		}
	}

	/**
	 * Parsea un string representando una fecha corta
	 * 
	 * @param date
	 *            string que representa la fecha en formato corto
	 * @return date
	 */
	public Date desformateaFecha(String date) {
		ParsePosition pos = new ParsePosition(0);
		Date parse = formateadorFechas.parse(date, pos);
		if(pos.getIndex() != date.length() || pos.getErrorIndex() != -1){
			return null;
		}else{
			return parse;
		}
	}

	public Date desformateaFechaHoraTicket(String date) {
		ParsePosition pos = new ParsePosition(0);
		Date parse = dfTicket.parse(date, pos);
		if(pos.getIndex() != date.length() || pos.getErrorIndex() != -1){
			return null;
		}else{
			return parse;
		}
	}

	/**
	 * Añade i espacios a la izquierda del texto y devuelve el resultado como string
	 * 
	 * @param texto
	 * @param espacios
	 * @return
	 */
	public String addSpacesLeft(String texto, int espacios) {
		String res = texto;
		for (int i = 0; i < espacios; i++) {
			res = " " + res;
		}
		return res;
	}

	/**
	 * Añade i espacios a la derecha del texto y devuelve el resultado como string
	 * 
	 * @param texto
	 * @param espacios
	 * @return
	 */
	public String addSpacesRight(String texto, int espacios) {
		String res = texto;
		for (int i = 0; i < espacios; i++) {
			res = res + " ";
		}
		return res;
	}

	/**
	 * Devuelve el Long que representa un texto
	 * 
	 * @pre texto representa un long
	 * @param texto
	 * @return
	 */
	public Long desformateaLong(String texto) {
		Long res = null;
		if (!texto.isEmpty()) {
			res = Long.parseLong(texto);
		}
		return res;
	}

	/**
	 * Pasa a Bigdecimal un valor numérico
	 * 
	 * @pre texto representa un BigDecimal
	 * @param texto
	 * @return
	 */
	public BigDecimal desformateaBigDecimal(String texto) {
		BigDecimal res = null;
		ParsePosition pos = new ParsePosition(0);
		res = (BigDecimal) formateadorNumeros.parse(texto, pos);
		if(pos.getIndex() != texto.length() || pos.getErrorIndex() != -1){
			return null;
		}else{
			return res;
		}
	}

	/**
	 * Pasa a Bigdecimal un valor numérico
	 * 
	 * @pre texto representa un BigDecimal
	 * @param texto
	 * @return
	 */
	public BigDecimal desformateaBigDecimal(String texto, int decimales) {
		BigDecimal res = null;
		res = desformateaBigDecimal(texto);
		if(res != null){
			res = BigDecimalUtil.redondear(res, decimales);
		}
		return res;
	}

	public char getDecimalSeparator() {
		return ((DecimalFormat) formateadorNumeros).getDecimalFormatSymbols().getDecimalSeparator();
	}

	/**
	 * Completa con ceros el valor dado y devuelve un string
	 * 
	 * @param valor
	 *            Valor distinto de null
	 * @param numcaracteres
	 * @return
	 */
	public String completarCerosIzquierda(Long valor, int numcaracteres) {
		String resultado = "" + valor;
		return completarCerosIzquierda(resultado, numcaracteres);
	}

	/**
	 * Completa con ceros el valor dado y devuelve un string
	 * 
	 * @param valor
	 *            Valor distinto de null
	 * @param numcaracteres
	 * @return
	 */
	public String completarCerosIzquierda(String valor, int numcaracteres) {
		String resultado = valor;
		if (resultado.length() < numcaracteres) {
			while (resultado.length() < numcaracteres) {
				resultado = "0" + resultado;
			}
		}
		return resultado;
	}
	
	/**
	 * Recorta o completa con ceros el valor dado y devuelve un string.
	 * 
	 * @param valor
	 *            Valor distinto de null
	 * @param numcaracteres
	 * @return
	 */
	public String recortarOCompletarCerosIzquierda(String valor, int numcaracteres) {
		String resultado = valor;
		if (resultado.length() < numcaracteres) {
			while (resultado.length() < numcaracteres) {
				resultado = "0" + resultado;
			}
		} else if(resultado.length() > numcaracteres) {
			resultado = valor.substring(resultado.length() - numcaracteres);
		}
		return resultado;
	}
	
	public int getNumeroDecimales() {
		return formateadorImportes.getMaximumFractionDigits();
	}
	
	public String getPatternDate() {
		return ((SimpleDateFormat) formateadorFechas).toPattern();
	}

	public String formateaNumeroEnTexto(Long number){
		String res = "";
		if(formateadorTexto != null && number != null){
			res = formateadorTexto.convert(number);
		}
		return res;
	}
	
	public String formateaNumeroEnTexto(BigDecimal number){	
		String resultado = "";
		if(formateadorTexto != null && number != null){	
			String separador = seleccionaSeparador();
			String numberString = number.toPlainString();
			String[] arrayNums = numberString.split("\\.");
			Long parteEntera = Long.parseLong(arrayNums[0]);
			Long parteDecimal = null;
			if(arrayNums.length > 1){
				parteDecimal = Long.parseLong(arrayNums[1]);
			}		
			resultado = formateadorTexto.convert(parteEntera);
			if(parteDecimal != null && parteDecimal != 0.0){
				resultado += separador + formateadorTexto.convert(parteDecimal);
			}
		}
		return resultado;
	}
	
	protected String seleccionaSeparador(){
		String separador = "";
		if(formateadorTexto instanceof Spanish){
			separador = " con ";
		}
		else if(formateadorTexto instanceof English){
			separador = " and ";
		}
		else if(formateadorTexto instanceof German){
			separador = " ";
		}else{
			separador = " ";
		}
		return separador;
	}
	
}
