/**
 * Assignment: P3a Hash Table Lecture: 001 Due: 3/14/19 email: mmbeyer3@wisc.edu
 * 
 * @author Matthew Beyer
 *
 * @param <K>
 *            generic type of key
 * @param <V>
 *            generic type of value
 */

// **describe the collision resolution scheme you have chosen**
// using open addressing with linear probing
//
// **explain your hashing algorithm here**
// algorithm will get java hashcode() int value and mod by the table size
//
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	private double LFThreshold;
	private double loadFactor;
	private int capacity = 0;
	private int numKeys = 0;
	private KeyValuePair<K, V>[] hashTable;

	/**
	 * No arguments constructor creates hashtable size 31(Prime), with load factor
	 * threshold of 75%
	 */
	public HashTable() {
		// call constructor with init capacity and LF Threshold
		this(31, 0.75);
	}

	/**
	 * Creates a hashtable size initialcapacity with a LFThreshold of
	 * loadFactorThreshold
	 * 
	 * @param initialCapacity
	 *            size of hashtable
	 * @param loadFactorThreshold
	 *            Threshold for which the table will expand
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		// creates KeyValuePair array of size intitalCapacity
		capacity = initialCapacity;
		hashTable = new KeyValuePair[capacity];
		// sets LFThreshold to given threshold
		LFThreshold = loadFactorThreshold;
	}

	/**
	 * creates an index for the specific object in the hashtable
	 * 
	 * @return
	 */
	public int hashIndex(K key) {
		// gets hashcode of object
		int hash = key.hashCode();
		hash = Math.abs(hash);
		// mod by table size
		return hash % capacity;
	}

	/**
	 * inserts a key value pair into the hash table first hashes key then puts
	 * object at correct index if there is a collision it will perform linear
	 * probing
	 */
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if (key == null)
			throw new IllegalNullKeyException();
		// update loadFactor
		numKeys++;
		loadFactor = ((double) numKeys / capacity);

		// expand loadFactor equals or exceeds LFThreshold
		if (loadFactor >= LFThreshold)
			expand();
		// get hash index for key
		int hashIndex = hashIndex(key);
		if (hashTable[hashIndex] != null && !hashTable[hashIndex].isRemoved()) {
			if (hashTable[hashIndex].key.equals(key))
				throw new DuplicateKeyException();
		}

		// insert into table
		// if index not empty, insertLinearProbing() Linear Probing
		if (hashTable[hashIndex] != null && !hashTable[hashIndex].isRemoved()) {
			insertLinearProbing(key, value);
		} else // if index is empty or item is removed insert into index
			hashTable[hashIndex] = new KeyValuePair<K, V>(key, value);
	}

	/**
	 * performs linear probing by looping until finding null location or removed
	 * value
	 * 
	 * @param key
	 *            key of value inserting
	 * @param value
	 *            value of data inserting
	 * @throws DuplicateKeyException
	 *             throws if key is found already in hashTable
	 */
	private void insertLinearProbing(K key, V value) throws DuplicateKeyException {
		// loop until hit null or removed location
		int index = hashIndex(key) + 1;
		while (hashTable[index] != null && !hashTable[index].isRemoved()) {
			if (hashTable[index].key.equals(key))
				throw new DuplicateKeyException();
			index++;
			if (index == capacity)
				index = 0;
		}
		// exits when found empty or removed location
		hashTable[index] = new KeyValuePair<K, V>(key, value);
	}

	/**
	 * removes key from HashTable, decrements numKeys if key not found return false
	 * 
	 * @throws IllegalNullKeyException
	 *             when key is null
	 */
	public boolean remove(K key) throws IllegalNullKeyException {
		if (key == null)
			throw new IllegalNullKeyException();

		int hashIndex = hashIndex(key);
		// if item at that index is null return false: key not found
		if (hashTable[hashIndex] == null)
			return false;

		// if index does not have key, removeLinearProbe() (return true if found)
		if (hashTable[hashIndex].key.equals(key)) {
			// if index++ equals null, remove object to be null
			if (hashTable[hashIndex + 1] == null)
				hashTable[hashIndex] = null;
			else // else set as removed
				hashTable[hashIndex].remove();
		} else {
			if (!removeLinearProbing(key))
				return false;
		}
		// if here key value has been removed
		numKeys--;
		loadFactor = ((double) numKeys / capacity);
		return true;
	}

	/**
	 * iterates through array until finding open spot and returning false or finding
	 * the key and removing the object
	 * 
	 * @param key
	 *            key of object to remove
	 * @return true if item removed
	 */
	private boolean removeLinearProbing(K key) {
		int index = hashIndex(key) + 1;
		if (hashTable[index] == null)
			return false;
		// loop until key found or null index
		while (!hashTable[index].key.equals(key)) {
			index++;
			if (index == capacity)
				index = 0;
			// if null at index in hashTable, key not found
			if (hashTable[index] == null)
				return false;
		}
		// Here if key found
		// if next is null, set key,value as null
		if (hashTable[index + 1] == null)
			hashTable[index] = null;
		else // else set as removed
			hashTable[index].remove();
		return true;
	}

	/**
	 * returns value of object with key key
	 * 
	 * @return value corresponding to the key
	 * @throws IllegalNullKeyException
	 *             when key is null
	 * @throws KeyNotFoundException
	 *             when key is not found
	 */
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if (key == null) {
			throw new IllegalNullKeyException();
		}
		int hashIndex = hashIndex(key);
		if (hashTable[hashIndex] == null)
			throw new KeyNotFoundException();

		// if object at hashIndex matches key return value
		if (hashTable[hashIndex].key.equals(key))
			return hashTable[hashIndex].value;
		else// else use linear probing to check further for key
			return getLinearProbing(key);
	}

	/**
	 * Performs a linear probe to find the key if available when found, returns its
	 * value if not found, throws exception
	 * 
	 * @param key
	 *            key of value to find
	 * @return value corresponding to key
	 * @throws KeyNotFoundException
	 *             thrown if key not found in hash table
	 */
	private V getLinearProbing(K key) throws KeyNotFoundException {
		int index = hashIndex(key) + 1;
		// if next index is null, key not found, throw exception
		if (hashTable[index] == null)
			throw new KeyNotFoundException();
		// loop while keys are not equal
		while (!hashTable[index].key.equals(key)) {
			index++;
			if (index == capacity)
				index = 0;

			// if index is null throw exception
			if (hashTable[index] == null)
				throw new KeyNotFoundException();
		}
		// if here, key index has been found
		return hashTable[index].value;
	}

	/**
	 * Returns the load factor threshold that was passed into the constructor when
	 * creating the instance of the HashTable.
	 * 
	 * @return load factor threshold
	 */
	public double getLoadFactorThreshold() {
		return LFThreshold;
	}

	/**
	 * returns loadFactor calculated after each insert or remove load factor =
	 * number of items / current table size
	 * 
	 * @return loadFactor
	 */
	public double getLoadFactor() {
		return loadFactor;
	}

	/**
	 * Return the current Capacity (table size) of the hash table array.
	 */
	public int getCapacity() {
		return capacity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see HashTableADT#getCollisionResolution()
	 */
	public int getCollisionResolution() {
		return 1;
	}

	/**
	 * @return the number of key,value pairs in the data structure
	 */
	public int numKeys() {
		return numKeys;
	}

	/**
	 * rehashes key value pairs into new array size 2*capacity + 1
	 */
	private void expand() {
		// save copy of current table
		KeyValuePair<K, V>[] temp = hashTable;
		// set hashTable to new array size 2 * capacity + 1
		hashTable = new KeyValuePair[(2 * capacity) + 1];
		capacity = (2 * capacity) + 1;
		numKeys = 0;
		loadFactor = 0;
		// iterate through temp array and rehash into larger table
		try {
			for (KeyValuePair data : temp) {
				if (data != null && !data.isRemoved())
					insert((K) data.key, (V) data.value);
			}
		} catch (Exception e) {
		}
		// update LoadFactor
		loadFactor = ((double) numKeys / capacity);

	}
}