package com.back.domain.wiseSaying.wiseSaying.controller;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.wiseSaying.service.WiseSayingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class WiseSayingController {
    private final WiseSayingService wiseSayingService;

    @GetMapping("/wiseSayings/write")
    @ResponseBody
    public String wirte(
            @RequestParam String content,
            @RequestParam String author
    ) {
        if (content.isBlank()) {
            throw new IllegalArgumentException("content cannot be null or blank");
        }
        if (author.isBlank()) {
            throw new IllegalArgumentException("author cannot be null or blank");
        }
        WiseSaying wiseSaying = wiseSayingService.write(content, author);

        return "%d번 명언이 생성됐습니다.".formatted(wiseSaying.getId());
    }

    @GetMapping("/wiseSayings")
    @ResponseBody
    public String list() {
        return "<ul>" +
                wiseSayingService.findAll()
                        .stream()
                        .map(wiseSaying ->
                                "<li>%d / %s / %s</li>".formatted(wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent())
                        )
                        .collect(Collectors.joining(""))
                + "</ul>";
    }

    @GetMapping("/wiseSayings/{id}")
    @ResponseBody
    public String detail(@PathVariable int id){
        WiseSaying wiseSaying = wiseSayingService.findById(id).get();

        return """
                <h1>명언 : %s</h1>
                <div>번호 : %d</div>
                <div>작가 : %s</div>
                """.formatted(wiseSaying.getContent(), wiseSaying.getId(), wiseSaying.getAuthor());
    }

    @GetMapping("/wiseSayings/{id}/delete")
    @ResponseBody
    public String delete(
            @PathVariable int id
    ){
        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("%d번 명언은 없습니다".formatted(id))
                );
        wiseSayingService.delete(wiseSaying);

        return "%d번 명언이 삭제됐습니다.".formatted(id);
    }

    @GetMapping("/wiseSayings/{id}/modify")
    @ResponseBody
    public String modify(
            @PathVariable int id,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "") String author
    ){
        if(content.isBlank()){
            throw new IllegalArgumentException("content cannot be blank");
        }
        if(author.isBlank()){
            throw new IllegalArgumentException("author cannot be blank");
        }
        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("%d번 명언은 존재하지 않습니다".formatted(id))
                );

        wiseSayingService.modify(wiseSaying, content, author);

        return "%d번 명언이 수정되었습니다.".formatted(id);
    }
}