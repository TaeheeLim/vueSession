package com.kanboo.www.domain.repository.board.boardQueryDSL;

import com.kanboo.www.domain.entity.board.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDSLRepository {

    List<Board> getAllList(int nowPage, String keyword, String classification);

    public long getArticleNum();

}
