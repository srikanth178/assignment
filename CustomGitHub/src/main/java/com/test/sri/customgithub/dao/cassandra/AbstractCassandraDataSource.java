/**
 * 
 */
package com.test.sri.customgithub.dao.cassandra;

import java.util.List;

import com.test.sri.customgithub.dao.CustomGitHubDataSource;
import com.test.sri.customgithub.domain.CustomGitHubBean;

/**
 * @author srikanthgummula
 *
 */
public abstract class AbstractCassandraDataSource implements CustomGitHubDataSource {

		
	public abstract void connect();
	public abstract void createKeySpace();
	public abstract void createTable();
	public abstract void close();
	public abstract void insertData(List<CustomGitHubBean> customGitHubBeanList);
	
	public void insert(List<CustomGitHubBean> customGitHubBeanList) {
		connect();
		createKeySpace();
		createTable();
		insert(customGitHubBeanList);
		close();
	}

}
