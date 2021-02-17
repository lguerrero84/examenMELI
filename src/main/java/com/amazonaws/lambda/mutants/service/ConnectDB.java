package com.amazonaws.lambda.mutants.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

	private static Connection conn;
	private static final String user="admin";
	private static final String pwd="Luis1984";
	private static final String url="jdbc:mysql://meliexamen.cpiqgxyj55nc.us-east-1.rds.amazonaws.com:3306/mutants";
	private static final String driver="com.mysql.cj.jdbc.Driver";
	
	public ConnectDB() {
		conn=null;
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,user,pwd);
			if(conn != null)
			{
				System.out.print("Conexion establecida");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConection() {
		return conn;
	}
	
	public void desconectar() {
		conn=null;
	}
	
}


