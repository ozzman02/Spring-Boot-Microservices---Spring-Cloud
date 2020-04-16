package com.springframework.msscssm.services;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.msscssm.domain.Payment;
import com.springframework.msscssm.domain.PaymentEvent;
import com.springframework.msscssm.domain.PaymentState;
import com.springframework.msscssm.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
	
	public static final String PAYMENT_ID_HEADER = "payment_id";
	
	private final PaymentRepository paymentRepository;
	
	private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;
	
	private final PaymentStateChangeInterceptor paymentStateChangeInterceptor;
	
	private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
		
		Payment payment = paymentRepository.getOne(paymentId);
		
		StateMachine<PaymentState, PaymentEvent> sm = 
				stateMachineFactory.getStateMachine(Long.toString(payment.getId()));
		
		sm.stop();
		
		/*
		 * Set the StateMachine with the state of the payment that we have in the db
		 */
		sm.getStateMachineAccessor().doWithAllRegions(sma -> {
			sma.addStateMachineInterceptor(paymentStateChangeInterceptor);
			sma.resetStateMachine(new DefaultStateMachineContext<PaymentState, PaymentEvent>(
					payment.getState(), null, null, null));
		});
		
		sm.start();
		
		return sm;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void sendEvent(Long paymentId, StateMachine<PaymentState, PaymentEvent> sm, PaymentEvent event) {
		Message msg = MessageBuilder.withPayload(event).setHeader(PAYMENT_ID_HEADER, paymentId).build();
		sm.sendEvent(msg);
	}
	
	@Override
	public Payment newPayment(Payment payment) {
		payment.setState(PaymentState.NEW);
		return paymentRepository.save(payment);
	}

	@Transactional
	@Override
	public StateMachine<PaymentState, PaymentEvent> preAuth(long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		sendEvent(paymentId, sm, PaymentEvent.PRE_AUTH_APPROVED);
		return sm;
	}

	@Transactional
	@Override
	public StateMachine<PaymentState, PaymentEvent> authorizePaymnent(long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		sendEvent(paymentId, sm, PaymentEvent.AUTH_APPROVED);
		return sm;
	}
	
	@Transactional
	@Override
	public StateMachine<PaymentState, PaymentEvent> declineAuth(long paymentId) {
		StateMachine<PaymentState, PaymentEvent> sm = build(paymentId);
		sendEvent(paymentId, sm, PaymentEvent.AUTH_DECLINED);
		return null;
	}

}
