// --== CS400 Fall 2023 File Header Information ==--
// Name: Rishabh Jain
// Email: rvjain@wisc.edu
// Group: G18
// TA: Grant Waldow
// Lecturer: Florian

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Implements MapADT using an instance of java.util.HashMap.
 */
public class PlaceholderMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    // use an instance of java.util.HashMap for the functionality of PlaceholderMap
    protected HashMap<KeyType, ValueType> baseMap = new HashMap<>();

    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if (key == null)
            throw new NullPointerException("null keys not allowed");
        if (baseMap.containsKey(key))
            throw new IllegalArgumentException("key " + key + " already present in map");
        baseMap.put(key, value);
    }

    @Override
    public boolean containsKey(KeyType key) {
        if (key == null)
            throw new NullPointerException("null keys not allowed");
        return baseMap.containsKey(key);
    }

    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        if (key == null)
            throw new NullPointerException("null keys not allowed");
        if (baseMap.containsKey(key)) {
            return baseMap.get(key);
        }
        throw new NoSuchElementException("key " + key + " not in map");
    }

    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        if (key == null)
            throw new NullPointerException("null keys not allowed");
        if (baseMap.containsKey(key)) {
            return baseMap.remove(key);
        }
        throw new NoSuchElementException("key " + key + " not in map");
    }

    @Override
    public void clear() {
        baseMap.clear();
    }

    @Override
    public int getSize() {
        return baseMap.size();
    }

    @Override
    public int getCapacity() {
        throw new UnsupportedOperationException("PlaceholderMap does not support the .getCapacity() method");
    }
}
