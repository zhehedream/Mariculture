/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import me.zhehe.Config.Config;
import me.zhehe.Util.Constant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class HeadManager {
    public static List<ItemStack> heads = new ArrayList<>();
    
    public static HeadManager instance = new HeadManager();
    
    private HeadManager() {
        heads.add( createHead (
                "cb2736be-3e40-42d4-a5e8-9e15fb65d5f0",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTQxN2EwZDE4NGZiNDUxYTljYzNiYjQ4YjMxMWI1NWIyODdiZDQzOWQ4ZmRhZmQzYTRlODAyMTVjYWMzODUifX19"
        ) ); //https://minecraft-heads.com/custom/animals/1644-magikarp-129
        heads.add( createHead (
                "7b314fba-09ab-4ab6-b956-cec6a5fcbed5",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZkMTQ5ZTRkNDk5OTI5NjcyZTI3Njg5NDllNjQ3Nzk1OWMyMWU2NTI1NDYxM2IzMjdiNTM4ZGYxZTRkZiJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/355-clownfish
        heads.add( createHead (
                "e246c06c-5d6a-4fd9-b600-c6bd71336b7a",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWRmYzU3ZDA5MDU5ZTQ3OTlmYTkyYzE1ZTI4NTEyYmNmYWExMzE1NTc3ZmUzYTI3YWVkMzg5ZTRmNzUyMjg5YSJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/612-salmon
        heads.add( createHead (
                "8f718637-6901-4301-bd98-ebde0cc19ed8",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmY5OWI1ODBkNDVhNzg0ZTdhOTY0ZTdkM2IxZjk3Y2VjZTc0OTExMTczYmQyMWMxZDdjNTZhY2RjMzg1ZWQ1In19fQ=="
        ) ); //https://minecraft-heads.com/custom-heads/animals/613-fish
        heads.add( createHead (
                "b4630012-0e65-4a3d-bfd6-5024b782ab69",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk1NTkzODg5OTNmZTc4MmY2N2JkNThkOThjNGRmNTZiY2Q0MzBlZGNiMmY2NmVmNTc3N2E3M2MyN2RlMyJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/618-pufferfish
        heads.add( createHead (
                "3a8b00aa-319e-43d9-bc49-53d9b4d295cd",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDlkMGU4MzNkOWJkYTMyZjJkNzM2ZDhjM2MzYmU4YjliOTY0YWRkZDU5MzU3YzEyMjYzZmZjY2I4YjhkYWUifX19"
        ) ); //https://minecraft-heads.com/custom-heads/animals/6644-fish
        heads.add( createHead (
                "b43c5fb1-762d-4f01-8a29-0773c60ae341",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNkZTQ4YTg0NjY4YTM2MTkxODdkYTE5NmU3NzdmOTMxZjc1NTQ4NDllOGYyNDM4OTdhYzgzYzhhZDYxIn19fQ=="
        ) ); //https://minecraft-heads.com/custom-heads/animals/3815-jellyfish
        heads.add( createHead (
                "fb81f77f-3a6b-45c7-96b5-6663a20ff754",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBjZDcxZmJiYmJiNjZjN2JhZjc4ODFmNDE1YzY0ZmE4NGY2NTA0OTU4YTU3Y2NkYjg1ODkyNTI2NDdlYSJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/12377-fish
        heads.add( createHead (
                "774e3507-4493-40cc-ab0a-a97235c8f887",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVkMjljMGI0ZjE3NWI2NDBhZmNhYjQ0NWJkMTI5YzhmYjhiYTdjNDY1MTJjYTU2M2YxMDExOTU5MzJhZDdmNCJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/food%20&%20drinks/18150-fish-body
        heads.add( createHead (
                "0e3eee69-4e44-49f7-b618-15a4e0800c97",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY5MWExNDg2N2VmOTg2Nzg3MmRjZGM4YzU0ZTNkNGNmYjVlNTI1ZGNjZmI3Zjk5YTczMTRhNDVmYWViMzAxZSJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/18654-cod-fish-in-a-bucket
        heads.add( createHead (
                "8c2bfb27-5098-4746-8afa-9c1a0b5f6b01",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI1MGQ2ODYyMjE0MzU5YmFmZjQ3MzY5NmIzZWFjZjcxYzdhZmFiOGY0MDJjOTAyZDIwMzZlNzY3ODg3NTgzIn19fQ=="
        ) ); //https://minecraft-heads.com/custom-heads/animals/18646-fish-in-a-bucket
        heads.add( createHead (
                "088ddf58-e6f6-4cd8-b702-69a3dfb5c5e2",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjliYjE4ZTFjZmU2ZWRmMDM1MzVlNWZkYzY0ODJlMDlhN2ZkZjk1MTI1Yzg2MTEyNjliOWRlOWQ1NDcxNWI5ZCJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/18645-clownfish-in-a-bucket
        heads.add( createHead (
                "05ea2dbf-b772-47e0-939c-d61a19bd927f",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmIxNmFlZmIyZjAzYTcwNGYwMDdmMjJhZjU1OTUwM2QzNjgxY2I4MjdhM2Q1ZTFlY2IzMGI0MmRlZTAwNDJiYSJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/18644-salmon-in-a-bucket
        heads.add( createHead (
                "2f1794e5-ce3b-4a84-8f79-a9d7ea42b586",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2YyODM1OWNmOGY5NWY1NmUwYjQ1ZDkxNzE4NWQwMTM3YjUzODAxZjliYzU1MWQ0NThmNjM2NzE4ZmQifX19"
        ) ); //https://minecraft-heads.com/custom-heads/miscellaneous/17832-carrot-on-a-stick
        heads.add( createHead (
                "824f807e-421a-4769-ad92-73d9fa368dde",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FjMmIxMTkzYzEyNDU5ZGEzMjc3N2E2NmU3N2MzM2ViYTgzODhkOWNkNTE2ZDgzZjM2NTFiOWY3YmFmMCJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/miscellaneous/17833-fishing-rod
        heads.add( createHead (
                "6cbeb9dc-665e-489e-89e3-4cc9e6ac9947",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDEyNzE4ZjM4NjU2Mzg3NGQ0MTI2ZGNmMzRmNmRmYjRkYjg5ZmRkMTRiMWFkMzdiYmUxNjc4YmQzZWRkMiJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/25361-police-magikarp
        heads.add( createHead (
                "7a277c56-8bae-490e-be96-72152f526b51",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWIxMWZlZTliYTY5ZmE4ZGQ3MzNkYmZjY2U4M2FhYzhjZGY1NGQ2NDRhZmEzMjQzZGYyMmFjYTFhODQ4MCJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/decoration/7407-aquarium
        heads.add( createHead (
                "f4cc7cd9-7e67-4ac9-88bc-49099d6462af",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThiNTdkZDRhODJhNDJhNDM4OWQ5YzZhZjczYTI3NzE4YThhNDRiOGM0Nzg4Y2Q2ZDY1YWJiZWE3ZTYxODE0MiJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/decoration/4304-aquarium
        heads.add( createHead (
                "958a1af5-3c85-46bc-8d84-8e00343500a7",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RjNGU3YWQ0Njg0MjkyYTQ3MjljZjlmMTI3ZTcxNTFmODZjZGU2Njg4ODc2YjE0NmIwYWVkNjIxOGMifX19"
        ) ); //https://minecraft-heads.com/custom-heads/decoration/1238-aquarium
        heads.add( createHead (
                "87fbd6b4-7960-4481-b7e0-615c79cb02e1",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWYxMmUwZGZlNjhhZWY3Y2I1YzA2Yzc3Y2YyNzIyMzBhNWNkNjgyYmM0NTJjYjY5OWIyMTc3ZGY1ZTZhZjY0In19fQ=="
        ) ); //https://minecraft-heads.com/custom-heads/decoration/278-aquarium
        heads.add( createHead (
                "1b56e246-523a-4759-bbd2-196a7a06ee58",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdiN2Q3ODUxNDQyODdkMDVmMWFmZDZhNjE2ZWU1NWI1MmJlMWY0NDdjZWI1NjkwZmJhOWU0MjU3OGI0MzRlMiJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/decoration/8201-aquarium
        heads.add( createHead (
                "8349e6bb-533d-4fbc-8200-120269714969",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc1NmZiNjM0MTA4NmZjMmY2NDAxMzVmZDA5MTczN2VjMTdiYWUxYThmOGZkNWM5NTMyYTI4NjFkODIxZSJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/decoration/14009-aquarium
        heads.add( createHead (
                "2b4222c3-18c6-4523-becd-ee44a81967ac",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGEwMTBiZWY2MDcyMmJkZjFhMjQxOTBhZWM1MjgxMTg0MmY5NDM4ZjY4NTk0ZjI4MzI2ZTM3NDhiNDgwNTRjYiJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/decoration/29868-aquarium
        heads.add( createHead (
                "6cbeb9dc-665e-489e-89e3-4cc9e6ac9947",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDEyNzE4ZjM4NjU2Mzg3NGQ0MTI2ZGNmMzRmNmRmYjRkYjg5ZmRkMTRiMWFkMzdiYmUxNjc4YmQzZWRkMiJ9fX0="
        ) ); //https://minecraft-heads.com/custom-heads/animals/25361-police-magikarp
    }
    
    public ItemStack createHead2(final String uuid, final String textures) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        return Bukkit.getUnsafe().modifyItemStack(item,
				"{SkullOwner:{Id:\"" + uuid + "\",Properties:{textures:[{Value:\"" + textures + "\"}]}}}"
		);
    }
    
    public ItemStack createHead(final String uuid, final String textures) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(Config.instance.FISH_DECO);
        GameProfile profile = new GameProfile(UUID.fromString(uuid), null);
        profile.getProperties().put("textures", new Property("textures", textures));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NullPointerException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            //e1.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "尝试创建饰品失败，饰品功能可能无法正常工作");
        }
        head.setItemMeta(headMeta);
        return head;
    }
    
    public static ItemStack getRandomDrop(Random random) {
        if(heads.isEmpty()) return null;
        return heads.get(random.nextInt(heads.size()));
    }
    
    public static ItemStack getLuckyCookie() {
        ItemStack is = new ItemStack(Material.COOKIE, 1);
        ItemMeta im = is.getItemMeta();
        List<String> lores = new ArrayList<>();
        lores.add(Config.instance.FISH_COOKIE_TAG);
        im.setLore(lores);
        im.setDisplayName(Config.instance.FISH_COOKIE);
        is.setItemMeta(im);
        return is;
    }
    
    public static boolean isLuckyCookie(ItemStack is) {
        if(is.getType() != Material.COOKIE) return false;
        List<String> lores = is.getItemMeta().getLore();
        if(lores == null || lores.isEmpty()) return false;
        return lores.get(0).equals(Config.instance.FISH_COOKIE_TAG);
    }
    
    public static void applySpecialEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1), true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2), true);
    }
    
    public static void givePlayerAllDeco(Player player) {
        Inventory inv = player.getInventory();
        for(ItemStack is : heads) {
            inv.addItem(is);
        }
    }
}
