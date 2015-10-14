package Server;
import java.util.List;
import java.util.LinkedList;

public class ManageClient implements Runnable{
	List<ClientNode> listClient = new LinkedList<ClientNode>();
	
	public ManageClient(){
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
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


