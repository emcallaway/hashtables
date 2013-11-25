package taojava.util;

import java.io.PrintWriter;

import java.util.Random;

/**
 * A simple implementation of hash tables.
 *
 * @author Samuel A. Rebelsky
 */
public class HashTable<K,V> {
    // +-------+-----------------------------------------------------------
    // | Notes |
    // +-------+

/*
    We use linear probing to handle collisions.  (Well, we *will* use
    linear probing, once the table is finished.)

    We expand the hash table when the load factor is greater than
    LOAD_FACTOR (see constants below).

    For experimentation and such, we allow the client to supply a
    Reporter that is used to report behind-the-scenes work, such as
    calls to expand the table.

    Bugs to squash.
      [ ] Doesn't check for repeated keys in set.
      [ ] Doesn't handle collisions.
      [ ] Doesn't check for matching key in get  (Does it need to?).
      [ ] The `expand` method is not implemented.
      [ ] The `remove` method is not implemented.

    Features to add.
      [ ] An iterator for the values.
      [ ] An iterator for the keys.
 */

    // +-----------+-------------------------------------------------------
    // | Constants |
    // +-----------+

    /**
     * The load factor for expanding the table.
     */
    static final double LOAD_FACTOR = 0.5;

    // +--------+----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The number of values currently stored in the hash table.
     * We use this to determine when to expand the hash table.
     */
    int size = 0;

    /**
     * The array that we use to store the key/value pairs.  (We
     * use an array, rather than a vector, because we want to
     * control expansion.)
     */
    Object[] pairs;

    /**
     * An optional reporter to let us observe what the hash table
     * is doing.
     */
    Reporter reporter;

    /**
     * Do we report basic calls?
     */
    boolean REPORT_BASIC_CALLS = false;

    /**
     * To help avoid some DoS attacks, we use a random number generator
     * when we expand the size of the hash table.
     */
    Random rand;

    // +--------------+----------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new hash table.
     */
    public HashTable() {
        this.rand = new Random();
        this.clear();
        this.reporter = null;
    } // HashTable

    /**
     * Create a new hash table that reports activities using a
     * reporter.
     */
    public HashTable(Reporter reporter) {
        this();
        this.reporter = reporter;
    } // HashTable(Reporter)

    // +-----------+-------------------------------------------------------
    // | Observers |
    // +-----------+

    /**
     * Dump the hash table.
     */
    public void dump(PrintWriter pen) {
        pen.print("{");
        int printed = 0;        // Number of elements printed
        for (int i = 0; i < this.pairs.length; i++) {
            @SuppressWarnings("unchecked")
            KVPair pair = (KVPair) this.pairs[i];
            if (pair != null) {
                pen.print(i + ":" + pair.key + "(" +
                        pair.key.hashCode() + "):" +
                        pair.value);
                if (++printed < this.size) {
                    pen.print(", ");
                } // if the number printed is less than the size
            } // if the current element is not null
        } // for
        pen.println("}");
    } // dump(PrintWriter)

    /**
     * Get the value for a particular key.
     */
    public V get(K key) throws Exception {
        int index = find(key);
        @SuppressWarnings("unchecked")
        KVPair pair = (KVPair) pairs[index];
        if (pair == null) {
            if (reporter != null) { 
                reporter.report("get(" + key + ") failed"); 
            } // if reporter != null
            throw new Exception("Invalid key: " + key);
        } else {
            if (REPORT_BASIC_CALLS && (reporter != null)) {
                reporter.report("get(" + key + ") => " + pair.value);
            } // if reporter != null
            return pair.value;
        } // get
    } // get(K)

    /**
     * Get the size of the dictionary - the number of values stored.
     */
    public int size() {
        return this.size;
    } // size()

    // +----------+--------------------------------------------------------
    // | Mutators |
    // +----------+

    /**
     * Clear the whole dictionary.
     */
    public void clear() {
        this.pairs = new Object[41];
        this.size = 0;
    } // clear()

    /**
     * Remove a key/value pair.
     */
    public void remove(K key) {
        // STUB
    } // remove(K)

    /**
     * Set a value.
     */
    public void set(K key, V value) {
        // If there are too many entries, expand the table.
        if (this.size > (this.pairs.length * LOAD_FACTOR)) {
            expand();
        } // if there are too many entries
        // Find out where the key belongs and put the pair there.
        int index = find(key);
        this.pairs[index] = new KVPair(key, value);
        // Report activity, if appropriate
        if (REPORT_BASIC_CALLS && (reporter != null)) {
            reporter.report("pairs[" + index + "] = " + key + ":" + value);
        } // if reporter != null
        // Note that we've incremented the size.
        ++this.size;
    } // set(K,V)

    // +------+------------------------------------------------------------
    // | Misc |
    // +------+

    /**
     * Should we report basic calls?
     */
    public void reportBasicCalls(boolean report) {
        REPORT_BASIC_CALLS = report;
    } // reportBasicCalls

    // +---------+---------------------------------------------------------
    // | Helpers |
    // +---------+

    /**
     * Expand the size of the table.
     */
    void expand() {
        // Remember the old table.
        Object[] old = this.pairs;
        // Figure out the size of the new table.
        int newSize = 2*this.pairs.length + rand.nextInt(10);
        if (reporter != null) {
            reporter.report("Expanding to " + newSize + " elements.");
        } // if reporter != null
        // Create a new table of that size.
        //   STUB
        // Move all the values from the old table to their appropriate
        // location in the new table.
        //   STUB
    } // expand()

    /**
     * Find the index of the entry with a given key.  If there is no such
     * entry, return the index of an entry we can use to store that key.
     */
    int find(K key) {
        return Math.abs(key.hashCode()) % this.pairs.length;
    } // find(K)

    // +---------------+---------------------------------------------------
    // | Inner Classes |
    // +---------------+

    /**
     * An easy way to hold a key/value pair.
     */
    class KVPair {
        K key;
        V value;
        KVPair(K key, V value) {
            this.key = key;
            this.value = value;
        } // KVPair(K,V)
    } // class KVPair

} // class HashTable<K,V>
