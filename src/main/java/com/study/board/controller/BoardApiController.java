package com.study.board.controller;

import java.util.List;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class BoardApiController {
    @Autowired
    private BoardRepository repository;
    @Autowired
    private BoardService boardService;

    @GetMapping("/boards")
    List<Board> boardList() {
        return repository.findAll();
    }

    @PostMapping("/boards")
    Board boardWrite(@RequestBody Board boardWrite) {
        return repository.save(boardWrite);
    }

    @GetMapping("/boards/{id}")
    Board boardView(@PathVariable Integer id) {

        return repository.findById(id).orElse(null);
    }


    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Integer id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
