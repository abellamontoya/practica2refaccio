package ex2;

import java.util.ArrayList;

public class HashTable {
    private int SIZE = 10;  // Mida de la taula de hash
    private HashEntry[] entries = new HashEntry[SIZE];

    public void put(int key, Object value) {
        int hash = key % SIZE;

        HashEntry entry = new HashEntry(key, value);

        if (entries[hash] == null) {
            entries[hash] = entry;
        } else {
            HashEntry temp = entries[hash];
            while (temp.next != null && temp.key != key)
                temp = temp.next;

            if (temp.key == key) {
                temp.value = value;  // Actualitzar el valor si la clau ja existeix
            } else {
                temp.next = entry;
            }
        }
    }

    public Object get(int key) {
        int hash = key % SIZE;
        HashEntry temp = entries[hash];

        while (temp != null) {
            if (temp.key == key)
                return temp.value;
            temp = temp.next;
        }
        return null;  // Retorna null si no es troba
    }

    public void drop(int key) {
        int hash = key % SIZE;
        HashEntry temp = entries[hash];
        HashEntry prev = null;

        while (temp != null && temp.key != key) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null) return;

        if (prev == null) {
            entries[hash] = temp.next;
        } else {
            prev.next = temp.next;
        }
    }

    public int count() {
        int count = 0;
        for (HashEntry entry : entries) {
            HashEntry temp = entry;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
        }
        return count;
    }

    public int size() {
        return SIZE;
    }

    @Override
    public String toString() {
        StringBuilder hashTableStr = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            HashEntry entry = entries[i];
            hashTableStr.append("[")
                    .append(i)
                    .append("] -> ");
            while (entry != null) {
                hashTableStr.append("<")
                        .append(entry.key)
                        .append(", ")
                        .append(entry.value)
                        .append("> ");
                entry = entry.next;
            }
            hashTableStr.append("\n");
        }
        return hashTableStr.toString();
    }

    private static class HashEntry {
        int key;
        Object value;
        HashEntry next;

        public HashEntry(int key, Object value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        HashTable ht = new HashTable();

        // Proves per "put"
        ht.put(99, "p1");
        ht.put(21, "p2");
        ht.put(2, "p3");
        ht.put(2, "p4");
        ht.put(21, "p5");

        System.out.println("Després de put:");
        System.out.println(ht);

        // Proves per "get"
        System.out.println("Obtenir 99: " + ht.get(99));  // p1
        System.out.println("Obtenir 2: " + ht.get(2));    // p4

        // Proves per "drop"
        ht.drop(99);
        ht.drop(2);

        System.out.println("Després de drop:");
        System.out.println(ht);

        // Comprovar count i size
        System.out.println("Count: " + ht.count());  // Esperat: 3
        System.out.println("Size: " + ht.size());    // Esperat: 10
    }
}
