package net.ion.shin.fox

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.ion.shin.fox.DataBaseConvert;
class SyntexTest {

	@Test
	public void gstringTest(){
		def a = 1;
		
		print(" aaa bbb ${a} bbbb");
		
		DataBaseConvert convert = new DataBaseConvert();
		convert.convertQuery();
	}
}
