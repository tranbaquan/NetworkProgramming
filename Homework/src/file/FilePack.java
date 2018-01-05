package file;

import java.io.*;

public class FilePack {
	public void pack(String folder, String archiveFile) throws IOException {
		File sf = new File(folder);
		if (!sf.exists())
			return;
		File[] subs = sf.listFiles();
		if (subs != null) {
			File df = new File(archiveFile);
			RandomAccessFile rar = new RandomAccessFile(df, "rw");
			for (int i = 0; i < subs.length; i++) {
				rar.writeLong(0);
			}
			rar.writeLong(-1);
			for (int i = 0; i < subs.length; i++) {
				long pos = rar.getFilePointer();
				rar.seek(i * 8);
				rar.writeLong(pos);
				rar.seek(pos);
				rar.writeLong(subs[i].length());
				rar.writeUTF(subs[i].getName());
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(subs[i]));
				int data;
				byte[] range = new byte[1024 * 100];
				while ((data = bis.read(range)) != -1) {
					rar.write(range, 0, data);
				}
				bis.close();
			}
			rar.close();
		}
	}

	public void extract(String archiveFile, String extFile) throws IOException {
		File sf = new File(archiveFile);
		File df = new File(extFile);
		RandomAccessFile rar = new RandomAccessFile(sf, "rw");
		long pos;
		long head;
		while ((pos = rar.readLong()) != -1) {
			head = rar.getFilePointer();
			rar.seek(pos);
			long size = rar.readLong();
			String name = rar.readUTF();
			if (name.equals(df.getName())) {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(df));
				writeFileExt(bos, rar, size);
				bos.close();
				rar.close();
				return;
			}
			rar.seek(head);
		}
		rar.close();
	}

	public void writeFileExt(OutputStream os, RandomAccessFile rar, long size) throws IOException {
		int data;
		byte[] buffer = new byte[1024 * 100];
		while (size > 0) {
			data = rar.read(buffer);
			os.write(buffer, 0, data);
			size -= data;
		}
	}

	public static void main(String[] args) throws IOException {
		String folder = "e:\\test";
		String archiveFile = "e:\\test1\\test1.pack";
		FilePack filePack = new FilePack();
		filePack.pack(folder, archiveFile);
		filePack.extract(archiveFile, "e:\\test1\\1.pdf");
	}
}
