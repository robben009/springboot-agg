package typeStr;

public class 反转字符串 {

    public static char[] rob(char[] strs) {

        if(strs.length == 1){
            return strs;
        }

        int i = 0;
        int j = strs.length - 1;

        while (i < j){
            char a = strs[i];
            strs[i] = strs[j];
            strs[j] = a;

            i++;
            j--;
        }

        return strs;
    }

    public static void main(String[] args) {
        String a = "hello";
        System.out.println(rob(a.toCharArray()));
    }

}
