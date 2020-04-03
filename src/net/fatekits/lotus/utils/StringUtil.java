package net.fatekits.lotus.utils;


import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import java.util.List;

public final class StringUtil {

    // utility classes should not have the ability to be instantiated meaning, no one should be able to call "StringUtil util = new StringUtil"
    // that's what this method controls
    private StringUtil() {
        throw new UnsupportedOperationException("Cannot instantiate a utility class.");
    }

    // this is simple stuff u know already
    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // this is just a fancy way of coloring a list without using a forloop
    public static List<String> format(List<String> messages) {

        // I make a new list to return, btw Lists.newArrayList and Sets.newHashSet, etc. are apart of a library called guava, it's in the google.common package, it's just a more efficient way of doing "new ArrayList"
        // it's a really minor difference but I like doing it this way its kind of a habit now
        List<String> toReturn = Lists.newArrayList();

        // the forEach method is apart of Lambda too, it's a simplified for loop, it goes through each item in the list in this case with a "consumer", maybe u can picture it better if I do this, look
        messages.forEach(message -> toReturn.add(format(message)));


        // btw I return the list I made
        return toReturn;
    }
}