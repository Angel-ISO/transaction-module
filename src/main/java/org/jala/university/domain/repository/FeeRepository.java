package org.jala.university.domain.repository;

import org.jala.university.domain.entities.Fee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeeRepository extends BaseRepository<Fee, UUID> {
    @Query(value = "SELECT SUM(amount) FROM fees", nativeQuery = true)
    double totalFees();

    @Query("SELECT f.feeName, SUM(t.amount) FROM Transaction t JOIN t.fee f GROUP BY f.feeName")
    List<Object[]> sumFeesByType();
}

