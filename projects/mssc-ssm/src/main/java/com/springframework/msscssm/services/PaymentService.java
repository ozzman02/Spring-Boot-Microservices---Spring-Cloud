package com.springframework.msscssm.services;

import org.springframework.statemachine.StateMachine;

import com.springframework.msscssm.domain.Payment;
import com.springframework.msscssm.domain.PaymentEvent;
import com.springframework.msscssm.domain.PaymentState;

public interface PaymentService {
	
	Payment newPayment(Payment payment);
	
	StateMachine<PaymentState, PaymentEvent> preAuth(long paymentId);
	
	StateMachine<PaymentState, PaymentEvent> authorizePaymnent(long paymentId);
	
	StateMachine<PaymentState, PaymentEvent> declineAuth(long paymentId);
	
}
