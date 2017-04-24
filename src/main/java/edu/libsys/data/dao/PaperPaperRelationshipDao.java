package edu.libsys.data.dao;

import edu.libsys.data.mapper.neo4j.PaperPaperRelationshipMapper;
import edu.libsys.util.ListUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.LinkedList;
import java.util.List;

public class PaperPaperRelationshipDao {
    public List<Integer> getRecommendPaperIdList(final int paperId) {
        List<Integer> integerList = new LinkedList<>();
        try (SqlSession sqlSession = SqlSessionFactory.getNeo4jSqlSession()) {
            PaperPaperRelationshipMapper paperPaperRelationshipMapper =
                    sqlSession.getMapper(PaperPaperRelationshipMapper.class);
            integerList = paperPaperRelationshipMapper.getRecommendPaperIdListByPaperId(paperId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Test
        System.out.println("Got paperId:" + paperId + ", list size: " + integerList.size());
        //去重后返回
        return ListUtil.deDupIntegerList(integerList);
    }
}
