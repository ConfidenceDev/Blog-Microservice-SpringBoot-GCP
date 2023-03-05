package me.plurg.creator.dao;

import me.plurg.creator.entity.CreatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepo extends JpaRepository<CreatorEntity, Long> {
}
