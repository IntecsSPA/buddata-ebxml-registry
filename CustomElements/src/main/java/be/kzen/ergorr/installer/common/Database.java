/*
 * Project: Buddata ebXML Installer
 * Class: Database
 * Copyright (C) 2009 Bram Van Dam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.installer.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Very basic database abstraction layer.
 */
public class Database {
	private String database;
	private String url;
	private String user;
	private String password;

	public Database(String url, String database, String user, String password) {
		this.url = url;
		this.database = database;
		this.user = user;
		this.password = password;
	}

	public void executeSql(String sql) throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(createConnectionString(), user,
					password);

			Statement stmt = conn.createStatement();
			stmt.execute(sql);
		} finally {
			closeConnection(conn);
		}
	}

	public String createConnectionString() {
		return "jdbc:postgresql://" + url + "/" + database;
	}

	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
			}
		}
	}
}
