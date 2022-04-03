
import java.io.*;
import java.util.*;

//************ Edge Class *********************//

class Edge{
	public int Ni, Nj, cost;
	Edge next;

	Edge(int n1, int n2, int cost){
		Ni = n1;
		Nj = n2;
		this.cost = cost;
		next = null;
	}
	public void printEdge(FileWriter outFile){

	try{
		outFile.write("(" + Ni + ", " + Nj + ", "+cost + ") -> ");
	}catch(Exception e){
		System.out.println("Unable to write using printEdge");
		}
	}
}


//********* KruskalMST Class ******************//

class KruskalMST { 
	public int numNodes;
	public int whichSet[];
	public int numSets;
	public int totalMSTCost;
	public Edge edgeHead;
	public Edge mstHead;
    public Edge dummyNodeE;
    public Edge dummyNodeM;
  
	KruskalMST(int numNodes){
		whichSet = new int[numNodes+1];
		for(int i=1; i<=numNodes; i++){
			whichSet[i] = i;

		}
		numSets = numNodes;
		this.numNodes = numNodes;
		totalMSTCost = 0;
		
		dummyNodeE = new Edge(0,0,0);
		dummyNodeM = new Edge(0,0,0);
		edgeHead = dummyNodeE;
		mstHead = dummyNodeM;
	}
	public void insert(Edge newEdge, Edge edgeHead){
		
		Edge temp = edgeHead;	

		while(temp.next != null && temp.next.cost < newEdge.cost){

			temp = temp.next;
		}

		newEdge.next = temp.next;
		temp.next = newEdge;
	}

	public Edge removeEdge(){
		if(edgeHead.next != null) {
		Edge temp = new Edge(edgeHead.next.Ni,
                        edgeHead.next.Nj,
                        edgeHead.next.cost);

		edgeHead.next = edgeHead.next.next;
		return temp;
		}
		return null;
	}

	public void merge2Sets(int Ni, int Nj){
		if(whichSet[Ni] < whichSet[Nj])
			 updateWhichSet(whichSet[Nj], whichSet[Ni]);
		else
			 updateWhichSet(whichSet[Ni], whichSet[Nj]);
	}
	public void updateWhichSet(int a, int b){
		for(int i=1;i<=numNodes;i++){
			if (whichSet[i] == a) whichSet[i] = b;
		}
	}
	public void push(Edge nextEdge, Edge mstHead){

		nextEdge.next = mstHead.next; 
    mstHead.next = nextEdge; 


	}
  public void printAry(FileWriter debugFile){
try{
    for(int i=1;i<=numNodes;i++){
      debugFile.write("for Node i: "+i+", whichSet is "+whichSet[i]+"\n");
    }
    debugFile.write("\n\n");
}catch(IOException e){

}
  }
  public void printList(Edge listHead, FileWriter outFile) throws IOException{
    Edge temp = listHead;

    
    String whichList = (listHead == edgeHead) ? "Edge List" : "MST List";
		
    outFile.write("Printing list for " + whichList + ":\n");
    while(temp != null){
      temp.printEdge(outFile);
      temp = temp.next;
    }
    outFile.write(" NULL \n");
		if(whichList == "MST List") outFile.write("Total cost: " + totalMSTCost+"\n\n");
  }


}

//********* Main Class ******************//
public class Main{

public static void main(String[] args) {
	if(args.length != 3){
		System.out.println("Not proper input");
		return;
	}
  try{
		
	FileReader inFile = new FileReader(args[0]);
	FileWriter outFile1 = new FileWriter(args[1]);
	FileWriter outFile2 = new FileWriter(args[2]);
  
  BufferedReader bf = new BufferedReader(inFile);
	Scanner sc = new Scanner(bf);
    
  int numNodes = sc.nextInt();
	KruskalMST mst = new KruskalMST(numNodes);
    
	int ni, nj, cost;
	Edge newEdge;
  while(sc.hasNextInt()){
	    	ni = sc.nextInt();
	    	nj = sc.nextInt();
	    	cost = sc.nextInt();
	    	//sc.close()
	    	
	    	newEdge = new Edge(ni, nj, cost); 

	
		mst.insert(newEdge, mst.edgeHead);
		mst.printList(mst.edgeHead, outFile2);
    }

    Edge nextEdge = mst.removeEdge();
    while(mst.numSets > 1) {

    	
	    while(mst.whichSet[nextEdge.Ni] == mst.whichSet[nextEdge.Nj]){ //nextEdge changes

	    	nextEdge = mst.removeEdge();
	    }

	    mst.push(nextEdge, mst.mstHead);
	    mst.totalMSTCost += nextEdge.cost;
	    mst.merge2Sets(nextEdge.Ni, nextEdge.Nj);
	    mst.numSets--;
	    mst.printAry(outFile2);
	    mst.printList(mst.edgeHead, outFile2); 
	    mst.printList(mst.mstHead, outFile2); 
    }
    mst.printList(mst.mstHead, outFile1);

		bf.close();
    sc.close();
    inFile.close();
    outFile1.close();
    outFile2.close();
    
  }catch(IOException e){
    System.out.println("Error in Scanning inFile");
    e.printStackTrace();
  }

}
}