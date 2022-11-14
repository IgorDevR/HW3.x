package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.hogwarts.school.entity.Avatar;

import java.util.Optional;

public interface AvatarRepositoryPaging extends PagingAndSortingRepository<Avatar, Long> {


}
