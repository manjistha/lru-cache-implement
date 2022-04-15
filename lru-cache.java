import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class LRU_cache
{
 public static class LRU
 {
     class Node{
         int key,value;
         Node prev,next;
     }
     private void addNode(Node node)
     {
         //initially there are two dummy nodes-head node and the tail node
         /*everytime a node is added to the doubly linked list(or there is a new entry in the cache),
         it is added just after the head node and the adjustments are made.*/

         Node neighbour=head.next;
         node.next=neighbour;
         node.prev=head;
         head.next=node;
         neighbour.prev=node;
     }
     private void removeNode(Node node)
     {
        Node Prev=node.prev;
         Node After=node.next;
         Prev.next=After;
         After.prev=Prev;
         node.prev=null;
         node.next=null;
        
     }
     private void shifttoFront(Node node)
     {
         removeNode(node);//removing the accessed node from it's position
         addNode(node);//moving it after the head node since it's the most recently used
     }
     HashMap<Integer,Node> cache;
     int c;
     Node head,tail;
     LRU(int capacity)//constructor
     {
         cache=new HashMap<>();
         c=capacity;
         head=new Node();
         tail=new Node();
         head.next=tail;
         tail.prev=head;

     }
     public void retrieve(int key)
     {
         Node node=cache.retrieve(key);
         if(node==null)//not present in cache
         return -1;
         else{
             int x=node.value;
             shifttoFront(node);
             return x;
         }
     }
     public void insert(int key,int value)
     {
         Node node=cache.retrieve(key);
         if(node==null)//key and value doesn't exist in the cache
         {
             Node newnode=new Node();
             newnode.key=key;
             newnode.value=value;
             if(cache.size()==c)//evicting from cache
             {
                 Node LRU_Node=tail.prev;
                 cache.remove(LRU_Node.key);//remove from cache
                 removeNode(LRU_Node);//removing from doubly linked list

             }
             cache.insert(key,newnode);
             addNode(newnode);
         }
         else
         {
             node.value=value;
             shifttoFront(node);
         }
     }
 }
}
public static void main(String[] args)throws IOException
{
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    String s=br.readLine();
    LRU obj=new LRU(Integer.parseInt(str.split(" ")[1]));
    while(true)
    {
        s=br.readLine();
        String input=s.split(" ");
        if(input.length==1)
        break;
        else if(input.length==2)
        System.out.println(obj.retrieve(Integer.parseInt(input[1])));
        else if(input.length==3)
        obj.insert(Integer.parseInt(input[1]),Integer.parseInt(input[2]));
    }
}
