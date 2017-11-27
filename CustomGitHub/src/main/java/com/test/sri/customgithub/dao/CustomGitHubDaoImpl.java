/**
 * 
 */
package com.test.sri.customgithub.dao;

import java.util.List;

import com.test.sri.customgithub.dao.cassandra.CassandraDataSourceImpl;
import com.test.sri.customgithub.domain.CustomGitHubBean;

/**
 * @author srikanthgummula
 *
 */
public class CustomGitHubDaoImpl implements CustomGitHubDao {

	private CustomGitHubDataSource dataSource;
	
	public CustomGitHubDaoImpl(String keyspace) {
		dataSource = new CassandraDataSourceImpl(keyspace);
	}
	/* (non-Javadoc)
	 * @see com.test.sri.customgithub.dao.CustomGitHubDao#storeFileRefs(java.util.List)
	 */
	public void insert(List<CustomGitHubBean> customGitHubBeanList) {
		dataSource.insert(customGitHubBeanList);
	}

}
