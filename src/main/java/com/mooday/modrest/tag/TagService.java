package com.mooday.modrest.tag;

import com.mooday.modrest.auth.User;
import com.mooday.modrest.security.JwtUtil;
import com.mooday.modrest.tag.dto.CreateTagDto;
import com.mooday.modrest.tag.mapper.TagMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public Tag createTag(CreateTagDto createTagDto, HttpServletRequest request) {

        User user = this.jwtUtil.getUserFromToken(request.getHeader("Authorization").substring(7));
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Tag tag = tagMapper.createTagDtoToTag(createTagDto);

        tag.setUserId(user.getId());

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


    public List<Tag> getUserTags(String name, String lang, HttpServletRequest request) {
        User user = this.jwtUtil.getUserFromToken(request.getHeader("Authorization").substring(7));
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (name == null || name.isEmpty()) {
            return tagRepository.findAllByUserIdAndLangOrderByUsageCountDesc(user.getId(), lang);
        }  else {
            return tagRepository.findByUserIdAndNameContainingIgnoreCaseAndLangOrderByUsageCountDesc(user.getId(), name, lang);
        }
    }


}
