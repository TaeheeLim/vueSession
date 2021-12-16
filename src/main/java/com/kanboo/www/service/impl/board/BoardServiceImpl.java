package com.kanboo.www.service.impl.board;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.repository.board.BoardRepository;
import com.kanboo.www.domain.repository.board.boardQueryDSL.BoardDSLRepositoryImpl;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.service.inter.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private static final Logger logger
            = LoggerFactory.getLogger(BoardDSLRepositoryImpl.class);

    @Override
    public List<BoardDTO> getAllList(int nowPage, String keyword, String selected) {
        List<Board> allList = boardRepository.getAllList(nowPage, keyword, selected);
        List<BoardDTO> result = new ArrayList<>();

//        allList.forEach(item -> {
//            result.add(item.entityToDto());
//        });

        for (Board board : allList) {
            result.add(board.entityToDto());
        }

        return result;
    }

    @Override
    public long getArticleNum(){
        return boardRepository.getArticleNum();
    }
}
