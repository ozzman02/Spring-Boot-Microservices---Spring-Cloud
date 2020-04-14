package com.springframework.msscssm.services;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import com.springframework.msscssm.domain.Payment;
import com.springframework.msscssm.domain.PaymentEvent;
import com.springframework.msscssm.domain.PaymentState;
import com.springframework.msscssm.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	
	private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;
	
	private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
		
		Payment payment = paymentRepository.getOne(paymentId);
		
		StateMachine<PaymentState, PaymentEvent> sm = 
				stateMachineFactory.getStateMachine(Long.toString(payment.getId()));
		
		sm.stop();
		
		/*
		 * Set the StateMachine with the state of the payment that we have in the db
		 */
		sm.getStateMachineAccessor().doWithAllRegions(sma -> {
			sma.resetStateMachine(new DefaultStateMachineContext<PaymentState, PaymentEvent>(
					payment.getState(), null, null, null));
		});
		
		sm.start();
		
		return sm;
		
	}
	
	@Override
	public Payment newPayment(Payment payment) {
		payment.setState(PaymentState.NEW);
		return paymentRepository.save(payment);
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> preAuth(long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		return null;
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> authorizePaymnent(long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateMachine<PaymentState, PaymentEvent> declineAuth(long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
