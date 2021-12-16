package com.kanboo.www.service.inter.board;


import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.dto.board.BoardDTO;

import java.util.List;

public interface BoardService {
    List<BoardDTO> getAllList(int nowPage, String keyword, String classification);

    long getArticleNum();
}
