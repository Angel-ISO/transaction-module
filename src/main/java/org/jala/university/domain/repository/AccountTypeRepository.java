package org.jala.university.domain.repository;

import org.jala.university.domain.entities.AccountType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountTypeRepository extends BaseRepository<AccountType, UUID> {

    @Query("select a from AccountType a where a.typeName = :typeName")
    AccountType findByTypeName(String typeName);

}
