import java.util.Iterator;
import java.util.LinkedList;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

    public HashTable() {
        this.slots = new LinkedList[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
        this.count = 0;
    }

    public HashTable(int capacity) {
        this.slots = new LinkedList[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    public void add(K key, V value) {
        growIfNeeded();
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] == null) {
            slots[slotNumber] = new LinkedList<>();
        }

        for (KeyValue<K, V> kv : slots[slotNumber]) {
            if (kv.getKey().equals(key)) {
                throw new IllegalArgumentException("Duplicate key not allowed");
            }
        }

        slots[slotNumber].add(new KeyValue<>(key, value));
        count++;
    }

    private int findSlotNumber(K key) {
        int hashCode = key.hashCode();
        return hashCode % capacity;
    }

    private void growIfNeeded() {
        if ((double) (count + 1) / capacity > LOAD_FACTOR) {
            grow();
        }
    }

    private void grow() {
        int newCapacity = capacity * 2;
        LinkedList<KeyValue<K, V>>[] newSlots = new LinkedList[newCapacity];

        for (LinkedList<KeyValue<K, V>> slot : slots) {
            if (slot != null) {
                for (KeyValue<K, V> kv : slot) {
                    int newSlotNumber = kv.getKey().hashCode() % newCapacity;
                    if (newSlots[newSlotNumber] == null) {
                        newSlots[newSlotNumber] = new LinkedList<>();
                    }
                    newSlots[newSlotNumber].add(kv);
                }
            }
        }

        slots = newSlots;
        capacity = newCapacity;
    }

    public int size() {
        return count;
    }

    public int capacity() {
        return capacity;
    }

    public boolean addOrReplace(K key, V value) {
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> kv : slots[slotNumber]) {
                if (kv.getKey().equals(key)) {
                    kv.setValue(value);
                    return true;
                }
            }
        }
        add(key, value);
        return false;
    }

    public V get(K key) {
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> kv : slots[slotNumber]) {
                if (kv.getKey().equals(key)) {
                    return kv.getValue();
                }
            }
        }
        return null;
    }

    public KeyValue<K, V> find(K key) {
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> kv : slots[slotNumber]) {
                if (kv.getKey().equals(key)) {
                    return kv;
                }
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return find(key) != null;
    }

    public boolean remove(K key) {
        int slotNumber = findSlotNumber(key);
        if (slots[slotNumber] != null) {
            Iterator<KeyValue<K, V>> iterator = slots[slotNumber].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> kv = iterator.next();
                if (kv.getKey().equals(key)) {
                    iterator.remove();
                    count--;
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        slots = new LinkedList[INITIAL_CAPACITY];
        count = 0;
        capacity = INITIAL_CAPACITY;
    }

    public Iterable<K> keys() {
        LinkedList<K> keys = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> slot : slots) {
            if (slot != null) {
                for (KeyValue<K, V> kv : slot) {
                    keys.add((K) kv.getKey());
                }
            }
        }
        return keys;
    }

    public Iterable<V> values() {
        LinkedList<V> values = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> slot : slots) {
            if (slot != null) {
                for (KeyValue<K, V> kv : slot) {
                    values.add(kv.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        LinkedList<KeyValue<K, V>> allEntries = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> slot : slots) {
            if (slot != null) {
                allEntries.addAll(slot);
            }
        }
        return allEntries.iterator();
    }
}
