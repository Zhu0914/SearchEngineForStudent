package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.Arrays;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数
     * @param input：Filter的输入，类型为AbstractTermTupleStream
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     *
     * @return termTuple: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        if (input == null) return null;
        AbstractTermTuple termTuple= input.next();
        if (termTuple == null) return null;
        while (Arrays.binarySearch(StopWords.STOP_WORDS, termTuple.term.getContent()) >= 0){
            termTuple = input.next();
            if (termTuple == null) return null;
        }
        return termTuple;
    }

}
