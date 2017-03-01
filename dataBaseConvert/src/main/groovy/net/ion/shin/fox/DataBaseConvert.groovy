package net.ion.shin.fox

import groovy.sql.Sql;
import groovy.sql.DataSet;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test

import com.google.common.collect.StandardTable.Row.RowEntrySet;

import groovy.sql.DataSet
import groovy.sql.GroovyRowResult
import groovy.sql.Sql;

/**
 * mssql -> mariadb 로 data convert
 * @author fox
 *
 */
class DataBaseConvert {
	private Sql source = Sql.newInstance (
		'jdbc:postgresql://localhost:5432/fox',

		'org.postgresql.Driver');
	
	private Sql target = Sql.newInstance (
		'jdbc:mariadb://localhost:3306/fox',

		'org.mariadb.jdbc.Driver');
	
	def tables = ['serialnumber', 'test'];
	 
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
	public void convertTable() throws Exception {
		
		tables.each{ table ->
			
			String query_tbl = "select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '$table'";
			String query_col = "select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = :table_name";
			
			source.eachRow(query_tbl){ tables ->
				String table_name = tables.TABLE_NAME;
				
				String ddl = " create table if not exists $table_name (";
				
				int i = 0;
				source.eachRow(query_col, [table_name:tables.TABLE_NAME]){ row ->
					def comma = i > 0 ? ", " : " ";
					
					ddl = ddl + comma + row.COLUMN_NAME + " " + convertType(i, row.udt_name , row.CHARACTER_MAXIMUM_LENGTH);
					i++;
				}
				
				
				ddl = ddl + ") COLLATE='utf8_general_ci' ENGINE=InnoDB ;";
				
				println(ddl);
				
				target.execute(ddl);
			}
			
			DataSet dataset = target.dataSet(table);
			List<Map<String, Object>> rows = source.rows("select * from " + table + "");
			
			rows.each { row ->
				dataset.add(row);
			}
			println(rows);
		}
		
		/*
		int i = 0;
		sql.eachRow("select * from AccountsProfile") { row ->
			println(row);
			
			i++;
		}
		
		println(i);
		*/
		
		source.close();
		target.close();
	}
	
	
	private String convertType(int i, String type, length){		
		String ddl = "";
		
		if(length == null){
			if(type.equals("uniqueidentifier")){
				type = "int";
			}

			ddl = type + " ";
		}
		else if(type.equals("text")){
			ddl = "longtext ";
		}
		else if(type.equals("image")){
			ddl = "longblob ";
		}
		else{
			ddl = type + "(" + length + ") ";
		}
		
		return ddl;
	}
}
