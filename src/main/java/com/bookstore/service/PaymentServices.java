package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaymentServices {

    //paypal developer credentials goes here
    //left balnk for now
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";

    private String mode = "sandbox";
    private HttpServletRequest request;
    private HttpServletResponse response;

    public PaymentServices(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }


    public void authorizePayment(BookOrder order) throws ServletException, PayPalRESTException {

        Payer payer = getPlayerInformation(order);
        RedirectUrls redirectUrls = getRedirectURLs();
        Amount amount = getAmountDetails(order);
        List<Transaction> transactions = getTransactionInformation(order);
        Payment requestPayment = new Payment();
        requestPayment.setPayer(payer)
                .setRedirectUrls(redirectUrls)
                .setIntent("authorize")
                .setTransactions(transactions);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, mode);
        try {
            Payment authorizedPayment = requestPayment.create(apiContext);
            String approvalUrl = getApprovalURL(authorizedPayment);
             response.sendRedirect(approvalUrl);
        } catch (PayPalRESTException | IOException e) {
            e.printStackTrace();
            throw new ServletException("Error in authorizing payment");
        }
    }

    private String getApprovalURL(Payment authorizedPayment){
        String approvalURL = null;
       List<Links> links =  authorizedPayment.getLinks();
        for(Links link: links){
            if(link.getRel().equalsIgnoreCase("approval_Url")){
                approvalURL = link.getHref();
                break;
            }
        }
        return null;
    }

    private Payer getPlayerInformation(BookOrder order){
        Payer payer = new Payer();
        payer.setPaymentMethod("paypl");

        PayerInfo payerInfo = new PayerInfo();
        Customer customer = order.getCustomer();
        payerInfo.setFirstName (customer.getFirstname());
        payerInfo.setLastName (customer.getLastname());
        payerInfo.setEmail (customer.getEmail());
        return payer;
    }

    private RedirectUrls getRedirectURLs(){
        String requestURL = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        String baseURL = requestURL.replace(requestURI,"").concat(request.getContextPath());

        RedirectUrls redirectUrls = new RedirectUrls();
        String cancelUrl = baseURL.concat("/view_cart");
        String returnUrl = baseURL.concat("/review_payment");
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(returnUrl);
        return redirectUrls;
    }

    private Amount getAmountDetails(BookOrder order){
        Details details = new Details();
        details.setShipping(String.format("%.2f",(order.getShippingFee())));
        details.setTax(String.format("%.2f",(order.getTax())));
        details.setSubtotal(String.format("%.2f",(order.getSubtotal())));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setDetails(details);
        amount.setTotal(String.format("%.2f",(order.getTotal())));

        return amount;
    }

    private ShippingAddress getRecipientInformation (BookOrder order){
        ShippingAddress shippingAddress = new ShippingAddress();
        String recipientName = order.getFirstname()+" "+ order.getLastname();
        shippingAddress.setRecipientName(recipientName)
                .setPhone(order.getPhone())
                .setLine1(order.getAddressLine1())
                .setLine1(order.getAddressLine1())
                .setCity(order.getCity())
                .setState(order.getState())
                .setCountryCode(order.getCountry())
                .setPostalCode(order.getZipcode());

        return shippingAddress;
    }

    private List<Transaction> getTransactionInformation(BookOrder order) {
        Transaction transaction = new Transaction();
        transaction.setDescription("Book ordered on Evergreen Books");
        Amount amount = getAmountDetails(order);
        transaction.setAmount(amount);

        ItemList itemList = new ItemList();
        ShippingAddress shippingAddress = getRecipientInformation(order);
        itemList.setShippingAddress(shippingAddress);
        List<Item> paypalItems = new ArrayList<>();
        Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
        while (iterator.hasNext()) {
            OrderDetail orderDetail = iterator.next();
            Book book = orderDetail.getBook();
            Integer quantity = orderDetail.getQuantity();

            Item paypalItem = new Item();
            paypalItem.setCurrency("USD").setName(book.getTitle())
                    .setQuantity(String.valueOf(quantity))
                    .setPrice(String.format("%.2f",(book.getPrice())));
            paypalItems.add(paypalItem);
        }

        itemList.setItems(paypalItems);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);
        return listTransaction;

    }

    public void reviewPayment() throws ServletException {

        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerId");

        if(paymentId ==null || payerId == null) {
            throw new ServletException("error in displaying payment review");
        }
        APIContext apiContext = new APIContext(CLIENT_ID,CLIENT_SECRET,mode);
        try{
            Payment payment = Payment.get(apiContext,paymentId);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();


            request.setAttribute("Payer", payerInfo);
            request.setAttribute("recipient", shippingAddress);
            request.setAttribute("transaction",transaction);

            String reviewPage = "frontend/review_payment.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
            request.getRequestDispatcher(reviewPage).forward(request,response);
        }catch(PayPalRESTException | IOException e){
            e.printStackTrace();
            throw new ServletException("Error in getting payment details from paypal.");
        }

    }

    public Payment executePayment() throws PayPalRESTException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerId");

        PaymentExecution  paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);
        APIContext apiContext = new APIContext (CLIENT_ID, CLIENT_SECRET, mode);

      return  payment.execute(apiContext,paymentExecution);

    }
}
