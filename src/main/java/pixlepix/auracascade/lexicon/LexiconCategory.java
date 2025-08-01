/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * <p>
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * <p>
 * File Created @ [Jan 14, 2014, 6:23:47 PM (GMT)]
 */
package pixlepix.auracascade.lexicon;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LexiconCategory implements Comparable<LexiconCategory> {

    private static int count = 0;

    public final String unlocalizedName;
    public final List<LexiconEntry> entries = new ArrayList<LexiconEntry>();
    private final int sortingId;
    private ItemStack icon;
    private int priority = 5;

    /**
     * @param unlocalizedName The unlocalized name of this category. This will be localized by the client display.
     */
    public LexiconCategory(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        sortingId = count;
        count++;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    /**
     * Sets the priority for this category for sorting. Higher numbers
     * means they'll appear first in the book. The basics category
     * is 9, the miscellaneous category is 0, other vanilla botania categories
     * are 5. Using 9 and 0 is <b>not</b> recommended, since having your
     * categories before basics or after miscellaneous is a bad idea.
     * If two categories have the same priority they'll be sorted
     * by insertion order.
     */
    public LexiconCategory setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getSortingPriority() {
        return priority;
    }

    public final int getSortingId() {
        return sortingId;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public LexiconCategory setIcon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(LexiconCategory category) {
        return priority == category.priority ? sortingId - category.sortingId : category.priority - priority;
    }
}
