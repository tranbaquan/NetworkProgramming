package file;

import java.io.File;

public class DirectoryTree {
	public String dirTree(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return "Not found";
		}
		int indent = 0;
		StringBuilder sb = new StringBuilder();
		
		dirTree(file, indent, sb);
		return sb.toString();
	}

	public void dirTree(File file, int indent, StringBuilder sb) {
		sb.append(addIndent(indent));
		if (!file.isDirectory()) {
			sb.append("- ");
			sb.append(file.getName());
			sb.append("\n");
		} else {
			sb.append("+ ");
			sb.append(file.getName());
			sb.append("\n");
			File[] subs = file.listFiles();
			if(subs != null) {
				for (int i = 0; i < subs.length; i++) {
					dirTree(subs[i], indent+1, sb);
				}
			}
		}
	}

	public String addIndent(int indent) {
		String s = "";
		for (int i = 0; i < indent; i++) {
			s += "\t";
		}
		return s;
	}
	
	public static void main(String[] args) {
		DirectoryTree directoryTree = new DirectoryTree();
		String path = "e:\\picture";
		System.out.println(directoryTree.dirTree(path));
	}
}
