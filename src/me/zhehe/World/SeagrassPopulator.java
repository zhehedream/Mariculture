/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.World;

import java.util.Random;
import static org.bukkit.Bukkit.createBlockData;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BlockPopulator;

/**
 *
 * @author zhiqiang.hao
 */
public class SeagrassPopulator extends BlockPopulator {
    	@Override
	public void populate(final World world, final Random random, final Chunk source) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				final int chance = random.nextInt(100);
				if (chance < 33) {
					Block handle = world.getHighestBlockAt(x + source.getX() * 16, z + source.getZ() * 16);
					if(handle.getRelative(BlockFace.DOWN).getType().equals(Material.WATER)) {
                                            handle = handle.getRelative(BlockFace.DOWN);
                                            while(handle.getType() == Material.WATER) {
                                                handle = handle.getRelative(BlockFace.DOWN);
                                            }
                                            handle = handle.getRelative(BlockFace.UP);
                                            if(world.getBiome(handle.getX(), handle.getZ()) == Biome.COLD_OCEAN) {
                                                handle.setBlockData(createBlockData(Material.KELP_PLANT), false);
                                                continue;
                                            }
                                            if(random.nextInt(10) > 2) {
                                                handle.setBlockData(createBlockData(Material.SEAGRASS), false);
                                            } else {
                                                if(handle.getRelative(BlockFace.UP).getType() == Material.WATER) {
                                                    BlockData data = createBlockData(Material.TALL_SEAGRASS);
                                                    ((Bisected)data).setHalf(Bisected.Half.BOTTOM);
                                                    handle.setBlockData(data, false);

                                                    handle = handle.getRelative(BlockFace.UP);
                                                    data = createBlockData(Material.TALL_SEAGRASS);
                                                    ((Bisected)data).setHalf(Bisected.Half.TOP);
                                                    handle.setBlockData(data, false);
                                                }
                                            }
                                        }
				}
			}
		}
	}

}
