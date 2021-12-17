package com.kanboo.www.controller.global;

import com.kanboo.www.domain.entity.board.Board;
import com.kanboo.www.domain.repository.board.boardQueryDSL.BoardDSLRepositoryImpl;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.board.CommentDTO;
import com.kanboo.www.service.inter.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private static final Logger logger
            = LoggerFactory.getLogger(BoardDSLRepositoryImpl.class);

    private final BoardService boardService;

    @GetMapping("/boardTest")
    public List<BoardDTO> testBoard(String selected, String key, int articleOnvView, String codeDetail) {
        return boardService.getAllList(selected, key, articleOnvView, codeDetail);
    }

    @GetMapping("/getArticleNum")
    public long getArticleNum(String key, String selected, String codeDetails) {
        return boardService.getArticleNum(key, selected, codeDetails);
    }

    @GetMapping("/BoardComment")
    public List<CommentDTO> getComments(long boardIdx, int commentsOnView){
        return boardService.getComments(boardIdx, commentsOnView);
    }


}

