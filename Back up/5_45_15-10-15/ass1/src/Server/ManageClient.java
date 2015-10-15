package Server;
import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

public class ManageClient implements Serializable{
	List<ClientNode> listClient = new LinkedList<ClientNode>();
	
	public ManageClient(){
		
	}
	
	
	void addClient(ClientNode node){
		listClient.add(node);
	}
	
	public void removeClient(String name){
		int index=0;
		for(int i=0;i<listClient.size();i++){
			if(listClient.get(i).username.equals(name)){
				index=i;
				break;
			}
		}
		listClient.get(index).disconnect();
		listClient.remove(index);
	}
}


