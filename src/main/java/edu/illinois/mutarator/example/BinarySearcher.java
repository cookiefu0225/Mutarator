package edu.illinois.mutarator.example;

public class BinarySearcher {
    /**
     *
     * @param nums
     * A sorted array that is going to be searched
     * @param target
     * Target number
     * @return
     * The index of the target number. If the number doesn't exist, return -1
     */
    public int binarySearch(int[] nums, int target) {
        int len = nums.length;
        return searchHelper(nums, target, 0, len-1);
    }

    /**
     *
     * @param nums
     * @param target
     * @param s
     * Start point
     * @param e
     * End point
     * @return
     */
    private int searchHelper(int[] nums, int target, int s, int e) {
        if (s == e) {
            if (nums[s] != target) {
                return -1;
            } else {
                return s;
            }
        }

        int mid = (s+e) / 2;
        if (target > nums[mid]) {
            return searchHelper(nums, target, mid+1, e);
        } else if (target < nums[mid]) {
            return searchHelper(nums, target, s, mid);
        } else {
            return mid;
        }
    }
}
