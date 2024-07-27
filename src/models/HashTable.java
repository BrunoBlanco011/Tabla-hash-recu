package models;

import java.util.LinkedList;

public class HashTable {
    private LinkedList<Entry>[] tabla;
    private int size;

    public HashTable(int capacidad) {
        tabla = new LinkedList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new LinkedList<>();
        }
        size = 0;
    }

    public boolean push(String key, Business value, boolean useHashFunction1) {
        int index = useHashFunction1 ? metodoHash1(key) : metodoHash2(key);
        tabla[index].add(new Entry(key, value));
        size++;
        return true;
    }

    public Business get(String key, boolean useHashFunction1) {
        int index = useHashFunction1 ? metodoHash1(key) : metodoHash2(key);
        for (Entry entry : tabla[index]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public LinkedList<Business> getAll() {
        LinkedList<Business> allBusinesses = new LinkedList<>();
        for (LinkedList<Entry> bucket : tabla) {
            for (Entry entry : bucket) {
                allBusinesses.add(entry.getValue());
            }
        }
        return allBusinesses;
    }

    // Jenkins hash
    private int metodoHash1(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash) % tabla.length;
    }

    // Hash de suma y desplazamiento
    private int metodoHash2(String key) {
        final int FNV_PRIME = 0x01000193;
        final int FNV_OFFSET_BASIS = 0x811c9dc5;
        int hash = FNV_OFFSET_BASIS;
        for (int i = 0; i < key.length(); i++) {
            hash ^= key.charAt(i);
            hash *= FNV_PRIME;
        }
        return Math.abs(hash) % tabla.length;
    }
}