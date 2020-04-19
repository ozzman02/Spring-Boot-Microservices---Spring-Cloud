package com.springframework.msscssm.config.guards;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import com.springframework.msscssm.domain.PaymentEvent;
import com.springframework.msscssm.domain.PaymentState;
import com.springframework.msscssm.services.PaymentServiceImpl;

@Component
public class PaymentIdGuard implements Guard<PaymentState, PaymentEvent> {

	@Override
	public boolean evaluate(StateContext<PaymentState, PaymentEvent> context) {
		return context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER) != null;
	}

}
