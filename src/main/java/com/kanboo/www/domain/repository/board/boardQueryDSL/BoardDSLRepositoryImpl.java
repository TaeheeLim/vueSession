package com.kanboo.www.domain.repository.board.boardQueryDSL;

import com.kanboo.www.domain.entity.board.*;
import com.kanboo.www.domain.entity.global.QCodeDetail;
import com.kanboo.www.domain.entity.global.QMasterCode;
import com.kanboo.www.domain.entity.member.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.QueryParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class BoardDSLRepositoryImpl implements BoardDSLRepository{

    private final EntityManager em;
    private static final Logger logger
            = LoggerFactory.getLogger(BoardDSLRepositoryImpl.class);
//    private JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Override
    public List<Board> getAllList(int articleOnView, String keyword, String selected) {
        QBoard board = QBoard.board;
        QLikes likes = QLikes.likes;
        QBoardReport reports = QBoardReport.boardReport;
        QMember member = QMember.member;
        QCodeDetail codeDetail = QCodeDetail.codeDetail;

        JPAQueryFactory query = new JPAQueryFactory(em);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(selected.equals("memNick")) {
            booleanBuilder.and(
                    board.member.memNick.contains(keyword)
            );
        } else if(selected.equals("boardCN")) {
            booleanBuilder.and(
                    board.boardCn.contains(keyword)
            );
        }

        return query
            .select(board)
            .from(board)
            .innerJoin(board.member, member)
            .fetchJoin()
            .innerJoin(board.codeDetail, codeDetail)
            .fetchJoin()
                .where(
                    (JPAExpressions
                                    .select(reports.count())
                                    .from(reports)
                                    .where(reports.board.boardIdx.eq(board.boardIdx))
                    ).lt(5L)
                            .and(board.delAt.eq("N"))
                            .and(board.codeDetail.codeDetailIdx.eq("1"))
                            .and(booleanBuilder)
            )
            .distinct()
            .offset(articleOnView) .limit(5)
            .fetch();
    }

    @Override
    public long getArticleNum(){
        JPAQueryFactory query = new JPAQueryFactory(em);
        QBoard board = QBoard.board;

        return query.select(board.count()).from(board).fetchCount();
    }

}
