package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

import java.util.regex.Pattern;

public class PatternTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数
     * @param input：Filter的输入，类型为AbstractTermTupleStream
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     * @return termTuple: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        if (input == null) return null;
        AbstractTermTuple termTuple =input.next();
        if (termTuple == null) return null;
        while (!Pattern.matches(Config.TERM_FILTER_PATTERN, termTuple.term.getContent())){
            termTuple = input.next();
            if (termTuple == null) return null;
        }
        return termTuple;
    }
}
