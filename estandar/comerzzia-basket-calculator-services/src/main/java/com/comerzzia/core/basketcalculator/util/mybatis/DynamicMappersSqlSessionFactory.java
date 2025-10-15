package com.comerzzia.core.basketcalculator.util.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

/**
 * Wrapper a {@link SqlSessionFactory} que devuelve {@link DynamicMappersSqlSession}
 */
public class DynamicMappersSqlSessionFactory implements SqlSessionFactory {

	private SqlSessionFactory sqlSessionFactory;

	public DynamicMappersSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public SqlSession openSession() {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession());
	}

	@Override
	public SqlSession openSession(boolean autoCommit) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(autoCommit));
	}

	@Override
	public SqlSession openSession(Connection connection) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(connection));
	}

	@Override
	public SqlSession openSession(TransactionIsolationLevel level) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(level));
	}

	@Override
	public SqlSession openSession(ExecutorType execType) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(execType));
	}

	@Override
	public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(execType, autoCommit));
	}

	@Override
	public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(execType, level));
	}

	@Override
	public SqlSession openSession(ExecutorType execType, Connection connection) {
		return new DynamicMappersSqlSession(sqlSessionFactory.openSession(execType, connection));
	}

	@Override
	public Configuration getConfiguration() {
		return sqlSessionFactory.getConfiguration();
	}

	/**
	 * SqlSession que internamente llama a getConfiguration().addMapper(Class) cuando se ejecuta el {@link #getMapper(Class)} 
	 */
	static class DynamicMappersSqlSession implements SqlSession {
		private SqlSession sqlSession;

		public DynamicMappersSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;			
		}

		public <T> T selectOne(String statement) {
			return sqlSession.selectOne(statement);
		}

		public <T> T selectOne(String statement, Object parameter) {
			return sqlSession.selectOne(statement, parameter);
		}

		public <E> List<E> selectList(String statement) {
			return sqlSession.selectList(statement);
		}

		public <E> List<E> selectList(String statement, Object parameter) {
			return sqlSession.selectList(statement, parameter);
		}

		public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
			return sqlSession.selectList(statement, parameter, rowBounds);
		}

		public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
			return sqlSession.selectMap(statement, mapKey);
		}

		public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
			return sqlSession.selectMap(statement, parameter, mapKey);
		}

		public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
			return sqlSession.selectMap(statement, parameter, mapKey, rowBounds);
		}

		public void select(String statement, Object parameter, ResultHandler handler) {
			sqlSession.select(statement, parameter, handler);
		}

		public void select(String statement, ResultHandler handler) {
			sqlSession.select(statement, handler);
		}

		public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
			sqlSession.select(statement, parameter, rowBounds, handler);
		}

		public int insert(String statement) {
			return sqlSession.insert(statement);
		}

		public int insert(String statement, Object parameter) {
			return sqlSession.insert(statement, parameter);
		}

		public int update(String statement) {
			return sqlSession.update(statement);
		}

		public int update(String statement, Object parameter) {
			return sqlSession.update(statement, parameter);
		}

		public int delete(String statement) {
			return sqlSession.delete(statement);
		}

		public int delete(String statement, Object parameter) {
			return sqlSession.delete(statement, parameter);
		}

		public void commit() {
			//Se fuerza el commit. Esto hace que se fuerce el commit aunque sólo haya habido select
			//Es necesario porque solemos crear nuevos SqlSession usando la connection ( openSession(connection) ) y éste segundo DefaultSqlSession no tiene la variable dirty asignada 
			sqlSession.commit(true);
		}

		public void commit(boolean force) {
			sqlSession.commit(force);
		}

		public void rollback() {
			sqlSession.rollback();
		}

		public void rollback(boolean force) {
			sqlSession.rollback(force);
		}

		public List<BatchResult> flushStatements() {
			return sqlSession.flushStatements();
		}

		public void close() {
			sqlSession.close();
		}

		public void clearCache() {
			sqlSession.clearCache();
		}

		public Configuration getConfiguration() {
			return sqlSession.getConfiguration();
		}

		public <T> T getMapper(Class<T> type) {
			// Se necesita sincronizar hilos en el objeto configuration para
			// evitar que se produzcan errores cuando 2 hilos con 2 sqlSession
			// distintos entran al método y uno de ellos se queda añadiendo el
			// nuevo mapper mientras que el otro ya pasa a llamar a getMapper
			// pero aún no están todos los Statements añadidos
			synchronized (getConfiguration()) { 
				if (!getConfiguration().hasMapper(type)) {
					getConfiguration().addMapper(type);
				}
			}
			return sqlSession.getMapper(type);
		}

		public Connection getConnection() {
			return sqlSession.getConnection();
		}

		@Override
		public <T> Cursor<T> selectCursor(String arg0) {
			return sqlSession.selectCursor(arg0);
		}

		@Override
		public <T> Cursor<T> selectCursor(String arg0, Object arg1) {
			return sqlSession.selectCursor(arg0, arg1);
		}

		@Override
		public <T> Cursor<T> selectCursor(String arg0, Object arg1,
				RowBounds arg2) {
			return sqlSession.selectCursor(arg0, arg1, arg2);
		}
		
	}
	
}
