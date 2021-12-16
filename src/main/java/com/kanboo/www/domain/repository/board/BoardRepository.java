package com.kanboo.www.domain.repository.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.repository.board.boardQueryDSL.BoardDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardDSLRepository {
    List<Board> getAllList(int nowPage, String keyword, String classification);
}
