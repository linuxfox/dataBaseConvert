package net.ion.shin.fox;

import java.util.Map;

public class ImageConvert implements ConvertExecutor {

	@Override
	public void exec(String id, Map<String, Object> param) {
		System.out.println(param);
		
	}
}
