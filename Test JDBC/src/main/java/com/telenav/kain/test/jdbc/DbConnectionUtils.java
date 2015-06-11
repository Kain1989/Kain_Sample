package com.telenav.kain.test.jdbc;

import org.apache.commons.lang.StringUtils;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnectionUtils {

	private static final int MAX_CONNECTIONS = 10;

	private static final int INITIAL_CONNECTIONS = 3;

	private static final String CN = "cn";

	private String serverName = StringUtils.EMPTY;

	private String dbName = StringUtils.EMPTY;

	private String user = StringUtils.EMPTY;

	private String password = StringUtils.EMPTY;

	private PGPoolingDataSource source;

	private static DbConnectionUtils cnInstance;

	private static DbConnectionUtils eu_naInstance;

	private DbConnectionUtils(String region) {

//		serverName = "172.16.101.77";
//		dbName = "TnGeo-Here-Data";
//		user = "contentwriter";
//		password = "szf123";

		serverName = "172.16.100.232";
		dbName = "TnGeo-Here-Data";
		user = "contentreader";
		password = "rdyh45td";

		source = new PGPoolingDataSource();
		source.setServerName(serverName);
		source.setDatabaseName(dbName);
		source.setUser(user);
		source.setPassword(password);
		source.setMaxConnections(MAX_CONNECTIONS);
		source.setInitialConnections(INITIAL_CONNECTIONS);
//		source.setBinaryTransfer(true);

	}

	public synchronized static Connection getDbConnection(String region) throws SQLException {
		if (region.equalsIgnoreCase(CN)) {
			if (cnInstance == null) {
				cnInstance = new DbConnectionUtils(region);
			}
			return cnInstance.source.getConnection();
		} else {
			if (eu_naInstance == null) {
				eu_naInstance = new DbConnectionUtils(region);
			}
			return eu_naInstance.source.getConnection();
		}
	}

//	public static Connection getDbConnection(String region) throws SQLException {
//		Object obj = new Object();
//		synchronized (obj) {
//			if (region.equalsIgnoreCase(CN)) {
//				if (cnInstance == null) {
//					cnInstance = new DbConnectionUtils(region);
//				}
//				return cnInstance.source.getConnection();
//			} else {
//				if (eu_naInstance == null) {
//					eu_naInstance = new DbConnectionUtils(region);
//				}
//				return eu_naInstance.source.getConnection();
//			}
//		}
//	}
}
