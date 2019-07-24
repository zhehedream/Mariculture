package com.bryankeiren.fjord;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.java.JavaPlugin;

public class FjordGrassPopulator extends BlockPopulator {
	private final float density = 0.35f;
	private final float grassVsFernDistribution = 0.6f;
	private JavaPlugin plugin = null;

	public FjordGrassPopulator(JavaPlugin _plugin) {
		plugin = _plugin;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		int realZ = chunk.getZ() * 16;
                int realX = chunk.getX() * 16;
		for (int z = 0; z < 16; ++z) {
			for (int x = 0; x < 16; ++x) {
				float rf = random.nextFloat();

				// Do a random-check with the density parameter to see if we should place grass here.
				if (rf <= density) {
					Block highestBlock = world.getHighestBlockAt(realX + x, realZ + z);

					// If the block is a free block...
					if (highestBlock.getType() == Material.AIR) {
						Block highestBlockBelow = world.getBlockAt(realX + x, highestBlock.getY() - 1, realZ + z);

						// ...and we have grass below us.
						if (highestBlockBelow.getType() == Material.GRASS_BLOCK) {
                                                        if(random.nextFloat() < grassVsFernDistribution) highestBlock.setType(Material.GRASS, false);
                                                        else highestBlock.setType(Material.FERN, false);
						}
					}
				}
			}
		}
	}
}
