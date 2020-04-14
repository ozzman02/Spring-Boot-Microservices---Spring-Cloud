package com.springframework.msscssm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springframework.msscssm.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
