package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.PostingList;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher{


    /**
     * 从指定索引文件打开索引，加载到index对象里. 一定要先打开索引，才能执行search方法
     *
     * @param indexFile ：指定索引文件
     */
    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));

    }

    /**
     * 根据单个检索词进行搜索
     *
     * @param queryTerm ：检索词
     * @param sorter    ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        PostingList postings = (PostingList) index.termToPostingListMapping.get(queryTerm);
        if(postings == null)
            return null;
        List<AbstractHit> hits = new ArrayList<>();
        for (AbstractPosting posting : postings.getPostings()) {
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            termPostingMapping.put(queryTerm, posting);
            Hit hit = new Hit(posting.getDocId(), index.docIdToDocPathMapping.get(posting.getDocId()), termPostingMapping);
            sorter.score(hit);
            hits.add(hit);
        }
        sorter.sort(hits);
        AbstractHit[] arr1 = hits.toArray(new AbstractHit[0]);
        AbstractHit[] arr2 = new AbstractHit[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[arr1.length - 1 - i];
        }
        return arr2;
    }

    /**
     * 根据二个检索词进行搜索
     *
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter     ：    排序器
     * @param combine    ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        PostingList postings1 = (PostingList) index.termToPostingListMapping.get(queryTerm1);
        PostingList postings2 = (PostingList) index.termToPostingListMapping.get(queryTerm2);
        postings1.sort();
        postings2.sort();
        List<AbstractHit> hits = new ArrayList<>();
        ListIterator<AbstractPosting> iterator1 = postings1.getPostings().listIterator();
        ListIterator<AbstractPosting> iterator2 = postings2.getPostings().listIterator();
        if (combine == LogicalCombination.AND) {

            while (iterator1.hasNext() && iterator2.hasNext()) {
                AbstractPosting posting1 = iterator1.next();
                AbstractPosting posting2 = iterator2.next();
                if (posting1.getDocId() < posting2.getDocId()) {
                    iterator2.previous();
                } else if (posting1.getDocId() > posting2.getDocId()) {
                    iterator1.previous();
                } else {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    termPostingMapping.put(queryTerm2, posting2);
                    Hit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                }
            }
            sorter.sort(hits);
            AbstractHit[] arr1 = hits.toArray(new AbstractHit[0]);
            AbstractHit[] arr2 = new AbstractHit[arr1.length];
            for (int i = 0; i < arr1.length; i++) {
                arr2[i] = arr1[arr1.length - 1 - i];
            }
            return arr2;
        } else {

            while (iterator1.hasNext() && iterator2.hasNext()) {
                AbstractPosting posting1 = iterator1.next();
                AbstractPosting posting2 = iterator2.next();
                if (posting1.getDocId() < posting2.getDocId()) {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    Hit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                    iterator2.previous();
                } else if (posting1.getDocId() > posting2.getDocId()) {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm2, posting2);
                    Hit hit = new Hit(posting2.getDocId(), index.docIdToDocPathMapping.get(posting2.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                    iterator1.previous();
                } else {
                    Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                    termPostingMapping.put(queryTerm1, posting1);
                    termPostingMapping.put(queryTerm2, posting2);
                    Hit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                    sorter.score(hit);
                    hits.add(hit);
                }
            }
            while (iterator1.hasNext()) {
                AbstractPosting posting1 = iterator1.next();
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                Hit hit = new Hit(posting1.getDocId(), index.docIdToDocPathMapping.get(posting1.getDocId()), termPostingMapping);
                sorter.score(hit);
                hits.add(hit);
            }

            while (iterator2.hasNext()) {
                AbstractPosting posting2 = iterator2.next();
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm2, posting2);
                Hit hit = new Hit(posting2.getDocId(), index.docIdToDocPathMapping.get(posting2.getDocId()), termPostingMapping);
                sorter.score(hit);
                hits.add(hit);
            }
            sorter.sort(hits);
            AbstractHit[] arr1 = hits.toArray(new AbstractHit[0]);
            AbstractHit[] arr2 = new AbstractHit[arr1.length];
            for (int i = 0; i < arr1.length; i++) {
                arr2[i] = arr1[arr1.length - 1 - i];
            }
            return arr2;
        }
    }
}
