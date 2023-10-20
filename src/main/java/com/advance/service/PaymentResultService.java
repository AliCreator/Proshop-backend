package com.advance.service;

import java.util.List;

import com.advance.entity.PaymentResult;

public interface PaymentResultService {

	PaymentResult addPaymentResult(PaymentResult paymentResult); 
	PaymentResult getPaymentResult(Long paymentResultId); 
	List<PaymentResult> getAllPaymentResult(); 
	PaymentResult updatePaymentResult(PaymentResult paymentResult); 
	Boolean deletePaymentResult(Long paymentResultId); 
}
