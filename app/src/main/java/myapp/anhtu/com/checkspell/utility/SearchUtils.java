package myapp.anhtu.com.checkspell.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anhtu on 3/27/2017.
 */

public class SearchUtils {
    public static ArrayList<String> search(String content, String keywords) {
        ArrayList<String> result = new ArrayList<>();
        String[] keys = keywords.trim().split(" "); //Tach thanh mang cac keyword
        //Remove Duplicate from listkey
        ArrayList<String> listKey = new ArrayList(Arrays.asList(keys));
        HashSet hs = new HashSet(listKey);
        listKey.clear();
        listKey.addAll(hs);
        Collections.reverse(listKey); // đảo ngược list
        //Use Regex to search
        //Form Pattern: /(\w+ \bkey\b)|(\bkey\b \w+)/igm
        String pattern;
        for (int i = 0; i < listKey.size(); i++) {
            String key = listKey.get(i);
//            pattern = "\\b" + key + "\\b";
            pattern = "(\\w+ \\b" +key+"\\b)|" + "\\b" +key+ "\\b \\w+";
            Pattern pt = Pattern.compile(pattern, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher mc = pt.matcher(content);
            while (mc.find()) {
                result.add(mc.group());
            }
        }
        if (result.size() == 0) {
            result.add("Không tìm thấy kết quả nào!");
        }
        return result;
    }
}
