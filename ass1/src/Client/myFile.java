package Client;

import java.io.File;
import java.util.List;
import java.util.LinkedList;

import Server.ClientNode;

public class myFile {
	List<MyFileNode> listFile = new LinkedList<MyFileNode>();
	
	public String removeFile(int i){
		String name =listFile.get(i).fileName;
		/*if(listFile.size()==1){
			listFile = new LinkedList<MyFileNode>();
			return name;
		}*/
		listFile.remove(i);
		return name;
	}
	
	public void addFile(MyFileNode f){
		listFile.add(f);
	}

}
