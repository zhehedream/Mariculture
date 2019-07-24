package com.bryankeiren.fjord;

import me.simplex.nordic.populators.*;

import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.*;
import org.bukkit.util.noise.*;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.zhehe.World.SeagrassPopulator;
import org.bukkit.plugin.java.JavaPlugin;

public class FjordChunkGenerator extends ChunkGenerator
{	
	private JavaPlugin plugin = null;
	
	private SimplexOctaveGenerator gen1 = null;
	private PerlinOctaveGenerator gen2 = null;
	private PerlinOctaveGenerator gen3 = null;
	private SimplexOctaveGenerator gen4 = null;
	private SimplexOctaveGenerator gen5 = null;
	
	private ArrayList<BlockPopulator> populators = null;
	
	public FjordChunkGenerator( JavaPlugin _plugin )
	{
		plugin = _plugin;
		
		// Initialize list of block populators. 
		populators = new ArrayList<>();    	
		populators.add(new Populator_Caves());
		populators.add(new Populator_Ores());
		populators.add(new FjordLavaPopulator());
		populators.add(new FjordTreePopulator(plugin));
		populators.add(new FjordGrassPopulator(plugin));
                
                populators.add(new SeagrassPopulator());
	}
	
	private void InitNoiseGenerators( World world )
	{
		// Initialize noise generators.
    	if (gen1 == null)
    	{
    		gen1 = new SimplexOctaveGenerator(world, 8);
    		gen1.setScale(1/128.0); // Roughly: The distance between peaks.
    	}
    	
    	if (gen2 == null)
    	{
	    	gen2 = new PerlinOctaveGenerator(world, 5);
	    	gen2.setScale(1/64.0);
		}
    	
    	if (gen3 == null)
    	{
	    	gen3 = new PerlinOctaveGenerator(world, 5);
	    	gen3.setScale(1/32.0);
		}
    	
    	if (gen4 == null)
    	{
	    	gen4 = new SimplexOctaveGenerator(world, 8);
	    	gen4.setScale(1/24.0);
		}
    	
    	if (gen5 == null)
    	{
	    	gen5 = new SimplexOctaveGenerator(world, 4);
	    	gen5.setScale(1/8.0);
		}	
    	
    	/*RidgedMulti ridgedMultiGen = new RidgedMulti();
    	ridgedMultiGen.setFrequency(2);
    	try {	// Try-catch because else Eclipse whines.
			ridgedMultiGen.setOctaveCount(1);
		} catch (ExceptionInvalidParam e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ridgedMultiGen.setNoiseQuality(NoiseQuality.QUALITY_FAST);
    	ridgedMultiGen.setSeed((int)world.getSeed());*/
	}
	
    @Override
    public List<BlockPopulator> getDefaultPopulators( World world ) 
    {    	 
    	return populators;
    }
    
    @Override
    public boolean canSpawn( World world, int x, int z ) 
    {
    	Block highestBlock = world.getHighestBlockAt(x, z).getRelative(BlockFace.DOWN);
    	
    	Material highestBlockType = highestBlock.getType();
		return highestBlockType == Material.SAND ||
				highestBlockType == Material.GRAVEL ||
				highestBlockType == Material.GRASS ||
				highestBlockType == Material.STONE;
	}
    
    //This converts relative chunk locations to bytes that can be written to the chunk
    public int xyzToByte(int x, int y, int z) 
    {
    	return (x * 16 + z) * 128 + y;
    }
        
    // Converts a y-position to a section ID.
    private int getSectionID( int y )
    {
    	return y >> 4;
    }
    
    // Set a block in a chunk. If the section in which the coordinates exist is not allocated,
    // we allocate it here.
    private void setBlock( ChunkData result, int x, int y, int z, Material block ) 
    {
        x = x % 16;
        y = y % 256;
        z = z % 16;
        result.setBlock(x, y, z, block);
    }
    
    private Material getBlock( ChunkData result, int x, int y, int z ) 
    {
    	return result.getType(x, y, z);
    }
    
