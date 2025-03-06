package com.example.demo.Repositorie;

import com.example.demo.Controllers.MpesaSTKTransactions;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MpesaTransactionRepository extends JpaRepository<MpesaSTKTransactions, Long> {
//    Optional<MpesaTransaction> findStudentById(@NonNull Long student_id);
    Optional<?> findByMerchantRequestID(String  studentId);
}
