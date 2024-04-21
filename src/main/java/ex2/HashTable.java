package ex2;

import java.util.ArrayList;

public class HashTable {
    private int SIZE = 16;
    private int ITEMS = 0;
    private HashEntry[] entries = new HashEntry[SIZE];

    public int count(){
        return this.ITEMS;
    }

    public int size(){
        return this.SIZE;
    }

    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if(entries[hash] == null) {
            entries[hash] = hashEntry;
            ITEMS++;  // Incrementar el contador de ITEMS
        }
        else {
            HashEntry temp = entries[hash];
            while(temp.next != null)
                temp = temp.next;

            temp.next = hashEntry;
            hashEntry.prev = temp;
        }
    }

    public String get(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            while( !temp.key.equals(key))
                temp = temp.next;

            return temp.value;
        }

        return null;
    }

    public void drop(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {

            HashEntry temp = entries[hash];
            while( !temp.key.equals(key))
                temp = temp.next;

            if(temp.prev == null) entries[hash] = null;
            else{
                if(temp.next != null) temp.next.prev = temp.prev;
                temp.prev.next = temp.next;
            }
            ITEMS--;  // Decrementar el contador de ITEMS
        }
    }

    private int getHash(String key) {
        return key.hashCode() % SIZE;
    }

    private class HashEntry {
        String key;
        String value;
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                bucket++;
                continue;
            }

            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    public static void main(String[] args) {
        testPut();
        testGet();
        testDrop();
        testCountAndSize();
    }

    public static void testPut() {
        HashTable ht = new HashTable();
        ht.put("1", "one");
        ht.put("2", "two");
        ht.put("17", "seventeen");  // colisión con "1"
        ht.put("33", "thirty-three");  // colisión con "1" y "17"

        System.out.println("After put:");
        System.out.println(ht.toString());
    }

    public static void testGet() {
        HashTable ht = new HashTable();
        ht.put("1", "one");
        ht.put("2", "two");
        ht.put("17", "seventeen");

        System.out.println("Get value for key '1': " + ht.get("1"));
        System.out.println("Get value for key '17': " + ht.get("17"));
        System.out.println("Get value for key '33': " + ht.get("33"));  // Debería ser null
    }

    public static void testDrop() {
        HashTable ht = new HashTable();
        ht.put("1", "one");
        ht.put("2", "two");
        ht.put("17", "seventeen");

        ht.drop("1");

        System.out.println("After drop:");
        System.out.println(ht.toString());
    }

    public static void testCountAndSize() {
        HashTable ht = new HashTable();
        ht.put("1", "one");
        ht.put("2", "two");
        ht.put("17", "seventeen");

        System.out.println("Count: " + ht.count());  // Debería ser 3
        System.out.println("Size: " + ht.size());  // Debería ser 16
    }
}