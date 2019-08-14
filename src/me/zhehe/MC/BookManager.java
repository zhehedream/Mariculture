/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zhehe.MC;

import me.zhehe.Config.Config;
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
        
        BaseComponent[] page = new ComponentBuilder(Config.instance.BookName + "\n").bold(true).append("\n")
                .append(Config.instance.Book1 + "\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.instance.BookUrl1))
                .append(Config.instance.Book2 + "\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.instance.BookUrl2))
                .append(Config.instance.Book3 + "\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.instance.BookUrl3))
                .append(Config.instance.Book4 + "\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.instance.BookUrl4))
                .append(Config.instance.Book5 + "\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.instance.BookUrl5))
                .append(Config.instance.Book6 + "\n").bold(false)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, Config.instance.BookUrl6))
                .create();
        
        bookMeta.spigot().addPage(page);
        
        bookMeta.setTitle(Config.instance.BookName);
        bookMeta.setAuthor("Mariculture Plugin");
        book.setItemMeta(bookMeta);
        
        return book;
    }
}
