package com.comerzzia.core.basketcalculator.servicios.sesion;

import java.util.Locale;

import org.apache.ibatis.session.SqlSessionFactory;

import com.comerzzia.core.basketcalculator.util.i18n.Translation;

public interface IDatosSesion {

	String getUidInstancia();

	String getUidActividad();
	
	String getUser();
	
	Long getUserId();
	
	Locale getLocale();

	String getCodEmpresa();	
    
	Translation getTranslation();		
	
	@Deprecated
	SqlSessionFactory getSqlSessionFactory();
}