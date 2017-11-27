/**
 * 
 */
package com.test.sri.customgithub.dao.cassandra;

import java.util.List;

import com.test.sri.customgithub.domain.CustomGitHubBean;

/**
 * @author srikanthgummula
 *
 */
public class CassandraDataSourceImpl extends AbstractCassandraDataSource {

	private CassandraConnector cassandraConnector;
	private CassandraSchema cassandraSchema; 
	private String keyspace;
	
	public CassandraDataSourceImpl(String keyspace) {
		this.keyspace = keyspace;
		cassandraConnector = new CassandraConnector();
		cassandraSchema = new CassandraSchema(cassandraConnector.getSession());
	}
	
	/* (non-Javadoc)
	 * @see com.test.sri.customgithub.dao.cassandra.AbstractCassandraDataSource#connect()
	 */
	@Override
	public void connect() {		
		cassandraConnector.connect();
	}

	/* (non-Javadoc)
	 * @see com.test.sri.customgithub.dao.cassandra.AbstractCassandraDataSource#createKeySpace(java.lang.String)
	 */
	@Override
	public void createKeySpace() {
		cassandraSchema.createKeyspace(keyspace); 		
	}

	/* (non-Javadoc)
	 * @see com.test.sri.customgithub.dao.cassandra.AbstractCassandraDataSource#createTable()
	 */
	@Override
	public void createTable() {
		cassandraSchema.createTable();
	}	
	
	@Override
	public void close() {
		cassandraConnector.close();		
	}

	@Override
	public void insertData(List<CustomGitHubBean> customGitHubBeanList) {
		for(CustomGitHubBean customGitHubBean:customGitHubBeanList) {
			String query = cassandraSchema.getInsertQuery(customGitHubBean);
			cassandraConnector.getSession().execute(query);
		}		
	}

	
}
