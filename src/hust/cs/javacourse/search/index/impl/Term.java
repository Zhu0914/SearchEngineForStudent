package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Term extends AbstractTerm {

    /**
     * 缺省构造函数
     */
    public Term() {}
    /**
     * 构造函数
     * @param content ：Term内容
     */
    public Term(String content) {
        super(content);
    }

    /**
     * 返回Term的字符串表示
     * @return 字符串
     */
    @Override
    public String toString() {
        return content;
    }

    /**
     * 返回Term内容
     * @return Term内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置Term内容
     * @param content：Term的内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 判断二个Term内容是否相同
     * @param other ：要比较的另外一个Term
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getClass() != this.getClass()){
            return false;
        }else return content.equals(((Term)other).getContent());

    }

    /**
     * 比较二个Term大小（按字典序）
     * @param other： 要比较的Term对象
     * @return ： 返回二个Term对象的字典序差值
     */
    @Override
    public int compareTo(AbstractTerm other) {
        return content.compareTo(other.getContent());
    }

    /**
     * 写到二进制文件
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            content = (String) (in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
