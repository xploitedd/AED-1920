/*
package series.serie2;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static series.serie2.ListUtils.internalReverse;

public class InternalReverseTest {

	@Test
	public void  internalreverse_singleton_list(){
		Node<Node<Integer>> list = new Node<Node<Integer>>();
		ArrayList<Integer> array=new  ArrayList<Integer>();
		for(int i=10;i>=0;i--) array.add(i);
		Node<Integer> subList= ListUtilTest.getListWithoutSentinel(array);
		list.value=subList;
		internalReverse(list);
		Node<Integer> cur=list.value;
		for(int i:array){
			assertEquals(array.get(i),cur.value);
			cur=cur.next;
		}
	}

	@Test
	public void internalreverse_lists(){
		Node<Node<Integer>> list = new Node<Node<Integer>>();
		Node<Node<Integer>> current=list;
		Node<Node<Integer>> novo=null;
		ArrayList<ArrayList<Integer>> array=new ArrayList<ArrayList<Integer>>();
		for(int i=1;i<=10;i++){
			ArrayList<Integer> subArray=new ArrayList<Integer>();
			for(int j=1;j<=10;j++){
				subArray.add(i*j);
			}
			array.add(subArray);
			current.value=ListUtilTest.getListWithoutSentinel(subArray);
			novo=new Node<Node<Integer>>();
			current.next=novo;
			novo.previous=current;
			current=current.next;
		}
		internalReverse(list);
		for(int i=0;i<=9;i++){
			Node<Integer> cur=list.value;
			ArrayList<Integer> sub=array.get(i);
			for(int j=sub.size()-1;j>=0;j--){
				assertEquals(sub.get(j),cur.value);
				cur=cur.next;
			}
			list=list.next;
		}
	}


	@Test
	public void internalreverse_withSomeEmptylists(){
		Node<Node<Integer>> list = new Node<Node<Integer>>();
		Node<Node<Integer>> current=list;
		Node<Node<Integer>> novo=null;
		ArrayList<ArrayList<Integer>> array=new ArrayList<ArrayList<Integer>>();
		for(int i=1;i<=10;i++){
			ArrayList<Integer> subArray=new ArrayList<Integer>();
			if(i%2==0){
				for(int j=1;j<=10;j++){
					subArray.add(i*j);
				}
			}
			array.add(subArray);
			current.value=ListUtilTest.getListWithoutSentinel(subArray);
			novo=new Node<Node<Integer>>();
			current.next=novo;
			novo.previous=current;
			current=current.next;
		}
		internalReverse(list);
		for(int i=0;i<=9;i++){
			Node<Integer> cur=list.value;
			ArrayList<Integer> sub=array.get(i);
			if(i%2!=0){
				for(int j=sub.size()-1;j>=0;j--){
					assertEquals(sub.get(j),cur.value);
					cur=cur.next;
					}
			}
			else{
				assertEquals(null,cur);
			}
			list=list.next;
		}
	}
}*/
