package com.springframework.msscssm.services;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.msscssm.domain.Payment;
import com.springframework.msscssm.domain.PaymentEvent;
import com.springframework.msscssm.domain.PaymentState;
import com.springframework.msscssm.repository.PaymentRepository;

@SpringBootTest
public class PaymentServiceImplTest {
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	Payment payment;
	
	@BeforeEach
	void setUp() {
		payment = Payment.builder().amount(new BigDecimal("12.99")).build();
	}
	
	@Transactional
	@Test
	void preAuth() {
		
		Payment savedPayment = paymentService.newPayment(payment);
		
		System.out.println(savedPayment.getState());
		
		StateMachine<PaymentState, PaymentEvent> sm = 
				paymentService.preAuth(savedPayment.getId());
		
		paymentService.preAuth(savedPayment.getId());
		
		Payment preAuthPayment = paymentRepository.getOne(savedPayment.getId());
		
		System.out.println(sm.getState().getId());
		
		System.out.println(preAuthPayment);
	}
	
}
