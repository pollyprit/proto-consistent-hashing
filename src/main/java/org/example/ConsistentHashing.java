package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class ConsistentHashing {
    // Hash to nodes mapping. The nodes could have any level of detail from its IP, keys-ranges etc.
    private HashMap<Integer, String> nodes = new HashMap<>();

    // This is purely a consistent hashing ring.
    private SortedArrayList sortedRing = new SortedArrayList();

    public ConsistentHashing() {
    }

    public void addNode(String node) {
        int hash = ConsistentHashing.sha256(node);
        sortedRing.add(hash);
        nodes.put(hash, node);
        // TODO: map the keys to the new node
    }

    public void removeNode(String node) {
        int hash = ConsistentHashing.sha256(node);
        sortedRing.remove(hash);
        nodes.remove(hash);
        // TODO: map the keys to the new node
    }

    public String getNode(int key) {
        // int hash = ConsistentHashing.sha256(key);   // key is already hashed
        int nodePos = sortedRing.find(key);
        String node = nodes.get(nodePos);

        return node;
    }

    public void listNodes() {
        System.out.print("Nodes in the ring: ");
        for (int hash : nodes.keySet())
            System.out.print("[" + nodes.get(hash) + "] ");
    }

    public static int sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            String str = hexString.toString().substring(0, 4);    // take only first 4 characters, 2^16 = 65536
            int hash = Integer.parseInt(str, 16);

            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
