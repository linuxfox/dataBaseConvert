package net.ion.shin.fox

import groovy.sql.Sql;
import groovy.sql.DataSet;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import groovy.sql.DataSet
import groovy.sql.GroovyRowResult
import groovy.sql.Sql;

class DataBaseConvert {
	private Sql source = Sql.newInstance (

		'org.mariadb.jdbc.Driver');
	
	private Sql target = Sql.newInstance (

		'org.postgresql.Driver');
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		source.close();
		target.close();
	}
	
	@Test
	public void convert(){
		//String query = "select * from mk_sequence limit 2";
		
		String query_tbl = "select top 10 * from INFORMATION_SCHEMA.TABLES";
		String query_col = "select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = :table_name";
		
		/*
		sql.eachRow(query_tbl){ tables ->
			String table_name = tables.TABLE_NAME;
			
			String ddl = "create $table_name (";
			
			sql.eachRow(query_col, [table_name:tables.TABLE_NAME]){ rows ->
				//print(rows.TABLE_NAME);
				//print(rows.COLUMN_NAME);
				//print(rows.DATA_TYPE);
				//println(rows.CHARACTER_MAXIMUM_LENGTH);
				//Map columns = rows.findAll();
				//print(columns);
			}
			
			ddl = ddl + ")";
			
			println(ddl);
		}
		*/
		
		source.eachRow("select * from serialnumber"){ row ->
			def meta = row.getMetaData();
			println(meta.getColumnName(1) + ':' +  row['number']);
		}
	}
}
