package edu.libsys.data.dao;

import edu.libsys.conf.Conf;
import edu.libsys.data.mapper.mariadb.BookMapperForMariaDB;
import edu.libsys.data.mapper.neo4j.BookMapperForNeo4j;
import edu.libsys.entity.Book;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class BookDao implements Serializable {
    public Book getBookById(final int id) {
        Book book = null;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            book = bookMapperForMariaDB.getBookById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> getBookListByIdList(final List<Integer> integerList) {
        List<Book> bookList = new LinkedList<>();
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            for (Integer id : integerList) {
                if (id != 0) {
                    bookList.add(bookMapperForMariaDB.getBookById(id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public int addBook(final Book book) {
        int status = 0;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            bookMapperForMariaDB.addBook(book);
            sqlSession.commit();
            status = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public int updateBook(final Book book) {
        int status = 0;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            bookMapperForMariaDB.updataBook(book);
            sqlSession.commit();
            status = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public int deleteBook(final int marcRecId) {
        int status = 0;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            bookMapperForMariaDB.deleteBook(marcRecId);
            sqlSession.commit();
            status = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public int countBook() {
        int count = 0;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            count = bookMapperForMariaDB.countBook();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Book> getBookListBySearchTitle(final String keyWord) {
        List<Integer> idArray = JedisDao.search(0, keyWord);
        return getBookListByIdList(idArray);
    }

    public List<Book> getBookListBySearchAuthor(final String keyWord) {
        List<Book> bookList = null;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            bookList = bookMapperForMariaDB.getBookListBySearchAuthor(keyWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Book> getBookList(final int page, final int size) {
        List<Book> bookList = null;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            bookList = bookMapperForMariaDB.getBookList(page, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * 点赞
     *
     * @param id     需要点赞的图书
     * @param weight 权重编号
     * @return 状态
     */
    public int updateALike(final int id, final int weight) {
        int status = 0;
        try (SqlSession sqlSession = SqlSessionFactory.getNeo4jSqlSession()) {
            BookMapperForNeo4j bookMapperForNeo4j = sqlSession.getMapper(BookMapperForNeo4j.class);
            if (weight == 1) {
                bookMapperForNeo4j.addWeight1(id, Conf.WEIGHT_OF_A_LIKE);
            } else if (weight == 2) {
                bookMapperForNeo4j.addWeight2(id, Conf.WEIGHT_OF_A_LIKE);
            } else if (weight == 3) {
                bookMapperForNeo4j.addWeight3(id, Conf.WEIGHT_OF_A_LIKE);
            }
            sqlSession.commit();
            status = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public int disLikePlusOne(final int id) {
        int status = 0;
        try (SqlSession sqlSession = SqlSessionFactory.getMariaDBSqlSession()) {
            BookMapperForMariaDB bookMapperForMariaDB = sqlSession.getMapper(BookMapperForMariaDB.class);
            bookMapperForMariaDB.disLikeCountPlusOne(id);
            sqlSession.commit();
            status = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
