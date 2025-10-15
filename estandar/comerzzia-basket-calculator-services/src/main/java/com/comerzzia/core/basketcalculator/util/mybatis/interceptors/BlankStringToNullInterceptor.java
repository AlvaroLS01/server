package com.comerzzia.core.basketcalculator.util.mybatis.interceptors;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.Logger;

/**
 * Esta clase intercepta los updates que ejecuta MyBatis y aplica una conversión de datos para todos aquellos atributos de tipo <code>String</code> del
 * bean que se pasa como parámetro. Esta conversión consiste en establecer con valor <code>null</code> si el valor de la cadena es vacío (<code>""</code>).
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })  
public class BlankStringToNullInterceptor implements Interceptor {

	private static final Logger log = Logger.getLogger(BlankStringToNullInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement stmt = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];
       
        if (stmt == null) {  
            return invocation.proceed();  
        }  
        
        //Sólo se hace el procesamiento si la consulta es de tipo update o insert y no es un selective.
        if (!stmt.getId().toUpperCase().contains("SELECTIVE") && (stmt.getSqlCommandType().equals(SqlCommandType.INSERT) || stmt.getSqlCommandType().equals(SqlCommandType.UPDATE))) {  
        	BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
        	for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
		        try {
			        if(propertyDesc.getPropertyType().isAssignableFrom(String.class)) {
		                 String value = (String) propertyDesc.getReadMethod().invoke(param);
		                 if(value != null && value.isEmpty()) {
			                 Method writeMethod = propertyDesc.getWriteMethod();
			                 if (writeMethod != null) {
				                  writeMethod.invoke(param, new Object[] { null });
		                     }
		                 }
			        }
		        } catch (Exception e) {
			        log.warn("intercept() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
		        }
	        }
        }  
  
        return invocation.proceed();  
	}

	@Override
	public Object plugin(Object target) {
		 return Plugin.wrap(target, this);  
	}

	@Override
	public void setProperties(Properties properties) {
		
	}

}
