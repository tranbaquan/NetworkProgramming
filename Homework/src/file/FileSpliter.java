package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSpliter {
	public void fileSplit(String sf, long partSize) throws IOException {
		File file = new File(sf);
		if (!file.exists())
			return;
		long partNum = file.length() / partSize;
		long remainSize = file.length() % partSize;
		InputStream fis = new FileInputStream(sf);
		BufferedInputStream bis = new BufferedInputStream(fis);
		for (int i = 1; i <= partNum; i++) {
			String df = sf + "." + ext(i);
			OutputStream fos = new FileOutputStream(df);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			transfer(bis, bos, partSize);
			bos.close();
		}
		if (remainSize > 0) {
			int count = (int) partNum + 1;
			String df = sf + "." + ext(count);
			OutputStream fos = new FileOutputStream(df);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			transfer(bis, bos, remainSize);
			bos.close();
		}
		bis.close();
	}

	public void transfer(InputStream is, OutputStream os, long partSize) throws IOException {
		byte[] bufferSize = new byte[100 * 1024];
		int byteRead, byteMustRead;
		long remain = partSize;
		while (remain > 0) {
			byteMustRead = bufferSize.length < remain ? bufferSize.length : (int) remain;
			byteRead = is.read(bufferSize, 0, byteMustRead);
			os.write(bufferSize, 0, byteRead);
			remain -= byteRead;
		}
	}

	public String ext(int index) {
		String ext = "" + index;
		while (ext.length() < 3) {
			ext = "0" + ext;
		}
		return ext;
	}
	
	public void fileJoiner(String path) throws IOException {
		File sf = new File(path);
		if(!sf.exists()) {
			throw new IllegalArgumentException("File does not exists!");
		}
		File parent = sf.getParentFile();
		List<String> fileNames = new ArrayList<>();
		if(parent != null) {
			File[] subs = parent.listFiles();
			if(subs != null) {
				for (int i = 0; i < subs.length; i++) {
					if(subs[i].getPath().contains(sf.getPath())) {
						fileNames.add(subs[i].getPath());
					}
				}
			}
		}
		fileNames.remove(sf.getPath());
		Collections.sort(fileNames);
		String df = parent.getPath() + File.separator + "Join" + sf.getName();
		OutputStream os = new FileOutputStream(df);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		for (int i = 0; i < fileNames.size(); i++) {
			InputStream is = new FileInputStream(fileNames.get(i));
			BufferedInputStream bis = new BufferedInputStream(is);
			join(bis, bos);
		}
		bos.close();
	}

	public void join(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[1024*100];
		int data;
		while ((data = is.read(buffer)) != -1) {
			os.write(buffer, 0, data);
		}
		is.close();
	}

	public static void main(String[] args) throws IOException {
		FileSpliter fileSplit = new FileSpliter();
		fileSplit.fileSplit("e:\\NetworkProgrammingTest\\test1\\1.pdf", 5000 * 1024);
//		fileSplit.fileJoiner("E:\\1.pdf");
	}
}
