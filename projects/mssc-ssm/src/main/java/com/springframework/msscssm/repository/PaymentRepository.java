package com.springframework.msscssm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springframework.msscssm.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
