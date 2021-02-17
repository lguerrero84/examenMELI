package com.amazonaws.lambda.mutants.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.amazonaws.lambda.mutants.dto.AverageMutantsDTO;
import com.amazonaws.lambda.mutants.dto.MutantDTO;
import com.amazonaws.services.lambda.runtime.LambdaLogger;



public class admiCRUD {
	
	ConnectDB conect=new ConnectDB();
	Connection con;
	Statement stmt ;
	ResultSet rs;
	
	public void guardarCadena(MutantDTO mutant,LambdaLogger logger) {
		
		try {
			logger.log("Registra en base de datos");
			mutant.setCadena("prueba lambda");
			mutant.setResultado(2);
			logger.log("Establecer conexion bd");
			con=conect.getConection();
			if(con==null)
			{
				logger.log("Conexion null");
			}
			stmt = con.createStatement();
			stmt.executeUpdate("insert into mutants (cadena,resultado)  VALUE ('"+mutant.getCadena() +"',"+mutant.getResultado()+")");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public AverageMutantsDTO getAverage() {
		
		AverageMutantsDTO avegareMutant=new AverageMutantsDTO();
		
		try {
			
			con=conect.getConection();
			stmt=con.createStatement();
			rs= stmt.executeQuery("select count(cadena),resultado from mutants group by resultado");
			
			while(rs.next())
			{
				if(rs.getInt(2)==1)
				{
					avegareMutant.setCount_mutant_dna(rs.getInt(1));
				}else if(rs.getInt(2)==2) {
					avegareMutant.setCount_human_dna(rs.getInt(1));
				}
			}
			
			avegareMutant.setRatio(avegareMutant.getCount_mutant_dna(),avegareMutant.getCount_human_dna());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return avegareMutant;
		
	}
	
	
	

}
