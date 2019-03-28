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

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*; 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

/**
 * Tests methods of HashTable class
 * 
 * @author Matthew Beyer
 *
 */
public class HashTableTest {

	/**
	 * creates an instance of a new hashtable
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * sets hashTables to null
	 * 
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * @return a instance of the HashTable class with K Integer, V String
	 */
	protected HashTable<Integer, String> createInstance() {
		return new HashTable<Integer, String>();
	}

	/**
	 * creates instance of hash table with small LFThreshold
	 * 
	 * @return a instance of the HashTable class with K Integer, V String
	 */
	protected HashTable<Integer, String> createInstance2() {
		return new HashTable<Integer, String>(31, .1);
	}

	/**
	 * creates instance of hash table with easy to use table size
	 * 
	 * @return a instance of the HashTable class with K Integer, V String
	 */
	protected HashTable<Integer, String> createInstance3() {
		return new HashTable<Integer, String>(10, .75);
	}

	/**
	 * Tests that a HashTable returns an integer code indicating which collision
	 * resolution strategy is used. REFER TO HashTableADT for valid collision scheme
	 * codes.
	 */
	@Test
	public void test000_collision_scheme() {
		HashTableADT htIntegerKey = new HashTable<Integer, String>();
		int scheme = htIntegerKey.getCollisionResolution();
		if (scheme < 1 || scheme > 9)
			fail("collision resolution must be indicated with 1-9");
	}

	/**
	 * Tests that insert(null,null) throws IllegalNullKeyException
	 */
	@Test
	public void test001_IllegalNullKey() {
		try {
			HashTableADT htIntegerKey = new HashTable<Integer, String>();
			htIntegerKey.insert(null, null);
			fail("should not be able to insert null key");
		} catch (IllegalNullKeyException e) {
			/* expected */ } catch (Exception e) {
			fail("insert null key should not throw exception " + e.getClass().getName());
		}
	}

