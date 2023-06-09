package com.study.board.controller;


import java.util.List;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
class BoardApiController {
    @Autowired
    private BoardRepository repository;

    @CrossOrigin
    @GetMapping("/boards")
    List<Board> boardList() {
        return repository.findAll();
    }
    @CrossOrigin
    @PostMapping("/boards/post")
    Board boardWrite(@RequestBody Board boardWrite) {
        return repository.save(boardWrite);
    }
    @CrossOrigin
    @GetMapping("/boards/{id}")
    Board boardView(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }
    @CrossOrigin
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

    @CrossOrigin
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
