//相同分数的排名相同

public class RankNum {

    public static void main(String[] args) {
        int[] scores = { 10, 9, 9, 8, 6, 5, 5, 5, 4, 1 };

        int prescore = -1;
        int rank = 0;
        int rankAddNum = 0;

        for (int i : scores){
            if (i == prescore){
                System.out.println(i + " - " + rank);
                rankAddNum++;
            }else{
                rank = rank + rankAddNum + 1;
                prescore = i;
                rankAddNum = 0;
                System.out.println(i + " - " + rank);
            }
        }
    }


}
