package com.uni.jamsession.repositories;

import com.uni.jamsession.model.Message;
import com.uni.jamsession.model.Reaction;
import com.uni.jamsession.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Integer> {
    public Optional<Reaction> findByMessageAndUser(Message message, User user);
}
