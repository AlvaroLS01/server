package com.comerzzia.pos.services.promociones;

/**Interfaz para ser implementada desde los diferentes tipos de promociones para poder clasificar cada promoción
 * según su tipo de aplicación (orden y momento en el que son analizadas y aplicadas) */
public interface PromocionTipoAplicacion {

	public boolean isAplicacionFinal();
	
	public boolean isAplicacionCabecera();
	
	public boolean isAplicacionLinea();
	
	public boolean isAplicacionPrecio();
	
	public boolean isAplicacionGeneracionCupon();
	
	public boolean isAplicacionCupon();
	
	boolean isImporte();
	
}
