package ex2;

public class HashTable {
    private int SIZE = 16;
    private int ITEMS = 0;
    private HashEntry[] entries = new HashEntry[SIZE];

    public int count() {
        return this.ITEMS;
    }

    public int size() {
        return this.SIZE;
    }

    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if (entries[hash] == null) {
            entries[hash] = hashEntry;
            ITEMS++;
        } else {
            HashEntry temp = entries[hash];
            while (temp.next != null && !temp.key.equals(key))
                temp = temp.next;

            if (temp.key.equals(key)) {
                temp.value = value;  // Actualitzar el valor si la clau ja existeix
            } else {
                temp.next = hashEntry;
                hashEntry.prev = temp;
                ITEMS++;
            }
        }
    }

    public String get(String key) {
        int hash = getHash(key);
        HashEntry temp = entries[hash];

        while (temp != null) {
            if (temp.key.equals(key))
                return temp.value;
            temp = temp.next;
        }

        return null;
    }

    public void drop(String key) {
        int hash = getHash(key);
        HashEntry temp = entries[hash];

        while (temp != null && !temp.key.equals(key))
            temp = temp.next;

        if (temp == null) return;

        if (temp.prev == null) {
            entries[hash] = temp.next;
        } else {
            temp.prev.next = temp.next;
        }

        if (temp.next != null) {
            temp.next.prev = temp.prev;
        }
        ITEMS--;
    }

    private int getHash(String key) {
        return Math.abs(key.hashCode() % SIZE);
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
        StringBuilder hashTableStr = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            HashEntry entry = entries[i];
            hashTableStr.append("bucket[").append(i).append("] = ");
            while (entry != null) {
                hashTableStr.append(entry.toString()).append(" -> ");
                entry = entry.next;
            }
            hashTableStr.append("null\n");
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
        ht.put("17", "seventeen");
        ht.put("33", "thirty-three");
        ht.put("1", "updated one");
        ht.put("17", "updated seventeen");
        ht.put("17", "updated seventeen again");
        ht.put("17", "updated seventeen yet again");

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
        System.out.println("Get value for key '4': " + ht.get("4"));    // Debería ser null
        System.out.println("Get value for key '2': " + ht.get("2"));
        System.out.println("Get value for key '33': " + ht.get("33"));
        System.out.println("Get value for key '17': " + ht.get("17"));
        System.out.println("Get value for key '1': " + ht.get("1"));
    }

    public static void testDrop() {
        HashTable ht = new HashTable();
        ht.put("1", "one");
        ht.put("2", "two");
        ht.put("17", "seventeen");
        ht.put("33", "thirty-three");

        ht.drop("1");
        ht.drop("17");
        ht.drop("33");

        System.out.println("After drop:");
        System.out.println(ht.toString());
    }

    public static void testCountAndSize() {
        HashTable ht = new HashTable();
        ht.put("1", "one");
        ht.put("2", "two");
        ht.put("17", "seventeen");
        ht.put("33", "thirty-three");

        System.out.println("Count: " + ht.count());
        System.out.println("Size: " + ht.size());

        ht.drop("1");
        ht.drop("17");
        ht.drop("33");

        System.out.println("Count: " + ht.count());
        System.out.println("Size: " + ht.size());
    }
}
