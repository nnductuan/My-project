package Client;

public class MyFileNode {
	String user;
	String fileName;
	String path;
	long size;
	MyFileNode(String us,String fn,String path,long size){
		this.user = us;
		this.fileName=fn;
		this.path= path;
		this.size=size;
	}

}
