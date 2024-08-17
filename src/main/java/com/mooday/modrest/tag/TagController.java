package com.mooday.modrest.tag;

import com.mooday.modrest.tag.dto.CreateTagDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam(required = false) String name, @Valid @RequestParam(required = true) String lang) {

        if(lang == null || lang.isEmpty()) {
            throw new IllegalArgumentException("lang is required");
        }

        List<Tag> tags = tagService.getAllTags(name, lang);
        return ResponseEntity.ok().body(tags);
    }

    @PostMapping("/create")
    public ResponseEntity<Tag> createTag(@Valid @RequestBody CreateTagDto createTagDto, HttpServletRequest request) {
        Tag createdTag = tagService.createTag(createTagDto, request);
        return ResponseEntity.ok().body(createdTag);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        Tag tag = tagService.getTag(id);
        return ResponseEntity.ok().body(tag);
    }


    @PutMapping("/{id}/use")
    public ResponseEntity<Void> useTag(@PathVariable Long id) {
        tagService.useTag(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/unuse")
    public ResponseEntity<Void> unuseTag(@PathVariable Long id) {
        tagService.unuseTag(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTag(@PathVariable Long id, @Valid @RequestBody Tag tag) {
        tagService.updateTag(id, tag);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/user")
    public ResponseEntity<List<Tag>> getUserTags(@RequestParam(required = false) String name, @Valid @RequestParam(required = true) String lang, HttpServletRequest request) {

        if(lang == null || lang.isEmpty()) {
            throw new IllegalArgumentException("lang is required");
        }

        List<Tag> tags = tagService.getUserTags(name, lang, request);
        return ResponseEntity.ok().body(tags);
    }

}
