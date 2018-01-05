package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {
	public void copyFile(String source, String dest) throws IOException {
		InputStream is = new FileInputStream(source);
		OutputStream os = new FileOutputStream(dest);
		byte[] range = new byte[1024 * 100];
		int data;
		while ((data = is.read(range)) > -1) {
			os.write(range, 0, data);
		}
		is.close();
		os.close();
	}

	public void copyFolder(String source, String dest) throws IOException {
		File sf = new File(source);
		File df = new File(dest);
		if (!sf.exists()) {
			throw new IllegalArgumentException("Directory does not exists!");
		}
		if (!sf.isDirectory()) {
			InputStream is = new FileInputStream(sf);
			OutputStream os = new FileOutputStream(df);
			byte[] buffer = new byte[1024 * 100];
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int data;
			while ((data = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, data);
			}
			bis.close();
			bos.close();
		} else {
			if(!df.exists()) {
				df.mkdirs();
			}
			File[] subs = sf.listFiles();
			if (subs != null) {
				for (int i = 0; i < subs.length; i++) {
					String fileName = subs[i].getName();
					String destPath = df.getPath() + File.separator + fileName;
					copyFolder(subs[i].getPath(), destPath);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String source = "E:\\1";
		String dest = "E:\\1-copy";
		FileCopy copyFile = new FileCopy();
		copyFile.copyFolder(source, dest);
	}
}
