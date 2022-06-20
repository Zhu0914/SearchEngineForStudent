package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TermTupleScanner extends AbstractTermTupleScanner {
    /**
     * 记录buf中已存放的位置
     */
    private int pos = 0;
    /**
     * 临时字符串存放列表
     */
    private List<String> buf;
    /**
     * 字符串分割类，根据标点符号和空白符将字符串分成一个个单词
     */
    private StringSplitter stringSplitter;

    /**
     * 缺省构造函数
     */
    public TermTupleScanner() {
        buf = new ArrayList<>();
        stringSplitter = new StringSplitter();
        stringSplitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    /**
     * 构造函数
     * @param bufferedReader：指定输入流对象，应该关联到一个文本文件
     */
    public TermTupleScanner(BufferedReader bufferedReader) {
        super(bufferedReader);
        buf = new ArrayList<>();
        stringSplitter = new StringSplitter();
        stringSplitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    /**
     * 获得下一个三元组
     * @return termTuple: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        try{
            if(buf.isEmpty()){
                String s;
                StringBuilder sb = new StringBuilder();
                while( (s = input.readLine()) != null){
                    sb.append(s).append("\n");
                }
                s = sb.toString().trim();//去掉空格，还能去掉其他一些多余的符号
                s = s.toLowerCase();
                buf = stringSplitter.splitByRegex(s);
            }
            if(buf.size() == 0)
                return null;
            AbstractTerm term = new Term(buf.get(0));
            buf.remove(0);
            return new TermTuple(term,pos++);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
