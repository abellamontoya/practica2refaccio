package ex4;

import java.util.ArrayList;
import java.util.List;

public class HashTable<K, V> {
    public static final int SIZE = 10;
    public List<Bucket<K, V>> buckets;

    public HashTable() {
        this.buckets = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            buckets.add(null);
        }
    }

    // Canvi: Abans, els valors eren de tipus String, ara són de tipus Object per permetre qualsevol tipus de dades.
    public void put(K key, V value) {
        int index = key.hashCode() % SIZE;
        if (buckets.get(index) == null) {
            buckets.set(index, new Bucket<K, V>());
        }

        Bucket<K, V> bucket = buckets.get(index);
        bucket.put(key, value);
    }

    // Canvi: Abans, retornava un String, ara pot retornar qualsevol tipus d'Object.
    public V get(K key) {
        int index = key.hashCode() % SIZE;
        if (buckets.get(index) == null) {
            return null;
        }

        Bucket<K, V> bucket = buckets.get(index);
        return bucket.get(key);
    }

    // Canvi: La funció drop ara esborra el valor del bucket associat a la clau donada.
    public void drop(K key) {
        int index = key.hashCode() % SIZE;
        if (buckets.get(index) != null) {
            Bucket<K, V> bucket = buckets.get(index);
            bucket.drop(key);
        }
    }

    // Canvi: Compte el total d'elements a tots els buckets.
    public int count() {
        int count = 0;
        for (Bucket<K, V> bucket : buckets) {
            if (bucket != null) {
                count += bucket.entries.size();
            }
        }
        return count;
    }

    // Canvi: Retorna la constant SIZE per indicar el tamany de la taula.
    public int size() {
        return SIZE;
    }

    public static void main(String[] args) {
        HashTable<Integer, Object> ht = new HashTable<>();

        // Prova put i get amb diferents tipus de dades
        ht.put(1, "A");
        ht.put(2, 2);
        ht.put(11, 3.14); // Double
        ht.put(12, true); // Boolean
        ht.put(21, 'C'); // Char

        System.out.println("Valor per clau 1: " + ht.get(1));  // Esperat: "A"
        System.out.println("Valor per clau 2: " + ht.get(2));  // Esperat: 2
        System.out.println("Valor per clau 11: " + ht.get(11)); // Esperat: 3.14
        System.out.println("Valor per clau 12: " + ht.get(12)); // Esperat: true
        System.out.println("Valor per clau 21: " + ht.get(21)); // Esperat: 'C'
        System.out.println("Valor per clau inexistent 3: " + ht.get(3)); // Esperat: null

        // Prova drop
        ht.drop(1);
        System.out.println("Valor per clau 1 després de drop: " + ht.get(1)); // Esperat: null

        // Prova count i size
        System.out.println("Nombre d'elements després de drop: " + ht.count()); // Esperat: 4
        System.out.println("Tamany de la taula: " + ht.size()); // Esperat: 10
    }
}

class Bucket<K, V> {
    public List<HashEntry<K, V>> entries;

    public Bucket() {
        this.entries = new ArrayList<>();
    }

    public void put(K key, V value) {
        HashEntry<K, V> entry = new HashEntry<>(key, value);
        entries.add(entry);
    }

    public V get(K key) {
        for (HashEntry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void drop(K key) {
        entries.removeIf(entry -> entry.key.equals(key));
    }
}

class HashEntry<K, V> {
    public K key;
    public V value;

    public HashEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
