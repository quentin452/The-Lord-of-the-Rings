package lotr.common.world.genlayer;

import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class LOTRIntCache {
    public static final int MAX_BIOME_ID = 255;

    public static LOTRIntCache SERVER = new LOTRIntCache();
    public static LOTRIntCache CLIENT = new LOTRIntCache();
    public int intCacheSize = 256;
    public List<int[]> freeSmallArrays = new ArrayList<>();
    public List<int[]> inUseSmallArrays = new ArrayList<>();
    public List<int[]> freeLargeArrays = new ArrayList<>();
    public List<int[]> inUseLargeArrays = new ArrayList<>();

    public static LOTRIntCache get(World world) {
        if (!world.isRemote) {
            return SERVER;
        }
        return CLIENT;
    }

    public String getCacheSizes() {
        return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated: " + inUseSmallArrays.size();
    }

    public int[] getIntArray(int size) {
        if (size <= MAX_BIOME_ID) {
            if (freeSmallArrays.isEmpty()) {
                int[] ints = new int[MAX_BIOME_ID + 1];
                inUseSmallArrays.add(ints);
                return ints;
            }
            int[] ints = freeSmallArrays.remove(freeSmallArrays.size() - 1);
            inUseSmallArrays.add(ints);
            return ints;
        }
        if (size > intCacheSize) {
            intCacheSize = size;
            freeLargeArrays.clear();
            inUseLargeArrays.clear();
            int[] ints = new int[intCacheSize];
            inUseLargeArrays.add(ints);
            return ints;
        }
        if (freeLargeArrays.isEmpty()) {
            int[] ints = new int[intCacheSize];
            inUseLargeArrays.add(ints);
            return ints;
        }
        int[] ints = freeLargeArrays.remove(freeLargeArrays.size() - 1);
        inUseLargeArrays.add(ints);
        return ints;
    }

    public void resetIntCache() {
        if (!inUseLargeArrays.isEmpty()) {
            freeLargeArrays.addAll(inUseLargeArrays);
            inUseLargeArrays.clear();
        }

        if (!inUseSmallArrays.isEmpty()) {
            freeSmallArrays.addAll(inUseSmallArrays);
            inUseSmallArrays.clear();
        }
    }
}
