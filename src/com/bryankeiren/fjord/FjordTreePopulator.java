package com.bryankeiren.fjord;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class FjordTreePopulator extends BlockPopulator 
{	
	private final float minDensity = 0.0f;
	private final float maxDensity = 0.1f;
	private final float redwoodVsTallredwoodDistribution = 0.5f;
	
	private SimplexOctaveGenerator gen1 = null;
		
	private JavaPlugin plugin = null;
	public FjordTreePopulator( JavaPlugin _plugin )
	{
		plugin = _plugin;
	}
	
	public void actuallyPopulate( World world, Random random, Chunk chunk )
	{
		//if (true) return;
		
		if (gen1 == null)
		{
			int numOctaves = 2;
			gen1 = new SimplexOctaveGenerator(world, numOctaves);
			gen1.setScale(1/128.0);
		}
                
                double density = minDensity + ((float)((gen1.noise(chunk.getZ() * 16 + 8192, -chunk.getX() * 16 - 8192, 0.2, 0.7) + 1.0) * 0.5) * (maxDensity - minDensity));
		
		int realZ = chunk.getZ() * 16;
                int realX = chunk.getX() * 16;		
		for (int z = 0; z < 16; ++z)
		{
			for (int x = 0; x < 16; ++x)
			{
				int rf = random.nextInt(10);
				
				// Do a random-check with the density parameter to see if we should place a tree here.
				
				if (rf <= density)
				{
					Block highestBlock = world.getHighestBlockAt(realX + x, realZ + z);
					
					// If the block is a free block...
					if (highestBlock.getType() == Material.AIR || 
						highestBlock.getType() == Material.SNOW)
					{
						Block highestBlockBelow = highestBlock.getRelative(BlockFace.DOWN);
						
						// ...and we have grass below us.
						if (highestBlockBelow.getType() == Material.GRASS_BLOCK)
						{							
							world.generateTree(	highestBlock.getLocation(), 
												random.nextFloat() < redwoodVsTallredwoodDistribution ? TreeType.REDWOOD : TreeType.TALL_REDWOOD);
							
						}
					}
				}
			}
		}
	}
        
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        populateSpread(world, random, chunk, true);
    }
    
    public void populateSpread(World world, Random random, Chunk chunk, boolean spread) {
        if(chunk.getBlock(0, 0, 0).getType() != Material.GLASS) return;
        int x = chunk.getX(), z = chunk.getZ();
        if( world.isChunkGenerated(x + 1, z)
                && world.isChunkGenerated(x - 1, z)
                && world.isChunkGenerated(x + 1, z - 1)
                && world.isChunkGenerated(x, z - 1)
                && world.isChunkGenerated(x - 1, z - 1)
                && world.isChunkGenerated(x + 1, z + 1)
                && world.isChunkGenerated(x, z + 1)
                && world.isChunkGenerated(x - 1, z + 1) ) {
            actuallyPopulate(world, random, chunk);
            chunk.getBlock(0, 0, 0).setType(Material.BEDROCK);
        }
        if(!spread) return;
        if(world.isChunkGenerated(x + 1, z)) populateSpread(world, random, world.getChunkAt(x + 1, z), false);
        if(world.isChunkGenerated(x - 1, z)) populateSpread(world, random, world.getChunkAt(x - 1, z), false);
        if(world.isChunkGenerated(x + 1, z + 1)) populateSpread(world, random, world.getChunkAt(x + 1, z + 1), false);
        if(world.isChunkGenerated(x, z + 1)) populateSpread(world, random, world.getChunkAt(x, z + 1), false);
        if(world.isChunkGenerated(x - 1, z + 1)) populateSpread(world, random, world.getChunkAt(x - 1, z + 1), false);
        if(world.isChunkGenerated(x + 1, z - 1)) populateSpread(world, random, world.getChunkAt(x + 1, z - 1), false);
        if(world.isChunkGenerated(x, z - 1)) populateSpread(world, random, world.getChunkAt(x, z - 1), false);
        if(world.isChunkGenerated(x - 1, z - 1)) populateSpread(world, random, world.getChunkAt(x - 1, z - 1), false);
    }
}
