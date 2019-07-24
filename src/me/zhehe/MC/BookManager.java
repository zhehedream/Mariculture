/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 *
 * @author zhiqiang.hao
 */
public class BookManager {
    public static ItemStack getGuideBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        
        BaseComponent[] page = new ComponentBuilder("水产养殖手册\n").bold(true).append("\n")
                .append("1. 水槽\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zhehedream/MaricultureGuide/blob/master/Page1.md"))
                .append("2. 合成配方\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zhehedream/MaricultureGuide/blob/master/Page2.md"))
                .append("3. 养鱼教程\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zhehedream/MaricultureGuide/blob/master/Page3.md"))
                .append("4. 鱼类图鉴\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zhehedream/MaricultureGuide/blob/master/Page4.md"))
                .append("5. 杂交手册\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zhehedream/MaricultureGuide/blob/master/Page5.md"))
                .append("6. 特性词典\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/zhehedream/MaricultureGuide/blob/master/Page6.md"))
                .create();
        
        bookMeta.spigot().addPage(page);
        
        bookMeta.setTitle("水产养殖手册");
        bookMeta.setAuthor("Mariculture Plugin");
        book.setItemMeta(bookMeta);
        
        return book;
    }
}
