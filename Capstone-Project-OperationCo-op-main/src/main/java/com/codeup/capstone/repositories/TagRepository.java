package com.codeup.capstone.repositories;

import com.codeup.capstone.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}