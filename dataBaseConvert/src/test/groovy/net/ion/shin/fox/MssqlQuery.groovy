package net.ion.shin.fox

import groovy.sql.Sql;
import groovy.sql.DataSet;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test

//import com.google.common.collect.StandardTable.Row.RowEntrySet;

import groovy.sql.DataSet
import groovy.sql.GroovyRowResult
import groovy.sql.Sql;

class MssqlQuery {
	private Sql source = Sql.newInstance (
		'jdbc:sqlserver://10.10.1.171:1433;DatabaseName=KGT',
		'sa',
		'd?UM3Phug_pe',
		'com.microsoft.sqlserver.jdbc.SQLServerDriver');
	
	
	@Test
	public void select(){
		def query = """
		SELECT c.column_name, k.CONSTRAINT_TYPE, c.table_name FROM 
		INFORMATION_SCHEMA.KEY_COLUMN_USAGE c
		inner join INFORMATION_SCHEMA.TABLE_CONSTRAINTS k on c.CONSTRAINT_NAME = k.CONSTRAINT_NAME
		WHERE k.CONSTRAINT_TYPE = 'PRIMARY KEY' and k.TABLE_NAME='AccountsMembership'		
		"""
		List<Map<String, Object>> rows = source.rows(query);
		rows.each { row ->
			println(row);
		}
	}
}
