/**
 * 
 */
package com.test.sri.customgithub.dao.cassandra;

import java.util.List;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.test.sri.customgithub.domain.CustomGitHubBean;

/**
 * @author srikanthgummula
 *
 */
public class CassandraSchema {

	private static final String REPLICATION_STRATEGY = "SimpleStrategy";
	private static final int REPLICATION_FACTOR = 3;
	
	public static final String TABLE_NAME = "fileExtensions";
	
	private Session session;	
	
	public CassandraSchema(Session session) {
		this.session = session;
	}	
		
	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	public void createKeyspace(String keyspaceName) {
		StringBuilder sb = 
				 new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
				.append(keyspaceName)
				.append(" WITH replication = {").append("'class':'").append(REPLICATION_STRATEGY)
				.append("','replication_factor':").append(REPLICATION_FACTOR).append("};");

		String query = sb.toString();
		getSession().execute(query);
	}
	
	public void createTable() {
	    StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
	      .append(TABLE_NAME).append("(")
	      .append("file_ref text, ")
	      .append("file_name text,")
	      .append("file_extension text,")
	      .append("organization text,")
	      .append("PRIMARY KEY(file_ref,file_extension));");

	    String query = sb.toString();
	    session.execute(query);
	}
	
	public String getInsertQuery(CustomGitHubBean customGitHubBean) {
		StringBuilder sb = new StringBuilder("INSERT INTO fileExtensions (file_ref,file_name,file_extension,organization) VALUES");
		sb.append("(")
			.append(customGitHubBean.getFileRef())
			.append(customGitHubBean.getFileName())
			.append(customGitHubBean.getExtension())
			.append(customGitHubBean.getOrganization())
			.append(");");
		return sb.toString();
	}

	public Statement getDataQuery(List<String> fileExtensions, List<String> orgNames) {
		Statement statement = QueryBuilder.select()
								.all()
								.from(TABLE_NAME)
								.where(QueryBuilder.in("file_extension", fileExtensions))
								.and(QueryBuilder.in("organizations", orgNames));
		return statement;
	}
}
