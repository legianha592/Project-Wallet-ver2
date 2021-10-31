package org.walletAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walletAPI.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