    // Compared to generateBlockSections, this function has extended block ID support. That means that the block ID
    // range is [0..4095] (note the fact that the functions returns a 2D array of short instead of byte).
    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biomes) {
    	InitNoiseGenerators(world);
    	
    	int worldMaxHeight = world.getMaxHeight();
    	ChunkData result = createChunkData(world);
    	
    	for (int bZ = 0; bZ < 16; ++bZ)
    	{
	    	for (int bX = 0; bX < 16; ++bX)
	    	{
	    		DoLayer_Bedrock(result, random, bX, bZ);
	    		
	    		int realX = bX + x * 16;	// x == X of the chunk.
	    		int realZ = bZ + z * 16;	// z == Z of the chunk.
	    		double frequency = 0.15;
	    		double amplitude = 0.4;
	    		int multitude = 42;
	    		int sealevel = 64;
	    		double noiseValue = 0.0;
	    		double plainsValue = (gen1.noise(realX * 0.33, realZ * -0.33, 0.1, 0.3) + 1.0) * 0.5;
	    		
	    		double mountainValue0 = gen1.noise(realX, realZ, frequency, amplitude);
		    	noiseValue += mountainValue0 * multitude + sealevel;
	    		noiseValue += gen2.noise(realX, realZ, 0.8f, 0.3f) * 16;
	    		noiseValue += gen3.noise(realX, realZ, 1.7f, 0.45f) * 8; 
	    		
	    		if (plainsValue <= 0.5)
	    		{
	    			float lerpFactor = 1.0f;
	    			if (plainsValue >= 0.35)
	    			{
	    				// Between 0.5 and 0.35 we want to smoothly change the terrain instead of having a sudden change at 0.5.
	    				lerpFactor = Util.LerpFactor(0.5f, 0.35f, (float)plainsValue);
	    			}
	    			noiseValue = sealevel * Util.Lerp(0.0f, 1.0f, lerpFactor) + noiseValue * Util.Lerp(1.0f, 0.1f, lerpFactor);
	    		}
	    		
	    		for (int bY = 1; bY < noiseValue && bY < worldMaxHeight; ++bY) 
	    		{
	    			setBlock(result, bX, bY, bZ, Material.STONE);	    		
	    		}
	    		
	    		// Set a preliminary biome based on terrain height. May chance in the code that follows.
	    		int taigaBorder = 95;
	    		biomes.setBiome(bX, bZ, noiseValue > taigaBorder ? Biome.TAIGA_HILLS : Biome.MOUNTAINS);
	    		
	    		int actualSealevel = (int)(sealevel * 0.75f);
	    		
	    		DoLayer_Shore(result, world, random, bX, bZ, actualSealevel);
	    		
	    		DoLayer_SeaBed(result, world, random, bX, bZ, actualSealevel);

	    		//DoLayer_Ore(result, world, random, bX, bZ, realX, realZ, gen5);
	    		
	    		DoLayer_Sea(result, world, random, bX, bZ, actualSealevel, biomes);
	    		
	    		DoLayer_GrassAndDirt(result, world, random, bX, bZ);
	    		
	    		DoLayer_Snow(result, world, random, bX, bZ, taigaBorder);
	    	}
    	}
    	
    	return result;    	
        //return null; // Default - returns null, which drives call to generateBlockSections()
    }
    private int findHighestBlockY( ChunkData result, World world, int bX, int bZ )
    {
    	int worldMaxHeight = world.getMaxHeight();
    	for (int bY = worldMaxHeight - 1; bY > 0; --bY)
    	{
    		Material blockType = getBlock(result, bX, bY, bZ);
    		if (blockType != Material.AIR)
    		{
    			// If the current block is NOT air, we've found the highest one.
    			// TODO MAYBE: Check for overhangs and stuff?
    			return bY;
    		}
    	}
    	return 0;
    }
    
    private void DoLayer_Bedrock( ChunkData result, Random random, int bX, int bZ )
    {
    	setBlock(result, bX, 0, bZ, Material.BEDROCK);	// One layer of bedrock.
        result.setBlock(0, 0, 0, Material.GLASS);
		
		for (int bY = 1; bY < 5; ++bY)
		{
    		float randomFloat = random.nextFloat();
    		if (randomFloat < 0.5)
    		{
	    		setBlock(result, bX, bY, bZ, Material.BEDROCK);
    		}
		}
    }
    
    private void DoLayer_Shore( ChunkData result, World world, Random random, int bX, int bZ, int sealevel )
    {
    	int worldMaxHeight = world.getMaxHeight();
    	
    	int shoreMinLimitDry = 2;	// Minimum number of blocks in the vertical direction (y-axis)
    								// that the shore may extend above sealevel.
    	int shoreMaxLimitDry = 4;	// Maximum number of blocks in the vertical direction (y-axis)
									// that the shore may extend above sealevel.
    	int shoreLimitMinWet = 5;	// Minimum number of blocks in the vertical direction (y-axis)
									// that the shore may extend below sealevel.
    	int shoreLimitMaxWet = 9;	// Maximum number of blocks in the vertical direction (y-axis)
									// that the shore may extend below sealevel.
    	int shoreMinDepth = 2;		// Min shore depth.
    	int shoreMaxDepth = 5;		// Max shore depth.
    	
    	// Find the highest block.
    	int highestBlockY = findHighestBlockY(result, world, bX, bZ);
    	
    	
    	int shoreLimitWet = shoreLimitMinWet + (int)(random.nextFloat() * (shoreLimitMaxWet - shoreLimitMinWet));
    	int shoreLimitDry = shoreMinLimitDry + (int)(random.nextFloat() * (shoreMaxLimitDry - shoreMinLimitDry));
    	if (highestBlockY > sealevel - shoreLimitWet &&
    		highestBlockY < sealevel + shoreLimitDry)
    	{
    		int shoreDepth = shoreMinDepth + (int)(random.nextFloat() * (shoreMaxDepth - shoreMinDepth));
    		for (int bY = highestBlockY; bY > highestBlockY - shoreDepth && bY > 0; --bY)
    		{
    			setBlock(result, bX, bY, bZ, Material.GRAVEL);
    		}
    	}
    	
    	// TODO MAYBE: Place gravel below it up until the next solid block so that it doesn't fall down?
    }
    
    private void DoLayer_SeaBed( ChunkData result, World world, Random random, int bX, int bZ, int sealevel )
    {
    	int highestBlockY = findHighestBlockY(result, world, bX, bZ);
    	
    	if (highestBlockY <= sealevel)
    	{
	    	for (int i = highestBlockY; i > highestBlockY - 5 && i > 1; --i)
	    	{
	    		Material currBlockMat = getBlock(result, bX, i, bZ);
	    		if (currBlockMat != Material.AIR &&
	    			currBlockMat != Material.GRAVEL)
	    		{
		    		double nextGaussian = random.nextGaussian();
		    		Material mat = (nextGaussian > -0.2 && nextGaussian < 0.2) ? (Material.CLAY) : (Material.DIRT); 
		    		setBlock(result, bX, i, bZ, mat);   	
	    		}
	    	}
    	}
    }
    
    private void DoLayer_Sea( ChunkData result, World world, Random random, int bX, int bZ, int sealevel, BiomeGrid biomes )
    {
    	int worldMaxHeight = world.getMaxHeight();
    	
    	// Find the highest block.
    	int highestBlockY = findHighestBlockY(result, world, bX, bZ);
    	
    	for (int bY = highestBlockY + 1; bY < sealevel && bY < worldMaxHeight; ++bY)
    	{
    		// Replace air blocks with water.
    		if (getBlock(result, bX, bY, bZ) == Material.AIR)
    		{
    			setBlock(result, bX, bY, bZ, Material.WATER);
                        biomes.setBiome(bX, bZ, Biome.COLD_OCEAN);
    		}
    	}
    }
    
    private void DoLayer_GrassAndDirt( ChunkData result, World world, Random random, int bX, int bZ )
    {
    	// Overlay grass, dirt below it.
    	
    	int highestBlockY = findHighestBlockY(result, world, bX, bZ);
    	
    	if (highestBlockY < 1)
    	{
    		return;
    	}
    	
    	Material highestBlockType = getBlock(result, bX, highestBlockY - 1, bZ);
    	
    	if (highestBlockType != Material.STONE)
    	{
    		return;
    	}
    	
    	int dirtMinDepth = 3;
    	int dirtMaxDepth = 8;
    	
    	int dirtDepth = dirtMinDepth + (int)(random.nextFloat() * (dirtMaxDepth - dirtMinDepth));
    	for (int bY = highestBlockY/* - 1*/; bY > highestBlockY - dirtDepth && bY > 0; --bY)
    	{
    		if (getBlock(result, bY, bY, bZ) != Material.AIR)
    		{
    			setBlock(result, bX, bY, bZ, Material.DIRT);
    		}
    	}
    	setBlock(result, bX, highestBlockY, bZ, Material.GRASS_BLOCK);
    }
    
    private void DoLayer_Snow( ChunkData result, World world, Random random, int bX, int bZ, int taigaBorder )
    {
    	int highestBlockY = findHighestBlockY(result, world, bX, bZ);
    	
    	int snowMinTransitionHeight = -2;
    	int snowMaxTransitionHeight = 2;
    	
    	int snowTransitionHeight = snowMinTransitionHeight + (int)(random.nextGaussian() * (snowMaxTransitionHeight - snowMinTransitionHeight));
    	taigaBorder += snowTransitionHeight;
    	
    	if (highestBlockY < taigaBorder)
    	{
    		return;
    	}
    	
    	Material blockType = getBlock(result, bX, highestBlockY, bZ);
    	if (blockType == Material.WATER ||
    		blockType == Material.LAVA ||
    		blockType == Material.AIR)
    	{
    		return;
    	}
    	
    	if (highestBlockY + 1 < world.getMaxHeight())
    	{
    		setBlock(result, bX, highestBlockY + 1, bZ, Material.SNOW);
    	}
    }
    
    private void DoLayer_Ore( ChunkData result, World world, Random random, int bX, int bZ, int realX, int realZ, SimplexOctaveGenerator gen )
    {
    	int highestBlockY = findHighestBlockY(result, world, bX, bZ);
    	
		for (int bY = 1; bY < highestBlockY; ++bY)	// Can skip y == 0 because we filled that level with bedrock.
		{
			double noise = gen.noise(realX, bY, realZ, 5, 0.005, 0.1);
			
			if (noise > 0.75)
			{
				if (getBlock(result, bX, bY, bZ) != Material.AIR)
				{					
					double gaussianDouble = Math.abs(random.nextGaussian()) / 3.0;	// Workable range [0..1].
										
					// Ores that we want, ordered by chance of occurence: (The gaussian distribution
					// of the random double will help us properly distribute)
					// Coal
					// Iron
					// Gold
					// Lapis Lazuli
					// Redstone
					// Diamond
					Material[] ores = {	Material.COAL_ORE,
								Material.IRON_ORE,
								Material.GOLD_ORE,
								Material.LAPIS_ORE,
								Material.REDSTONE_ORE,
								Material.DIAMOND_ORE};
					
					int[] oreLayersMin = {	5,
											5,
											5,
											14,
											5,
											5	};
					int[] oreLayersMax = {	132,
											68,
											34,
											34,
											16,
											16	};
					
					double d = 1.0 / ores.length;
					for (int i = 0; i < ores.length; ++i)
					{
						double threshold = i * d;
						if (gaussianDouble > threshold &&
							gaussianDouble < threshold + d &&
							bY >= oreLayersMin[i] &&
							bY <= oreLayersMax[i])
						{
							setBlock(result, bX, bY, bZ, ores[i]);
							break;
						}
					}
				}
			}
		}
    }
}