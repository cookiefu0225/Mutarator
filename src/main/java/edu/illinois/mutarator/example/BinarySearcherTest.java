package edu.illinois.mutarator.example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BinarySearcherTest {
    @Test
    public void testBinarySearch() {
        int[] nums1 = new int[]{1, 2, 3, 4, 5, 9, 12, 18};

        BinarySearcher bs = new BinarySearcher();
        int ans1 = bs.binarySearch(nums1, 2);
        int ans2 = bs.binarySearch(nums1, 5);
        int ans3 = bs.binarySearch(nums1, 19);
        int ans4 = bs.binarySearch(nums1, 11);


        assertEquals(1, ans1);
        assertEquals(4, ans2);
        assertEquals(-1, ans3);
        assertEquals(-1, ans4);
    }
}