	/**
	 * Tests that LoadFactor is set correctly by the constructor
	 */
	@Test
	public void test002_LoadFactorThreshold_set_get() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		if (hashTable.getLoadFactorThreshold() != .75)
			fail("Default constructor hashTable should have a threshold of .75");
		hashTable = new HashTable<Integer, String>(31, .1);
		if (hashTable.getLoadFactorThreshold() != .1)
			fail("constructor should have intialized hashTable2 with threshold of .1");
	}

	/**
	 * inserts several Key value pairs and gets the Load factor
	 */
	@Test
	public void test003_insert_several_KVPairs_get_load_factor() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			hashTable3.insert(10, "ten");
			hashTable3.insert(20, "twenty");
			hashTable3.insert(30, "thirty");
			if (hashTable3.getLoadFactor() != .3)
				fail("hashTable3 had load factor " + hashTable3.getLoadFactor()
						+ ", hashTable3 should have had load factor .3");
		} catch (Exception e) {
			fail("unexpected exception test003");
		}
	}

	/**
	 * test that load factor is zero for empty table
	 */
	@Test
	public void test004_get_load_factor_of_empty_table() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		try {
			if (hashTable.getLoadFactor() != 0)
				fail("An empty hashTable should have load factor of 0, but instead had a load factor of"
						+ hashTable.getLoadFactor());
		} catch (Exception e) {
			fail("unexpected exception test004");
		}
	}

	/**
	 * Test that correct exception are thrown for inserting null key and duplicate
	 * keys
	 */
	@Test
	public void test005_insert_null_key_insert_duplicate_key() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		try {
			hashTable.insert(null, "null");
			fail("hashTable insert did not throw exception when inserting null key");
		} catch (IllegalNullKeyException e) { /* Expected */
		} catch (Exception e) {
			fail("unexpected exception test 005 (null key)");
		}

		try {
			hashTable.insert(10, "ten");
			hashTable.insert(10, "ten");
			fail("hashTable insert did not throw exception when inserting duplicate key");
		} catch (DuplicateKeyException e) { /* Expected */
		} catch (Exception e) {
			fail("unexpected exception test 005 (duplicate key)" + e.getMessage());
		}
	}

	/**
	 * gets capacity of new empty table
	 */
	@Test
	public void test006_get_capacity_of_empty_hashTable() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		if (hashTable.getCapacity() != 31) {
			fail("default constructor hashTable should have capacity of 31");
		}
	}

	/**
	 * get capacity of expanded table
	 */
	@Test
	public void test007_get_capacity_of_expanded_hashTable() {
		HashTable<Integer, String> hashTable2 = new HashTable<Integer, String>(31, .1);
		try {
			hashTable2.insert(10, "ten");
			hashTable2.insert(12, "twelve");
			hashTable2.insert(15, "fifteen");
			hashTable2.insert(20, "Twenty");
			if (hashTable2.getCapacity() != 63)
				fail("expanded hashTable should have capacity of 62");
		} catch (Exception e) {
			fail("unexpected exception test007");
		}
	}

	/**
	 * insert KVPair into empty hashTable
	 */
	@Test
	public void test008_insert_into_empty_hashTable() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			hashTable3.insert(10, "Ten");
		} catch (Exception e) {
			fail("unexpected exception test008");
		}
	}

	/**
	 * test insert several KVPairs into hashTable
	 */
	@Test
	public void test009_insert_several_objects_into_hashTable() {
		HashTable<Integer, String> hashTable2 = new HashTable<Integer, String>(31, .1);
		try {
			hashTable2.insert(10, "ten");
			hashTable2.insert(11, "Eleven");
			hashTable2.insert(12, "Twelve");
			hashTable2.insert(13, "Thirteen");
			hashTable2.insert(14, "Fourteen");
			System.out.println(hashTable2.getCapacity());
			if (hashTable2.getCapacity() != 63) {
				fail("hashTable is not proper size");
			}
		} catch (Exception e) {
			fail("unexpected exception test009");
		}
	}

	/**
	 * test that remove returns false when trying to remove non-existent objects
	 */
	@Test
	public void test010_remove_non_existent_object() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		try {
			if (hashTable.remove(10))
				fail("if object not found remove method should have returned false");
		} catch (Exception e) {
			fail("unexpected exception test010");
		}
	}

	/**
	 * test that remove throws IllegalNullKeyException when trying to remove a null
	 * key
	 */
	@Test
	public void test011_remove_null_key() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		try {
			hashTable.remove(null);
		} catch (IllegalNullKeyException e) {
			/* expected */
		} catch (Exception e) {
			fail("unexpected exception test011" + e.getMessage());
		}
	}

	/**
	 * test insert works for position 0 in table
	 */
	@Test
	public void test012_insert_at_index_zero() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			// insert at index 0
			hashTable3.insert(0, "zero");
		} catch (Exception e) {
			fail("unexpected exception test012");
		}
	}

	/**
	 * test that table expands to insert next KVpair
	 */
	@Test
	public void test013_table_expand() {
		HashTable<Integer, String> hashTable2 = new HashTable<Integer, String>(31, .1);
		try {
			hashTable2.insert(10, "ten");
			hashTable2.insert(11, "Eleven");
			hashTable2.insert(12, "Twelve");
			hashTable2.insert(13, "Thirteen");
			hashTable2.insert(14, "Fourteen");
		} catch (Exception e) {
			fail("unexpected exception test013");
		}
	}

	/**
	 * test that insert resolve collisions
	 */
	@Test
	public void test014_hashTable_resolve_collisions() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			hashTable3.insert(40, "Fourty");
			hashTable3.insert(10, "Ten");
			hashTable3.insert(20, "Twenty");
			hashTable3.insert(70, "Seventy");
		} catch (Exception e) {
			fail("unexpected exception test014");
		}
	}

	/**
	 * remove item that had collision and tests that objects inserted after due to
	 * linear probing can still be accessed
	 */
	@Test
	public void test015_remove_collided_item_can_access_open_addressing() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			hashTable3.insert(0, "zero");
			hashTable3.insert(10, "Ten");
			hashTable3.remove(0);
			if (!hashTable3.get(10).equals("Ten"))
				fail("hashTable get method returned incorrect value");
		} catch (Exception e) {
			fail("unexpected exception test018");
		}
	}

	/**
	 * test that get throws KeyNotFoundException when key is not found and throws
	 * IllegalNullKeyException when given key is null
	 */
	@Test
	public void test016_get_throws_correct_exceptions() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		try {
			hashTable.get(10);
		} catch (KeyNotFoundException e) {
			/* Expected */
		} catch (Exception e) {
			fail("unexpected exception test016");
		}

		try {
			hashTable.get(null);
		} catch (IllegalNullKeyException e) {
			/* Expected */
		} catch (Exception e) {
			fail("unexpected exception test016");
		}
	}

	/**
	 * test that get returns several items from hashTable
	 */
	@Test
	public void test017_get_returns_correct_values() {
		HashTable<Integer, String> hashTable = new HashTable<Integer, String>();
		try {
			hashTable.insert(10, "Ten");
			hashTable.insert(14, "Fourteen");
			hashTable.insert(25334, "Twenty-Five Thousand and three hundred thirty four");
		} catch (Exception e) {
			fail("unexpected exception test017");
		}
	}

	/**
	 * get item that had collision (not in normal place)
	 */
	@Test
	public void test018_get_displaced_item() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			hashTable3.insert(0, "zero");
			hashTable3.insert(10, "Ten");
			if (!hashTable3.get(10).equals("Ten"))
				fail("hashTable get method returned incorrect value");
		} catch (Exception e) {
			fail("unexpected exception test018");
		}
	}

	/**
	 * Test that negative value keys work
	 */
	@Test
	public void test019_get_negativeOne() {
		HashTable<Integer, String> hashTable3 = new HashTable<Integer, String>(10, .75);
		try {
			hashTable3.insert(-1, "zero");
			hashTable3.insert(10, "Ten");
			if (!hashTable3.get(-1).equals("zero"))
				fail("hashTable get method returned incorrect value");
		} catch (Exception e) {
			fail("unexpected exception test018");
		}
	}
}