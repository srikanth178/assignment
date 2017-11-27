/**
 * 
 */
package com.test.sri.customgithub.dao.cassandra;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
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

	public List<CustomGitHubBean> getData(List<String> fileExtensions, List<String> orgNames) {
		Statement statement = cassandraSchema.getDataQuery(fileExtensions,orgNames);
		ResultSet result = cassandraConnector.getSession().execute(statement);
		
		List<CustomGitHubBean> customGitHubBeanList = new ArrayList<CustomGitHubBean>();
		for(Row row: result) {
			CustomGitHubBean customGitHubBean = new CustomGitHubBean(row.getString("organization"), row.getString("file_extension"), row.getString("file_name"), row.getString("file_ref"));
			customGitHubBeanList.add(customGitHubBean);
		}
		
		return customGitHubBeanList;
	}

	
}
