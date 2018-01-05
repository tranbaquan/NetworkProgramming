package tbq.rmi.upload;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

public class UploadClient {
	public static void upload(String sf, String df) throws NotBoundException, IOException {
		Registry reg = LocateRegistry.getRegistry("127.0.0.1", 12345);
		IUpload upload = (IUpload)reg.lookup("upload");
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sf));
		upload.openDest(df);
		int data;
		byte[] buff = new byte[100*1024];
		while ((data = bis.read(buff)) != -1) {
			upload.writeData(buff, data);
		}
		upload.close();
		bis.close();
	}
	public static void main(String[] args) throws NotBoundException, IOException {
		String sf ="E:\\Test\\nong_lam_logo.png";
		String df ="E:\\Test\\abc\\nong_lam_logo.png";
		upload(sf, df);
	}
}
