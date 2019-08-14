/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import me.zhehe.FishR.Type;
import me.zhehe.FishR.Type.FishType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class Config {
    public static Config instance = new Config();
    public String Rod_Iron = "";
    public String RodIronTag = "";
    public String RodIronDescription = "";
    public String Rod_Gold = "";
    public String RodGoldTag = "";
    public String RodGoldDescription = "";
    public String Rod_Diamond = "";
    public String RodDiamondTag = "";
    public String RodDiamondDescription = "";
    public String UnknownErrorMessage = "";
    public String NoWaterErrorMessage = "";
    public String NoFishErrorMessage = "";
    public String GenderErrorMessage = "";
    public String TooDarkErrorMessage = "";
    public String TooLightErrorMessage = "";
    public String NotSweetWaterErrorMessage = "";
    public String NotSaltWaterErrorMessage = "";
    public String OxygenErrorMessage = "";
    public String TemperatureErrorMessage = "";
    public Map<ItemStack,Double> itemProductionRate = new HashMap<>();
    public Map<FishType, ItemStack> fishProductionDict = new HashMap<>();
    public int baseRate = 35;
    public int IronRodMaxUseTime = 0;
    public int GoldRodMaxUseTime = 0;
    public int DiamondRodMaxUseTime = 0;
    
    public String BookName = "", 
            Book1 = "", BookUrl1 = "", Book2 = "", BookUrl2 = "", 
            Book3 = "", BookUrl3 = "", Book4 = "", BookUrl4 = "", 
            Book5 = "", BookUrl5 = "", Book6 = "", BookUrl6 = "";
    
    public String FISH_DECO = "";
    public String FISH_COOKIE = "";
    public String FISH_COOKIE_TAG = "";

    public String FISH_BREAD = "";
    public String FISH_BREAD_TAG = "";
    
    public String LIFE = "";
    public String PRO = "";
    public String TEMP_ADAP = "";
    public String OXYGEN_ADAP = "";
    public String WATER_TYPE = "";
    public String WATER_SALT = "";
    public String WATER_SWEET = "";
    public String FISH_SPECIAL = "";
    public String GENDER = "";
    public String MALE = "";
    public String FEMALE = "";
    public String DARK = "";
    public String MUL_TYPE = "";
    
    public String FISH_CATCHER = "";
    public String FISH_CATCHER_PLACE = "";
    public String FISH_CATCHER_FAIL = "";
    
    public Map<Type.FishSpecial, String> FISHSPECIAL_DICT = new HashMap<>();
    public Map<Integer, String> LIFE_DICT = new HashMap<>();
    public Map<FishType, String> FISHTYPE_DICT = new HashMap<>();

    
    public boolean worldEnable = true;
    
    private class WorldConfig {
        public boolean worldEnable = true;
    }
    
    private Config() {
        
    }
    
    public void initConfig() {
        final String PATH = "./plugins/Mariculture/";
        File directory = new File(PATH);
        if(!directory.exists()) {
            directory.mkdir();
        }
        
        File world = new File(PATH + "World.json");
        boolean create_world_json = true;
        WorldConfig wc = new WorldConfig();
        if(world.exists()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + "World.json"), "UTF8"))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                //Bukkit.getLogger().log(Level.INFO, sb.toString());
                wc = (new Gson()).fromJson(sb.toString(), WorldConfig.class);
                worldEnable = wc.worldEnable;
                create_world_json = false;
            } catch(IOException ex) {
                create_world_json = true;
            }
        }
        if(create_world_json) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(wc);
            try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(PATH + "World.json"), "utf-8")) {
                oStreamWriter.append(json);
                oStreamWriter.close();
            } catch (IOException ex) {
                
            }
        }
        
        File file = new File(PATH + "FishConfig.json");
        boolean need_initialize = true;
        DefaultConfig dc = generateDefaultConfig();
        if(file.exists()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH + "FishConfig.json"), "UTF8"))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                //Bukkit.getLogger().log(Level.INFO, sb.toString());
                dc = (new Gson()).fromJson(sb.toString(), DefaultConfig.class);
            } catch(IOException ex) {
                need_initialize = true;
            }
        }
        
        if(need_initialize) {
            dc = generateDefaultConfig();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dc);
            try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(PATH + "FishConfig.json"), "utf-8")) {
                oStreamWriter.append(json);
                oStreamWriter.close();
            } catch (IOException ex) {
                
            }
            
        }
        for(Map.Entry<Map<String, Object>, Double> entry : dc.itemProductionRate.entrySet()) {
            this.itemProductionRate.put(ItemStack.deserialize(entry.getKey()), entry.getValue());
        }
        for(Map.Entry<FishType, Map<String, Object>> entry : dc.fishProductionDict.entrySet()) {
            this.fishProductionDict.put(entry.getKey(), ItemStack.deserialize(entry.getValue()));
        }
        this.Rod_Iron = dc.Rod_Iron;
        this.RodIronTag = dc.RodIronTag;
        this.RodIronDescription = dc.RodIronDescription;
        this.Rod_Gold = dc.Rod_Gold;
        this.RodGoldTag = dc.RodGoldTag;
        this.RodGoldDescription = dc.RodGoldDescription;
        this.Rod_Diamond = dc.Rod_Diamond;
        this.RodDiamondTag = dc.RodDiamondTag;
        this.RodDiamondDescription = dc.RodDiamondDescription;
        
        this.GenderErrorMessage = dc.GenderErrorMessage;
        this.NoFishErrorMessage = dc.NoFishErrorMessage;
        this.NoWaterErrorMessage = dc.NoWaterErrorMessage;
        this.NotSaltWaterErrorMessage = dc.NotSaltWaterErrorMessage;
        this.NotSweetWaterErrorMessage = dc.NotSweetWaterErrorMessage;
        this.OxygenErrorMessage = dc.OxygenErrorMessage;
        this.TemperatureErrorMessage = dc.TemperatureErrorMessage;
        this.TooDarkErrorMessage = dc.TooDarkErrorMessage;
        this.TooLightErrorMessage = dc.TooLightErrorMessage;
        this.UnknownErrorMessage = dc.UnknownErrorMessage;
        
        this.IronRodMaxUseTime = dc.IronRodMaxUseTime;
        this.GoldRodMaxUseTime = dc.GoldRodMaxUseTime;
        this.DiamondRodMaxUseTime = dc.DiamondRodMaxUseTime;
        
        this.BookName = dc.BookName;
        this.Book1 = dc.Book1;
        this.BookUrl1 = dc.BookUrl1;
        this.Book2 = dc.Book2;
        this.BookUrl2 = dc.BookUrl2;
        this.Book3 = dc.Book3;
        this.BookUrl3 = dc.BookUrl3;
        this.Book4 = dc.Book4;
        this.BookUrl4 = dc.BookUrl4;
        this.Book5 = dc.Book5;
        this.BookUrl5 = dc.BookUrl5;
        this.Book6 = dc.Book6;
        this.BookUrl6 = dc.BookUrl6;
        
        this.FISH_DECO = dc.FISH_DECO;
        this.FISH_COOKIE = dc.FISH_COOKIE;
        this.FISH_COOKIE_TAG = dc.FISH_COOKIE_TAG;

        this.FISH_BREAD = dc.FISH_BREAD;
        this.FISH_BREAD_TAG = dc.FISH_BREAD_TAG;
        
        this.LIFE = dc.LIFE;
        this.PRO = dc.PRO;
        this.TEMP_ADAP = dc.TEMP_ADAP;
        this.OXYGEN_ADAP = dc.OXYGEN_ADAP;
        this.WATER_TYPE = dc.WATER_TYPE;
        this.WATER_SALT = dc.WATER_SALT;
        this.WATER_SWEET = dc.WATER_SWEET;
        this.FISH_SPECIAL = dc.FISH_SPECIAL;
        this.GENDER = dc.GENDER;
        this.MALE = dc.MALE;
        this.FEMALE = dc.FEMALE;
        this.DARK = dc.DARK;
        this.MUL_TYPE = dc.MUL_TYPE;
        
        this.FISH_CATCHER = dc.FISH_CATCHER;
        this.FISH_CATCHER_PLACE = dc.FISH_CATCHER_PLACE;
        this.FISH_CATCHER_FAIL = dc.FISH_CATCHER_FAIL;
        
        this.FISHSPECIAL_DICT.putAll(dc.FISHSPECIAL_DICT);
        this.LIFE_DICT.putAll(dc.LIFE_DICT);
        this.FISHTYPE_DICT.putAll(dc.FISHTYPE_DICT);
    }
    
    static class DefaultConfig {
        public String Rod_Iron = "Iron Rod";
        public String RodIronTag = "RodIron";
        public String Rod_Gold = "Golden Rod";
        public String RodGoldTag = "RodGold";
        public String Rod_Diamond = "Diamond Rod";
        public String RodDiamondTag = "RodDiamond";
        
        public String BookName = "The Guide of Mariculture";
        public String Book1 = "1. Water Tank";
        public String BookUrl1 = "https://github.com/zhehedream/MaricultureGuide/blob/master/Page1.md";
        public String Book2 = "2. Recipes";
        public String BookUrl2 = "https://github.com/zhehedream/MaricultureGuide/blob/master/Page2.md";
        public String Book3 = "3. Fish Farming Tutorial";
        public String BookUrl3 = "https://github.com/zhehedream/MaricultureGuide/blob/master/Page3.md";
        public String Book4 = "4. Fish Illustration";
        public String BookUrl4 = "https://github.com/zhehedream/MaricultureGuide/blob/master/Page4.md";
        public String Book5 = "5. Fish Breeding  Handbook";
        public String BookUrl5 = "https://github.com/zhehedream/MaricultureGuide/blob/master/Page5.md";
        public String Book6 = "6. Effect Dictionary";
        public String BookUrl6 = "https://github.com/zhehedream/MaricultureGuide/blob/master/Page6.md";
        
        public String FISH_DECO = "Decoration Block";
        public String FISH_COOKIE = "Lucky Sea Rhyme Cookie";
        public String FISH_COOKIE_TAG = "LuckyCookie";

        public String FISH_BREAD = "Quantum Cod Sandwich";
        public String FISH_BREAD_TAG = "DimensionBread";
        
        public String LIFE = "Lifespan";
        public String PRO = "Speed";
        public String TEMP_ADAP = "Temperature Tolerance";
        public String OXYGEN_ADAP = "Oxygen Tolerance";
        public String WATER_TYPE = "Aquatic Tolerance";
        public String WATER_SALT = "SALT";
        public String WATER_SWEET = "SWEET";
        public String FISH_SPECIAL = "Effect";
        public String GENDER = "Type";
        public String MALE = "Male";
        public String FEMALE = "Female";
        public String DARK = "Cave";
        public String MUL_TYPE = "Hybrid";
        
        public String FISH_CATCHER = "Automatic Fish Catcher";
        public String FISH_CATCHER_PLACE = "A Fish Catcher is created";
        public String FISH_CATCHER_FAIL = "Water is needed for Fish Catcher to work";
        
        
        public String RodIronDescription = "§This fishing rod exudes magical power and seems to be able to summon something...";
        public String RodGoldDescription = "§fThis fishing rod exudes magical power and seems to be able to summon something...";
        public String RodDiamondDescription = "§fThis fishing rod exudes magical power and seems to be able to summon something...";
        
        public String UnknownErrorMessage = "Unknown Error happened...";
        public String NoWaterErrorMessage = "Cannot find surface water. A 3x3 water is needed";
        public String NoFishErrorMessage = "Please put 2 fish into the first 2 cells of chest";
        public String GenderErrorMessage = "The gender should be different";
        public String TooDarkErrorMessage = "This fish cannot adapt to the open environment";
        public String TooLightErrorMessage = "This fish cannot adapt to the non-open environment";
        public String NotSweetWaterErrorMessage = "This fish cannot adapt to the salt water environment";
        public String NotSaltWaterErrorMessage = "This fish cannot adapt to the sweet water environment";
        public String OxygenErrorMessage = "Wrong Oxygen concentration, please try to increase/remove seagrass";
        public String TemperatureErrorMessage = "Wrong Temperature. Please use `/macu temp` to see the temperature. Please try in different biomes";
        
        public int IronRodMaxUseTime = 10;
        public int GoldRodMaxUseTime = 15;
        public int DiamondRodMaxUseTime = 40;
        
        
        public Map<Map<String, Object>, Double> itemProductionRate = new HashMap<>();
        public Map<FishType, Map<String, Object>> fishProductionDict = new HashMap<>();
        
        public Map<Type.FishSpecial, String> FISHSPECIAL_DICT;
        public Map<Integer, String> LIFE_DICT;
        public Map<FishType, String> FISHTYPE_DICT;
    }
    private static DefaultConfig generateDefaultConfig() {
        DefaultConfig dc = new DefaultConfig();
        {
            dc.fishProductionDict.put(FishType.PINGFAN, (new ItemStack(Material.DIRT, 10)).serialize());
            dc.fishProductionDict.put(FishType.PUSU, (new ItemStack(Material.CLAY, 10)).serialize());
            dc.fishProductionDict.put(FishType.PUSHI, (new ItemStack(Material.SAND, 10)).serialize());
            dc.fishProductionDict.put(FishType.CHONGLANG, (new ItemStack(Material.LILY_PAD, 5)).serialize());
            dc.fishProductionDict.put(FishType.QIANSHUI, (new ItemStack(Material.ICE, 5)).serialize());
            dc.fishProductionDict.put(FishType.TOUTIE, (new ItemStack(Material.RABBIT_FOOT, 2)).serialize());
            dc.fishProductionDict.put(FishType.TIAOYUE, (new ItemStack(Material.SLIME_BALL, 3)).serialize());
            dc.fishProductionDict.put(FishType.JILIU, (new ItemStack(Material.COD, 3)).serialize());
            dc.fishProductionDict.put(FishType.JIYOU, (new ItemStack(Material.SALMON, 3)).serialize());
            dc.fishProductionDict.put(FishType.NILIU, (new ItemStack(Material.MAGMA_CREAM, 3)).serialize());
            dc.fishProductionDict.put(FishType.JIANYUE, (new ItemStack(Material.GUNPOWDER, 3)).serialize());
            dc.fishProductionDict.put(FishType.YUANGU, (new ItemStack(Material.PRISMARINE_SHARD, 3)).serialize());
            dc.fishProductionDict.put(FishType.SHIZU, (new ItemStack(Material.PRISMARINE_CRYSTALS, 3)).serialize());
            dc.fishProductionDict.put(FishType.DITU, (new ItemStack(Material.RED_SAND, 3)).serialize());
            dc.fishProductionDict.put(FishType.CHENCHUAN, (new ItemStack(Material.GLOWSTONE, 3)).serialize());
            dc.fishProductionDict.put(FishType.BAOZANG, (new ItemStack(Material.QUARTZ, 3)).serialize());
            dc.fishProductionDict.put(FishType.HUASHI, (new ItemStack(Material.NAUTILUS_SHELL, 3)).serialize());
            
            dc.fishProductionDict.put(FishType.DENGLONG, (new ItemStack(Material.LAPIS_LAZULI, 3)).serialize());
            dc.fishProductionDict.put(FishType.HUAYANG, (new ItemStack(Material.LAPIS_LAZULI, 3)).serialize());
            dc.fishProductionDict.put(FishType.MEILI, (new ItemStack(Material.LAPIS_LAZULI, 3)).serialize());
            
            dc.fishProductionDict.put(FishType.ZHUANGJIA, (new ItemStack(Material.GOLD_INGOT, 2)).serialize());
            dc.fishProductionDict.put(FishType.HEJIN, (new ItemStack(Material.IRON_INGOT, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.SANSHA, (new ItemStack(Material.COAL, 2)).serialize());
            dc.fishProductionDict.put(FishType.DONGHAI, (new ItemStack(Material.GRANITE, 5)).serialize());
            dc.fishProductionDict.put(FishType.BAODAO, (new ItemStack(Material.DIORITE, 5)).serialize());
            dc.fishProductionDict.put(FishType.BOHAI, (new ItemStack(Material.ANDESITE, 5)).serialize());
            
            dc.fishProductionDict.put(FishType.WEIZHI, (new ItemStack(Material.EXPERIENCE_BOTTLE, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.YOULING, (new ItemStack(Material.GHAST_TEAR, 2)).serialize());
            dc.fishProductionDict.put(FishType.SHENMI, (new ItemStack(Material.DRAGON_BREATH, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.WEIZHI, (new ItemStack(Material.EMERALD, 2)).serialize());
            
            dc.fishProductionDict.put(FishType.ZHENZHU, (new ItemStack(Material.DIAMOND, 1)).serialize());
            dc.fishProductionDict.put(FishType.ZUANSHI, (new ItemStack(Material.DIAMOND, 1)).serialize());
            
            dc.fishProductionDict.put(FishType.HAIREN, (new ItemStack(Material.TRIDENT, 1)).serialize());
            
            dc.FISHSPECIAL_DICT = new HashMap<>();
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.NONE, "Normal");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.YANHUOWANHUI, "Fireworks");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.YUANDING, "Gardener");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.HULI, "Nursing");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.XIDAPUBEN, "Beatific");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.GUOJIABAOZANG, "National Treasure");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.SHIDAI, "Aging");
            dc.FISHSPECIAL_DICT.put(Type.FishSpecial.DABAOJIAO, "Great Barrier Reef");

            //15 18 20 25 30 32 35
            dc.LIFE_DICT = new HashMap<>();
            dc.LIFE_DICT.put(0, "Shortest");
            dc.LIFE_DICT.put(1, "Shorter");
            dc.LIFE_DICT.put(2, "Shortened");
            dc.LIFE_DICT.put(3, "Normal");
            dc.LIFE_DICT.put(4, "Elongated");
            dc.LIFE_DICT.put(5, "Longer");
            dc.LIFE_DICT.put(6, "Longest");

            dc.FISHTYPE_DICT = new HashMap<>();
            dc.FISHTYPE_DICT.put(FishType.PINGFAN, "Ordinary");
            dc.FISHTYPE_DICT.put(FishType.PUSU, "Simple");
            dc.FISHTYPE_DICT.put(FishType.PUSHI, "Plain");
            dc.FISHTYPE_DICT.put(FishType.CHONGLANG, "Surf");
            dc.FISHTYPE_DICT.put(FishType.QIANSHUI, "Diving");
            dc.FISHTYPE_DICT.put(FishType.TOUTIE, "Tough");
            dc.FISHTYPE_DICT.put(FishType.TIAOYUE, "Jump");
            dc.FISHTYPE_DICT.put(FishType.JILIU, "Rapid");
            dc.FISHTYPE_DICT.put(FishType.JIYOU, "Adventure");
            dc.FISHTYPE_DICT.put(FishType.NILIU, "Counter");
            dc.FISHTYPE_DICT.put(FishType.JIANYUE, "Splash");
            dc.FISHTYPE_DICT.put(FishType.YUANGU, "Ancient");
            dc.FISHTYPE_DICT.put(FishType.DITU, "Map");
            dc.FISHTYPE_DICT.put(FishType.SHIZU, "Ancestor");
            dc.FISHTYPE_DICT.put(FishType.CHENCHUAN, "Shipwreck");
            dc.FISHTYPE_DICT.put(FishType.BAOZANG, "Treasure");
            dc.FISHTYPE_DICT.put(FishType.HUASHI, "Fossil");
            dc.FISHTYPE_DICT.put(FishType.HAIREN, "Primadonna");
            dc.FISHTYPE_DICT.put(FishType.DENGLONG, "Lantern");
            dc.FISHTYPE_DICT.put(FishType.HUAYANG, "Undine");
            dc.FISHTYPE_DICT.put(FishType.MEILI, "Beauty");
            dc.FISHTYPE_DICT.put(FishType.ZHUANGJIA, "Armor");
            dc.FISHTYPE_DICT.put(FishType.HEJIN, "Alloy");
            dc.FISHTYPE_DICT.put(FishType.DONGHAI, "Pacific");
            dc.FISHTYPE_DICT.put(FishType.SANSHA, "Atlantic");
            dc.FISHTYPE_DICT.put(FishType.BAODAO, "Arctic");
            dc.FISHTYPE_DICT.put(FishType.BOHAI, "Antarctica");
            dc.FISHTYPE_DICT.put(FishType.WEIZHI, "Unknown");
            dc.FISHTYPE_DICT.put(FishType.YOULING, "Ghost");
            dc.FISHTYPE_DICT.put(FishType.SHENMI, "Mysterious");
            dc.FISHTYPE_DICT.put(FishType.ZHENZHU, "Pearl");
            dc.FISHTYPE_DICT.put(FishType.ZUANSHI, "Diamond");

        }
        return dc;
    }
}
