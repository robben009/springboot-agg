import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串相乘
 */
public class StrMultiply {

    public static void main(String[] args) {
        System.out.println(multiply("99","99"));
    }

    public static String multiply(String num1, String num2) {
        if(StringUtils.isEmpty(num1)  || StringUtils.isEmpty(num2)){
            return "";
        }
        if(num1.equals("0") || num2.equals("0")){
            return "0";
        }

        int inx1 = num1.length()-1;
        int inx2 = num2.length()-1;

        //两个数相乘的结果不会大于两个数位数之和
        int[] muls = new int[num1.length() + num2.length()];

        //类似乘法,需要从最后一位数相乘
        for(int i=inx1;i>=0;i--){
            for(int j=inx2;j>=0;j--){
                int mul = (num1.charAt(i)-'0') * (num2.charAt(j)-'0');
                mul = mul + muls[i+j+1];

                //用11*11来想这段代码
                muls[i+j+1] = mul%10;
                muls[i+j] = mul/10 + muls[i+j];
                System.out.println(JSON.toJSONString(muls));
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < muls.length; i++) {
            if(!"0".equals((muls[i] + ""))){
                result.append(muls[i]);
            }
        }
        return result.toString();
    }


}
