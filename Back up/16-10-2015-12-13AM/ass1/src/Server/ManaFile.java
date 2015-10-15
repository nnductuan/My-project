package Server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ManaFile implements Serializable {
	public List<FileNode> listFile = new LinkedList<FileNode>();
	
	public void addFile(FileNode file){
		listFile.add(file);
	}
	public void remove(int i){
		listFile.remove(i);
	}
}
