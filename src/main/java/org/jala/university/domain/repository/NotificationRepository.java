package org.jala.university.domain.repository;

import org.jala.university.domain.entities.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {

    public abstract List<Notification> findBySourceAccountIdOrDestinationAccountId(String sourceAccountId, String destinationAccountId);

}
