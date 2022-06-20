package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {

    /**
     * 缺省构造函数
     */
    public TermTuple() {
    }
    /**
     * 构造函数
     * @param term  ：单词
     * @param curPos   ：单词出现的当前位置
     */
    public TermTuple(AbstractTerm term, int curPos) {
        this.term = term;
        this.curPos = curPos;
    }

    /**
     * 判断二个三元组内容是否相同
     * @param other ：要比较的另外一个三元组
     * @return 如果内容相等（三个属性内容都相等）返回true，否则返回false
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getClass() != this.getClass()) {
            return false;
        } else {
            TermTuple o = (TermTuple)other;
            return term.equals(o.term) && curPos == o.curPos;
        }
    }

    /**
     * 获得三元组的字符串表示
     * @return ： 三元组的字符串表示
     */
    @Override
    public String toString() {
        return term.toString() + ", position: " + curPos + ", freq: " + freq;
    }
}
