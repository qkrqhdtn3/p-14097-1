package com.back.domain.wiseSaying.wiseSaying.controller;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WiseSayingController {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    @GetMapping("/wiseSayings/write")
    @ResponseBody
    public String wirte(
            String content,
            String author
    ) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content cannot be null or blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author cannot be null or blank");
        }
        int id = ++lastId;

        WiseSaying wiseSaying = new WiseSaying(id, content, author);

        wiseSayings.add(wiseSaying);

        return "%d번 명언이 생성됐습니다.".formatted(id);
    }

    @GetMapping("/wiseSayings")
    @ResponseBody
    public String list() {
        return "<ul>" +
                wiseSayings
                        .stream()
                        .map(wiseSaying ->
                                "<li>%d / %s / %s</li>".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent())
                        )
                        .collect(Collectors.joining(""))
                + "</ul>";
    }

    @GetMapping("/wiseSayings/delete/{id}")
    @ResponseBody
    public String delete(
            @PathVariable int id
    ){
        WiseSaying wiseSaying = findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("%d번 명언은 없습니다".formatted(id))
                );
        wiseSayings.remove(wiseSaying);

        return "%d번 명언이 삭제됐습니다.".formatted(id);
    }

    public Optional<WiseSaying> findById(int id){
        return wiseSayings
                .stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst();
    }
}