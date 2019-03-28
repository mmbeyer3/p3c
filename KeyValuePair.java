/**
 * Assignment: P3a Hash Table
 * Lecture: 001
 * Due: 3/14/19
 * email: mmbeyer3@wisc.edu
 * @author Matthew Beyer
 *
 * @param <K> generic type of key
 * @param <V> generic type of value
 */
public class KeyValuePair<K extends Comparable<K>, V>{

	public K key;
	public V value;
	private boolean removed = false;
	
	/**
	 * constructor for a key value pair of given key and value
	 * 
	 * @param key key of object (Comparable)
	 * @param value value of object
	 */
	public KeyValuePair(K key , V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Flags KeyValuePair as being removed
	 */
	public void remove(){
		removed = true;
	}
	/**
	 * returns if keyValuePair is removed
	 * 
	 * @return removed
	 */
	public boolean isRemoved() {
		return removed;
	}
}
