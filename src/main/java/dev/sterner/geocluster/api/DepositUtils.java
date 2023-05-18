package dev.sterner.geocluster.api;

import dev.sterner.geocluster.Geocluster;
import dev.sterner.geocluster.GeoclusterConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.random.RandomGenerator;

public class DepositUtils {

    private static HashSet<BlockState> defaultMatchersCached = null;

    @Nullable
    public static BlockState pick(HashMap<BlockState, Float> map, float totl, Random random) {
        float rng = random.nextFloat();
        for (Map.Entry<BlockState, Float> e : map.entrySet()) {
            float wt = e.getValue();
            if (rng < wt) {
                return e.getKey();
            }
            rng -= wt;
        }

        Geocluster.LOGGER.error("Could not reach decision on block to place at Utils#pick");
        return null;
    }

    public static HashSet<BlockState> getDefaultMatchers() {
        // If the cached data isn't there yet, load it.
        if (defaultMatchersCached == null) {
            defaultMatchersCached = new HashSet<>();
            GeoclusterConfig.DEFAULT_REPLACEMENT_MATS.forEach(s -> {
                Block block = Registry.BLOCK.get(new Identifier(s));
                if (block == null || !addDefaultMatcher(block)) {
                    Geocluster.LOGGER.warn(String.format(s + "&s is not a valid block. Please verify.", s));
                }
            });
        }

        return (HashSet<BlockState>) defaultMatchersCached.clone();
    }

    public static boolean addDefaultMatcher(Block block) {
        BlockState defaultState = block.getDefaultState();
        if (!defaultState.isAir()) {
            defaultMatchersCached.add(defaultState);
            return true;
        }
        return false;
    }

    /**
     * Returns true if a and b are within epsilon of each other, where epsilon is the minimum
     * representable value by a 32-bit floating point number.
     */
    public static boolean nearlyEquals(float a, float b) {
        return Math.abs(a - b) <= Float.MIN_VALUE;
    }
}