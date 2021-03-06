package edu.libsys.util;

import edu.libsys.entity.Book;
import edu.libsys.entity.Paper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {
    /**
     * 整数列表去重
     *
     * @param integerList 需要去重的整数列表
     * @return 去重后的证书列表
     */
    public static List<Integer> deDupIntegerList(List<Integer> integerList) {
        //http://stackoverflow.com/questions/27464781/creating-distinct-list-from-existing-list-in-java-7-and-8
        return integerList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 根据isbn号对图书列表去重
     *
     * @param bookList 需要去重的图书列表
     * @return 去重后的图书列表
     */
    public static List<Book> deDupBookList(List<Book> bookList) {
        List<Book> resultBookList = new LinkedList<>();
        //isbn可能含有“x”，故将“Long”改为“String”
        List<String> isbnList = new LinkedList<>();
        for (Book book : bookList) {
            String isbn = book.getIsbn().replace("-", "");
            if (!isbnList.contains(isbn)) {
                isbnList.add(isbn);
                resultBookList.add(book);
            }
        }
        return resultBookList;
    }

    /**
     * 根据paperId对论文列表去重
     *
     * @param paperList 需要去重的论文列表
     * @return 去重后的论文列表
     */
    public static List<Paper> deDupPaperList(List<Paper> paperList) {
        List<Paper> resultPaperList = new LinkedList<>();
        List<String> paperIdList = new LinkedList<>();
        for (Paper paper : paperList) {
            String paperId = paper.getPaperId().replace(".nh", "");
            if (!paperIdList.contains(paperId)) {
                paperIdList.add(paperId);
                resultPaperList.add(paper);
            }
        }
        return resultPaperList;
    }
}
