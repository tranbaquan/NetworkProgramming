package tbq.rmi.download;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

import tbq.rmi.download.IDownload;;

public class DownloadClient {
	public static void download(String sf, String df) throws NotBoundException, IOException {
		Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1234);
		IDownload download = (IDownload)reg.lookup("download");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(df));
		download.openSource(sf);
		byte[] data;
		while ((data = download.readData()) != null) {
			dos.write(data);
		}
		download.closeFile();
		dos.close();
	}
	public static void main(String[] args) throws NotBoundException, IOException {
		String sf = "E:\\Test\\nong_lam_logo.png";
		String df = "E:\\Test\\abc\\nong_lam_logo.png";
		download(sf, df);
	}
}
