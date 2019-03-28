/**
 * Filename:   MyProfiler.java
 * Project:    p3b-201901     
 * Authors:    Matthew Beyer Lec 001
 *
 * Semester:   Spring 2019
 * Course:     CS400
 * 
 * Due Date:   3/28 10pm
 * Version:    1.0
 * 
 * Credits:    N/A
 * 
 * Bugs:       TODO: add any known bugs, or unsolved problems here
 */

// Used as the data structure to test our hash table against
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;


public class MyProfiler<K extends Comparable<K>, V> {

    HashTableADT<K, V> hashtable;
    TreeMap<K, V> treemap;
    
    public MyProfiler() {
        // Instantiate your HashTable and Java's TreeMap
    	hashtable = new HashTable();
    	treemap = new TreeMap();
    }
    
    public void insert(K key, V value) {
        // Insert K, V into both data structures
    	try {
    	hashtable.insert(key,value);
    	treemap.put(key, value);
    	}
    	catch(Exception e){
    		System.out.println("unexpected exception: " + e.getMessage());
    	}
    }
    
    public void retrieve(K key) {
        // get value V for key K from data structures
    	try {
        	hashtable.get(key);
        	treemap.get(key);
        	}
        	catch(Exception e){
        		System.out.println("unexpected exception: " + e.getMessage());
        	}
    }
    
    public static void main(String[] args) {
        try {
        	int numElements = Integer.parseInt(args[0]);
        	// Create a profile object. 
            MyProfiler<Integer,Integer> profile = new MyProfiler<Integer, Integer>();
            ArrayList<Integer> keyList = new ArrayList<Integer>();
            Random rand = new Random();
            
            for(int numKeys = numElements; numKeys>0; numKeys--) {
            	keyList.add(rand.nextInt());
            }
            
            // execute the insert method of profile as many times as numElements
            // execute the retrieve method of profile as many times as numElements
            for(Integer key: keyList) {
            	profile.insert(key,rand.nextInt());
            	profile.retrieve(key);
            }
           
            String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
            System.out.println(msg);
        }
        catch (Exception e) {
            System.out.println("Usage: java MyProfiler <number_of_elements>");
            System.exit(1);
        }
    }
}
