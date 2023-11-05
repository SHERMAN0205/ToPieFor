/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topiefor.service.impl;

import com.topiefor.dao.PaymentDao;
import com.topiefor.models.Payment;
import com.topiefor.service.PaymentService;
import java.util.Random;

/**
 *
 * @author Train
 */
public class PaymentServiceImpl implements PaymentService {

    private PaymentDao PaymentDaoImpl;
    private boolean approved = false;

    public PaymentServiceImpl(PaymentDao PaymentDao) {
        setPaymentDaoImpl(PaymentDao);
    }

    @Override
    public boolean makePayment(String paymentMethod, double amount) {
        if (randomAmount() >= amount) {
            return approved = true;
        }
        return approved;
    }

    @Override
    public boolean addPayment(Payment payment, int orderID, double totAmount) {

        return payment == null ? false : PaymentDaoImpl.addPayment(payment, orderID, totAmount);

    }

    private double randomAmount() {

        Random rand = new Random();

        double min = 10.0;
        double max = 1000.0;
        return min + (max - min) * rand.nextDouble();

    }

    public void setPaymentDaoImpl(PaymentDao PaymentDaoImpl) {
        this.PaymentDaoImpl = PaymentDaoImpl;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

}
