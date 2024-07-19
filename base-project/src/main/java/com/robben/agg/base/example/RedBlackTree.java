package com.robben.agg.base.example;

public class RedBlackTree {
    private final int R = 0;
    private final int B = 0;
    
    private Node root;
    
    class Node{
        int data;
        int color = R;
        Node left;
        Node right;
        Node parent;
        
        public Node(int data){
            this.data = data;
        }
    }
    
    //root我们先默认第一个点就行了
    public void insert(Node root,int data){
        if(root.data < data){   //插入到右子树
            if(root.right == null){
                root.right = new Node(data);
            }else{
                insert(root.right, data);
            }
        }else{
            if(root.left == null){
                root.left = new Node(data);
            }else{
                insert(root.left, data);
            }
        }
    }
    
    //左旋说白了就是去修改各个节点的指针而已 归根就是三个Node的指针
    public void leftRotate(Node node){
        //分两种情况
        //1 就是根节点 简单一点，因为上面没有点了 少了操作一个指针 表示就是root节点了
        if(node.parent == null){
            Node right = root.right;
            root.right = right.left; //根据左旋的性质E点的右字数变换为S的左子树
            right.left.parent = root; //新加进来的S左子树会挂在E点上，所以他的parent变换成了R
            root.parent = right;    //S点上浮变成了新的根节点
            right.parent = null; //变成了新根节点  没有了parent
        }else{
            //1 判断当前点在父节点的左子树还是右子树
            if(node == node.parent.left){
                node.parent.left = node.right;  //就是把S浮上来变成了新的左子树
            }else{
                node.parent.right = node.right;
            }
            node.right.left.parent = node ; //S点的原来的左子树会挂到E上面
            node.right.parent = node.parent; //S点上浮 变成E点的父节点
            node.parent = node.right;       //E点的parent会变成原来的S点

            node.right = node.right.left;  //S点子树指针会挂到E点上米阿奴
            node.parent.left = node;       //修改根节点的指针
        }
    }
    
}
