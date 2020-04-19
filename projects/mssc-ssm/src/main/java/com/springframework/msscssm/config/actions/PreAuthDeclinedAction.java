package com.springframework.msscssm.config.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.springframework.msscssm.domain.PaymentEvent;
import com.springframework.msscssm.domain.PaymentState;

@Component
public class PreAuthDeclinedAction implements Action<PaymentState, PaymentEvent> {

	@Override
	public void execute(StateContext<PaymentState, PaymentEvent> context) {
		System.out.println("Sending Notification of PreAuth DECLINED");
		
	}

}
