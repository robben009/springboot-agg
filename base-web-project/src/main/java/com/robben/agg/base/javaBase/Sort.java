package com.robben.agg.base.javaBase;

public class Sort {

    /*
        https://www.cnblogs.com/Jason-Xiang/p/8567751.html
     */

    public static void main(String[] args) {
        int a[] = {101, 2, 4, 545, 11, 32, 13131, 4444};
        int b[] = {};

//        BubbleSort(a);
//        select_sort(a);
//        insert_sort(a);
//        shell_sort(a);
//        quickSort(a,0,7);
//        MinHeap_Sort(a,8);

        for (int i : a) {
            System.out.println(i);
        }

    }

    /**
     *冒泡
     基本思想：两个数比较大小，较大的数下沉，较小的数冒起来。
     过程：
     比较相邻的两个数据，如果第二个数小，就交换位置。
     从后向前两两比较，一直到比较最前两个数据。最终最小数被交换到起始的位置，这样第一个最小数的位置就排好了。
     继续重复上述过程，依次将第2.3...n-1个最小数排好位置。
     O(n2)
     */
    public static void BubbleSort(int[] arr){
        int temp;//临时变量
        for(int i=0; i<arr.length-1; i++){   //表示趟数，一共arr.length-1次。
            for(int j=arr.length-1; j>i; j--){
                if(arr[j] < arr[j-1]){
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     * 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
     * 第二次遍历n-2个数，找到最小的数值与第二个元素交换；
     * 。。。
     * 第n-1次遍历，找到最小的数值与第n-1个元素交换，排序完成。
     * O(n2)
     */

    public static void select_sort(int array[]){
        int lenth = array.length;
        for(int i=0;i<lenth-1;i++){
            int minIndex = i;
            for(int j=i+1;j<lenth;j++){
                if(array[j]<array[minIndex]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    /**
     * 插入排序
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     * O(n2)
     */
    public static void  insert_sort(int array[]){
        int lenth = array.length;
        int temp;
        for(int i=0;i<lenth-1;i++){
            for(int j=i+1;j>0;j--){
                if(array[j] < array[j-1]){
                    temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                }else{         //不需要交换
                    break;
                }
            }
        }
    }


    /**
     * 希尔排序
     * 在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
     * 然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序。
     * O(n1.5)
     */
    public static void shell_sort(int array[]){
        int lenth = array.length;
        int temp = 0;
        int incre = lenth;
        while(true){
            incre = incre/2;

            for(int k = 0;k<incre;k++){    //根据增量分为若干子序列

                for(int i=k+incre;i<lenth;i+=incre){

                    for(int j=i;j>k;j-=incre){
                        if(array[j]<array[j-incre]){
                            temp = array[j-incre];
                            array[j-incre] = array[j];
                            array[j] = temp;
                        }else{
                            break;
                        }
                    }
                }
            }
            if(incre == 1){
                break;
            }
        }
    }

    /**
     * 快速排序
     * 先从数列中取出一个数作为key值；
     * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；
     * 对左右两个小数列重复第二步，直至各区间只有1个数。
     * O(N*logN)
     */
    public static void quickSort(int a[],int l,int r){
        if(l>=r){
            return;
        }
        int i = l; int j = r; int key = a[l];//选择第一个数为key
        while(i<j){
            while(i<j && a[j]>=key)//从右向左找第一个小于key的值
                j--;
            if(i<j){
                a[i] = a[j];
                i++;
            }
            while(i<j && a[i]<key)//从左向右找第一个大于key的值
                i++;

            if(i<j){
                a[j] = a[i];
                j--;
            }
        }
        //i == j
        a[i] = key;
        quickSort(a, l, i-1);//递归调用
        quickSort(a, i+1, r);//递归调用
    }

    /**
     * 归并排序
     * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法的一个非常典型的应用。
     * 首先考虑下如何将2个有序数列合并。这个非常简单，只要从比较2个数列的第一个数，谁小就先取谁，
     * 取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，那直接将另一个数列的数据依次取出即可。
     * O(NlogN)
     */
    public static void merge_sort(int a[],int first,int last,int temp[]){
        if(first < last){
            int middle = (first + last)/2;
            merge_sort(a,first,middle,temp);//左半部分排好序
            merge_sort(a,middle+1,last,temp);//右半部分排好序
            mergeArray(a,first,middle,last,temp); //合并左右部分
        }
    }
    //合并 ：将两个序列a[first-middle],a[middle+1-end]合并
    public static void mergeArray(int a[],int first,int middle,int end,int temp[]){
        int i = first;
        int m = middle;
        int j = middle+1;
        int n = end;
        int k = 0;
        while(i<=m && j<=n){
            if(a[i] <= a[j]){
                temp[k] = a[i];
                k++;
                i++;
            }else{
                temp[k] = a[j];
                k++;
                j++;
            }
        }
        while(i<=m){
            temp[k] = a[i];
            k++;
            i++;
        }
        while(j<=n){
            temp[k] = a[j];
            k++;
            j++;
        }

        for(int ii=0;ii<k;ii++){
            a[first + ii] = temp[ii];
        }
    }

    /**
     * -------------------堆排序------------------
     * O(NlogN)
     */
    //构建最小堆
    public static void MakeMinHeap(int a[], int n){
        for(int i=(n-1)/2 ; i>=0 ; i--){
            MinHeapFixdown(a,i,n);
        }
    }
    //从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2
    public static void MinHeapFixdown(int a[],int i,int n){

        int j = 2*i+1; //子节点
        int temp = 0;

        while(j<n){
            //在左右子节点中寻找最小的
            if(j+1<n && a[j+1]<a[j]){
                j++;
            }

            if(a[i] <= a[j])
                break;

            //较大节点下移
            temp = a[i];
            a[i] = a[j];
            a[j] = temp;

            i = j;
            j = 2*i+1;
        }
    }
    public static void MinHeap_Sort(int a[],int n){
        int temp = 0;
        MakeMinHeap(a,n);

        for(int i=n-1;i>0;i--){
            temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            MinHeapFixdown(a,0,i);
        }
    }

    /**
     * 基数排序
     */
    public static void RadixSort(int A[],int temp[],int n,int k,int r,int cnt[]){

        //A:原数组
        //temp:临时数组
        //n:序列的数字个数
        //k:最大的位数2
        //r:基数10
        //cnt:存储bin[i]的个数

        for(int i=0 , rtok=1; i<k ; i++ ,rtok = rtok*r){

            //初始化
            for(int j=0;j<r;j++){
                cnt[j] = 0;
            }
            //计算每个箱子的数字个数
            for(int j=0;j<n;j++){
                cnt[(A[j]/rtok)%r]++;
            }
            //cnt[j]的个数修改为前j个箱子一共有几个数字
            for(int j=1;j<r;j++){
                cnt[j] = cnt[j-1] + cnt[j];
            }
            for(int j = n-1;j>=0;j--){      //重点理解
                cnt[(A[j]/rtok)%r]--;
                temp[cnt[(A[j]/rtok)%r]] = A[j];
            }
            for(int j=0;j<n;j++){
                A[j] = temp[j];
            }
        }
    }

}
