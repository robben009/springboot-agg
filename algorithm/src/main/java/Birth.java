import cn.hutool.core.util.RandomUtil;

/**
 * Description： 要是生女孩就生第二胎,生男孩就不生了
 * Author: robben
 * Date: 2021/2/8 19:24
 */
public class Birth {
    private static int  a = 1_0000_0000;

    public static void main(String[] args) {

        int boyCount = 0;
        int girlCount = 0;

        while(true) {
            try {
                boolean boyf = handleSex(RandomUtil.randomInt(1,100));

                if(boyf){
                    boyCount++;
                }else{
                    girlCount++;
                    while(!boyf){
                        boyf = handleSex(RandomUtil.randomInt(1,100));
                        if(boyf){
                            boyCount++;
                        }else{
                            girlCount++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        System.out.println("男孩数:" + boyCount);
        System.out.println("女孩数:" + girlCount);
    }


    private static boolean handleSex(int randomInt) {
        a--;
        if(a < 0){
            System.out.println(1/0);
        }
        if(randomInt > 50){
            return true;
        }else{
            return false;
        }
    }


}
