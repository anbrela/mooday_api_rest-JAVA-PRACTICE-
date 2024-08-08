package com.mooday.modrest.tag;

import com.mooday.modrest.exception.ForbiddenException;
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
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam(required = false) String name, @RequestParam(required = true) String lang) {
        throw new ForbiddenException("ieeepa");
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) {
        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.ok().body(createdTag);
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
}
