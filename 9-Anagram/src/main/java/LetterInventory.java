import java.util.*;

/**
 * A LetterInventory object represents the count of each letter A-Z found in
 * a given String.
 *
 * @author Stuart Reges and Marty Stepp
 * @version February 6, 2010
 */
public class LetterInventory implements Comparable<LetterInventory> {
    private static final int MAX = 'z' - 'a' + 1;  // # of letters (26)
    private static final Map<String, int[]> ALL_COUNTS = new HashMap<String, int[]>();
    private static int INSTANCE_COUNT = 0;
    
    private int[] counts;   // hack: last element of counts stores size
                            // (so the array contains the object's full state)

    /**
     * Returns the number of unique LetterInventory objects that have been created.
     */
    public static int getInstanceCount() {
        return INSTANCE_COUNT;
    }
    
    /**
     * Resets to 0 the count of how many LetterInventory objects have been created.
     */
    public static void resetInstanceCount() {
        INSTANCE_COUNT = 0;
    }
    
    /**
     * Erases this class's internal cache of LetterInventory objects created.
     */
    public static void clearCache() {
        ALL_COUNTS.clear();
    }
    
    /**
     * Constructs a letter inventory with the same counts of letters as
     * the given inventory.
     * @param s The inventory to clone.
     * @throws NullPointerException if the inventory passed is null.
     */
    public LetterInventory(LetterInventory other) {
        counts = other.counts;
    }
    
    /**
     * Constructs a letter inventory to count the letters in the given string.
     * @param s The string to inventory.
     * @throws NullPointerException if the string passed is null.
     */
    public LetterInventory(String s) {
        if (s == null) {
            throw new NullPointerException("constructor's parameter cannot be null");
        }
        if (ALL_COUNTS.containsKey(s)) {
            counts = ALL_COUNTS.get(s);
        } else {
            INSTANCE_COUNT++;
            
            counts = new int[MAX + 1];
            s = s.toLowerCase(); 
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if ('a' <= ch && ch <= 'z') {
                    counts[ch - 'a']++;
                    counts[MAX]++;
                }
            }
            ALL_COUNTS.put(s, counts);
        }
    }
    
    /**
     * Adds the letters in the given inventory to those in this one.
     * For example, adding inventories [ehllo] and [hhio] yields [ehhhillo].
     * @param other the inventory to add.
     * @throws NullPointerException if the inventory passed is null.
     */
    public void add(LetterInventory other) {
        counts = arrayCopy(counts);
        for (int i = 0; i < MAX; i++) {
            counts[i] += other.counts[i];
        }
        counts[MAX] += other.size();
    }
    
    /**
     * Adds the letters in the given string to those in this inventory.
     * For example, adding inventories [ehllo] and "hi ho" yields [ehhhillo].
     * @param s the string to add.
     * @throws NullPointerException if the string passed is null.
     */
    public void add(String s) {
        add(new LetterInventory(s));
    }

    /**
     * Compares this inventory to the given other one by the alphabetical
     * order of their <code>toString</code> representations.
     * In other words, [bbcfy] &lt; [bdfy] &lt; [gil] &lt; [xyz].
     * @param other the inventory to compare against.
     * @return &lt; 0 if this inventory comes "before" the other; 
     *              0 if they contain the same letters; or
     *              1 if this inventory comes "after" the other one.
     * @throws NullPointerException if the inventory passed is null.
     */
    public int compareTo(LetterInventory other) {
        return toString().compareTo(other.toString());
    }
    
    /**
     * Returns true if this inventory contains all of the letters in the given
     * inventory in at least as large of a count.
     * For example, [ehhhillo] contains [ehho].
     * @param other The other inventory to examine.
     * @return true if this inventory contains all of the letters in the given
     * inventory, otherwise false.
     * @throws NullPointerException if the inventory passed is null.
     */
    public boolean contains(LetterInventory other) {
        for (int i = MAX; i >= 0; i--) {
            if (counts[i] < other.counts[i]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns true if this inventory contains all of the letters in the given
     * string in at least as large of a count.
     * For example, [ehhhillo] contains "he ho".
     * @param s The string to examine.
     * @return true if this inventory contains all of the letters in the given
     * inventory, otherwise false.
     * @throws NullPointerException if the string passed is null.
     */
    public boolean contains(String s) {
        return contains(new LetterInventory(s));
    }
    
    /**
     * Returns whether o refers to a LetterInventory object with equal counts
     * of letters to this LetterInventory.
     * @param o the object to compare against.
     * @return true if o refers to a LetterInventory object with equal counts
     * of letters to this LetterInventory, otherwise false.
     */
    public boolean equals(Object o) {
        if (o instanceof LetterInventory) {
            LetterInventory other = (LetterInventory) o;
            return compareTo(other) == 0;
        } else {
            return false;
        }
    }
    
    /**
     * Returns a mostly-unique integer code used by hash-based collections.
     * @return the integer hash code.
     */
    public int hashCode() {
        // add up all the counts times various odd numbers
        int code = 0;
        int multiplier = 37;
        for (int i = 0; i < MAX; i++) {
            code += counts[i] * multiplier * ('a' + i);
            multiplier += 58;
        }
        return code;
    }

    /**
     * Returns whether there are no letters in this inventory.
     * @return true if there are no letters in this inventory, otherwise false
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the total number of letters in this inventory.
     * @return the total number of letters in this inventory (0 if the
     * inventory is empty).
     */
    public int size() {
        return counts[MAX];
    }
    
    /**
     * Removes the letters in the given inventory from this one.
     * For example, [ehhhillo] minus [ehho] yields [hill].
     * @param other the inventory to subtract.
     * @throws IllegalArgumentException if the letters in the other inventory
     * are not contained in this one.
     * @throws NullPointerException if the inventory passed is null.
     */
    public void subtract(LetterInventory other) {
        counts = arrayCopy(counts);
        for (int i = 0; i < MAX; i++) {
            counts[i] -= other.counts[i];
            checkCount(counts[i], (char) ('a' + i), other);
        }
        counts[MAX] -= other.size();
    }

    /**
     * Removes the letters in the given string from this one.
     * For example, [ehhhillo] minus "he ho" yields [hill].
     * @param s the string to subtract.
     * @throws IllegalArgumentException if the letters in the string
     * are not contained in this one.
     * @throws NullPointerException if the string passed is null.
     */
    public void subtract(String s) {
        subtract(new LetterInventory(s));
    }
    
    /**
     * Returns a string representation of this inventory, such as "[ehhhillo]".
     * @return a string representation of this inventory, such as "[ehhhillo]", or "[]" for an empty inventory.
     */
    public String toString() {
        StringBuilder result = new StringBuilder(27);
        result.append("[");
        for (int i = 0; i < MAX; i++) {
            for (int j = counts[i]; j > 0; j--) {
                result.append((char) ('a' + i));
            }
        }
        result.append("]");
        return result.toString();
    }
    
    private int[] arrayCopy(int[] a) {
        int[] a2 = new int[a.length];
        System.arraycopy(a, 0, a2, 0, a.length);
        INSTANCE_COUNT++;
        return a2;
    }
    
    // throws IllegalArgumentException if count of character c is negative
    private void checkCount(int count, char c, Object other) {
        if (count < 0) {
            throw new IllegalArgumentException("\"" + this + "\" does not contain enough '" + c + "' to subtract \"" + other + "\"");
        }
    }
}
