package org.example;

import java.util.ArrayList;
import java.util.Collections;

// Circular sorted array list
public class SortedArrayList {
    private ArrayList<Integer> list;

    public SortedArrayList() {
        list = new ArrayList<>();
    }

    public int find(int value) {
        int idx = lowerBound(value);

        if (idx == list.size())
            idx = 0;   // wrap around
        return list.get(idx);
    }

    public void add(int value) {
        int idx = Collections.binarySearch(list, value);
        if (idx < 0)
            idx = -(idx + 1);

        list.add(idx, value);
    }

    public void remove(int value) {
        int idx = Collections.binarySearch(list, value);
        if (idx >= 0)
            list.remove(idx);
    }

    private int lowerBound(int target) {
        int low = 0;
        int high = list.size();
        int index = high;

        while (low < high) {
            int mid = low + (high - low) / 2; // Calculate mid to prevent overflow

            if (list.get(mid) >= target) {
                // mid is a potential answer, or we need to search in the left half
                index = mid;
                high = mid; // Reduce search space to the left
            }
            else {
                // mid is less than target, so we need to search in the right half
                low = mid + 1; // Increase search space to the right
            }
        }
        return index;
    }
}

