package org.example;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ConsistentHashing ch = new ConsistentHashing();
        Random random = new Random();

        ch.addNode("10.0.0.1");
        ch.addNode("10.0.0.2");
        ch.addNode("10.0.0.3");

        // Generate random keys
        ArrayList<Integer> keys = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            keys.add(random.nextInt(65536));

        System.out.println("Initial mapping of keys to nodes:");
        checkKeysToNodes(ch, keys);

        // Add 2 new nodes
        ch.addNode("192.168.0.0");
        ch.addNode("172.16.0.0");

        // Check keys to nodes mapping again
        System.out.println("Mapping of keys to nodes after 2 scale-up:");
        checkKeysToNodes(ch, keys);

        ch.removeNode("10.0.0.2");
        ch.removeNode("172.16.0.0");

        // Check keys to nodes mapping again
        System.out.println("Mapping of keys to nodes after scale-down:");
        checkKeysToNodes(ch, keys);
    }

    public static void checkKeysToNodes(ConsistentHashing ch, ArrayList<Integer> keys) {
        System.out.println("\n");
        ch.listNodes();
        System.out.println();
        for (int key : keys)
            System.out.println("key = " + key + ", node = " + ch.getNode(key));
    }
}