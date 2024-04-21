package ex3;

import java.util.ArrayList;
import java.util.List;

public class HashTable {
    private static final int SIZE = 10; // Pots canviar aquesta mida segons les teves necessitats
    private Bucket[] buckets = new Bucket[SIZE];

    public void put(int key, String value) {
        int index = getIndex(key);
        ensureBucketInitialized(index);
        buckets[index].add(new HashEntry(key, value));
    }

    public String get(int key) {
        int index = getIndex(key);
        Bucket bucket = getBucket(index);
        HashEntry entry = bucket != null ? bucket.get(key) : null;
        return entry != null ? entry.value : null;
    }

    public void drop(int key) {
        int index = getIndex(key);
        Bucket bucket = getBucket(index);
        if (bucket != null) {
            bucket.remove(key);
        }
    }

    private int getIndex(int key) {
        return key % SIZE;
    }

    private void ensureBucketInitialized(int index) {
        if (buckets[index] == null) {
            buckets[index] = new Bucket();
        }
    }

    private Bucket getBucket(int index) {
        return buckets[index];
    }

    private static class Bucket {
        private List<HashEntry> entries = new ArrayList<>();

        public void add(HashEntry entry) {
            entries.add(entry);
        }

        public HashEntry get(int key) {
            for (HashEntry entry : entries) {
                if (entry.key == key) {
                    return entry;
                }
            }
            return null;
        }

        public void remove(int key) {
            HashEntry entryToRemove = null;
            for (HashEntry entry : entries) {
                if (entry.key == key) {
                    entryToRemove = entry;
                    break;
                }
            }
            if (entryToRemove != null) {
                entries.remove(entryToRemove);
            }
        }
    }

    private static class HashEntry {
        private final int key;
        private final String value;

        public HashEntry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        HashTable ht = new HashTable();

        // Prova put i get
        ht.put(1, "A");
        ht.put(2, "B");
        ht.put(11, "C"); // Col·lisió amb la clau 1
        ht.put(12, "D"); // Col·lisió amb la clau 2
        ht.put(21, "E"); // Col·lisió amb la clau 1

        System.out.println("Valor per clau 1: " + ht.get(1));  // Esperat: "A"
        System.out.println("Valor per clau 2: " + ht.get(2));  // Esperat: "B"
        System.out.println("Valor per clau 11: " + ht.get(11)); // Esperat: "C"
        System.out.println("Valor per clau 12: " + ht.get(12)); // Esperat: "D"
        System.out.println("Valor per clau 21: " + ht.get(21)); // Esperat: "E"
        System.out.println("Valor per clau inexistent 3: " + ht.get(3)); // Esperat: null

        // Prova drop
        ht.drop(1);
        System.out.println("Valor per clau 1 després de drop: " + ht.get(1)); // Esperat: null

        // Prova count i size
        System.out.println("Nombre d'elements després de drop: " + count(ht)); // Esperat: 4
        System.out.println("Tamany de la taula: " + size(ht)); // Esperat: 10
    }

    private static int count(HashTable ht) {
        int count = 0;
        for (Bucket bucket : ht.buckets) {
            if (bucket != null) {
                count += bucket.entries.size();
            }
        }
        return count;
    }

    private static int size(HashTable ht) {
        return ht.SIZE;
    }
}
