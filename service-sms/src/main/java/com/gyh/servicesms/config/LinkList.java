package com.gyh.servicesms.config;

public class LinkList<T> {

    private class Node{
        //数据
        T item;
        //下个节点
        Node next;
        //构造器
        public Node(T item, Node next){
            this.item = item;
            this.next = next;
        }
        public Node(T item) {
            this.item = item;
        }
    }
    // 头结点
    private Node head;
    // 尾结点
    private Node tail;
    // 节点个数
    private int size;
    // 链表定义
    public LinkList(){
        this.head = new Node(null, null);
        size = 0;
    }
    // 查找特定位置的链表节点
    public Node get(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        Node temp = this.head;
        for (int i = 0; i < index; i++) {
             temp = temp.next;
        }
        return temp;
    }

    public void add(T data) {
        Node temp = new Node(data);
        // 链表为空时
        if (this.size == 0) {
            head = temp;
            tail = head;
        }
        else {
            Node last = tail;
            last.next = temp;
            this.tail = temp;
        }
        this.size ++;
    }

    // 在练表的第i个位置插入一个值为t数据
    public void insert(int index, T data) throws Exception {
        if (index < 0 || index > this.size) {
            throw new Exception("插入超出范围");
        } else {
            Node newNode = new Node(data);
            // 在头节点插入元素
            if (index == 0) {
                if (this.size > 0) {
                    Node temp = head;
                    newNode.next = temp;
                }
                head = newNode;
            }
        }
    }
}
