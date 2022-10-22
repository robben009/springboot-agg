import java.util.HashMap;

/**
 * Description： 无重复字符的最长子串个数
 * Author: robben
 * Date: 2021/10/6 11:37
 */
public class LengthOfLongestSubstring {

    public static void main(String[] args) {
        System.out.println(getNum("abccddddjkmnb"));
    }

    public static int getNum(String s){
        if(s.length() == 0){
            return 0;
        }

        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0;
        int left = 0;

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            if(map.containsKey(a)){
                left = Math.max(left,map.get(a) + 1);
            }

            map.put(a,i);
            max = Math.max(max,i-left+1);
        }

        return max;
    }

}
