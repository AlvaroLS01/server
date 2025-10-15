package com.comerzzia.core.basketcalculator.util.mybatis.interceptors;

import java.util.Properties;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts(
@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }))
public class TimeSpendInterceptor implements Interceptor {
	private Long minimumExecutionTimeMillis = 1000L;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
	
		if (minimumExecutionTimeMillis == -1) {
			return invocation.proceed(); 
		}
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		//The parameters of invocation come from the intercepting method. Here, the first parameter of query method is statement object		
		Object proceed = null;
		try {
			proceed = invocation.proceed();
		} finally {
			stopWatch.stop();
			
			if (stopWatch.getTime() > minimumExecutionTimeMillis) {					
			   mappedStatement.getStatementLog().warn("<==\tSqlid: " + mappedStatement.getId() + " execution time is: " + DurationFormatUtils.formatDurationHMS(stopWatch.getTime()));
			}
		}
		return proceed;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		if (properties.containsKey("minimumExecutionTimeMillis")) {
			minimumExecutionTimeMillis = Long.valueOf((String)properties.get("minimumExecutionTimeMillis"));
		}		
	}
}