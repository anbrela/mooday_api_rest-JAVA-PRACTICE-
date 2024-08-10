package com.mooday.modrest.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTags(String name, String lang) {
        if (name == null || name.isEmpty()) {
            return tagRepository.findAllByLangOrderByUsageCountDesc(lang);
        }  else {
            return tagRepository.findByNameContainingIgnoreCaseAndLangOrderByUsageCountDesc(name, lang);
        }
    }
    public void useTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setUsageCount(tag.getUsageCount() + 1);
        tagRepository.save(tag);
    }

    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElseThrow();
    }

    public void unuseTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow();
        tag.setUsageCount(tag.getUsageCount() - 1);
        tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    public void updateTag(Long id, Tag tag) {
        Tag existingTag = tagRepository.findById(id).orElseThrow();
        existingTag.setName(tag.getName());
        tagRepository.save(existingTag);
    }


}
