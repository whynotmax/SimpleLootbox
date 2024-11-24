package de.skysaint.inventory.impl.pagination;

import de.skysaint.utility.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pagination<T> {

    List<List<T>> pages;
    int pageSize;

    public void addItem(T item) {
        List<T> lastPage = pages.get(pages.size() - 1);
        if (lastPage.size() < pageSize) {
            lastPage.add(item);
        } else {
            // Create a new page
            List<T> newPage = new ArrayList<>();
            newPage.add(item);
            pages.add(newPage);
        }
    }

    public List<T> getItems(int page) {
        return new ArrayList<>(pages.get(page));
    }

    public boolean hasNextPage(int page) {
        return page < pages.size() - 1;
    }

    public boolean hasPreviousPage(int page) {
        return page > 0;
    }

    public ItemStack getNextPageItem() {
        return new ItemBuilder(Material.ARROW).setName("§7Nächste Seite").lore("§7Klicke um zur nächsten Seite zu gelangen.");
    }

    public ItemStack getPreviousPageItem() {
        return new ItemBuilder(Material.ARROW).setName("§7Vorherige Seite").lore("§7Klicke um zur vorherigen Seite zu gelangen.");
    }

    public ItemStack getPageItem(int page) {
        return new ItemBuilder(Material.PAPER).setName("§7Seite §e" + (page + 1) + "§7 von §e" + pages.size());
    }

}
