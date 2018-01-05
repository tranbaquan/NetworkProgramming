package file;

import java.io.File;

public class FileDelete {
	// recursive
	
	public static boolean delete(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (file.isDirectory() && file.listFiles() != null) {
			for (File f : file.listFiles()) {
				delete(f.getPath());
			}
		}
		return file.delete();
	}

	// delete all file in folder but not delete folder
	
	public boolean deleteFileOnly(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (!file.isDirectory()) {
			return file.delete();
		}
		boolean check = true;
		if (file.list() != null) {
			for (File subFiles : file.listFiles()) {
				check &= deleteFileOnly(subFiles.getPath());
			}
		}
		return check;
	}
	
	public static void main(String[] args) {
		 FileDelete deleteFile = new FileDelete();
		 String path = "e:\\tmp";
		 System.out.println(deleteFile.deleteFileOnly(path));
	}
}
