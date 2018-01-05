package test_skill;

import java.io.*;

public class Pack {
	public void pack(String sfolder, String achiveFile) throws IllegalAccessException {
		File sf = new File(sfolder);
		if(!sf.isDirectory()) {
			throw new IllegalAccessException("Source is not folder or not exist!");
		}
		File[] subs = sf.listFiles();
		if(subs != null) {
			
		}
	}
}