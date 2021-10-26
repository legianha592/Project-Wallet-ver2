package org.walletAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walletAPI.entity.TypeRecord;

import java.util.Optional;

@Repository
public interface TypeRecordRepository extends JpaRepository<TypeRecord, Long> {
    Optional<TypeRecord> findByTypeRecordName(String typeRecordName);
}
