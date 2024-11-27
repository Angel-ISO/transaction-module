package org.jala.university.domain.repository;

import org.jala.university.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

}

