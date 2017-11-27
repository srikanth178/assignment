/**
 * 
 */
package com.test.sri.customgithub.dao.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;

/**
 * @author srikanthgummula
 *
 */
public class CassandraConnector {
	
	private static final String NODE = "127.0.0.1";
	private static final Integer PORT = 9142;
	
	private Cluster cluster;

    private Session session;

    public void connect() {
        Builder b = Cluster.builder().addContactPoint(NODE);
        if (PORT != null) {
            b.withPort(PORT);
        }
        cluster = b.build();

        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
