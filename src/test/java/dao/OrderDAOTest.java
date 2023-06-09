package dao;


import com.bookstore.dao.OrderDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDAOTest {

    private static OrderDAO orderDAO;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        orderDAO = new OrderDAO();
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        orderDAO.close();
    }

    @Test
    public void testCreateBookOrder() {
        BookOrder order = new BookOrder();
        Customer customer = new Customer();
        customer.setCustomerId(14);

        order.setCustomer(customer);
        order.setFirstname("Jennie");
        order.setLastname("Ha Minh");
        order.setPhone("12345566");
        order.setAddressLine1("123 South St");
        order.setAddressLine2("Clifton Park");
        order.setCity("New York");
        order.setState("New York");
        order.setCountry("US");
        order.setPaymentMethod("paypal");
        order.setZipcode("123566");

        Set<OrderDetail> orderDetails = new HashSet<>();
        OrderDetail orderDetail = new OrderDetail();

        Book book = new Book(1);
        orderDetail.setBook(book);
        orderDetail.setQuantity(2);
        orderDetail.setSubtotal(68.0f);
        orderDetail.setBookOrder(order);

        orderDetails.add(orderDetail);

        order.setOrderDetails(orderDetails);
        order.setTax(6.8f);
        order.setShippingFee(2.0f);
        order.setSubtotal(68.0f);
        order.setTotal(76.8f);

        BookOrder saveOrder = orderDAO.create(order);
        assertNotNull(saveOrder);
    }

    @Test
    public void testUpdateBookOrderShippingAddress() {
        Integer orderId = 24;
        BookOrder order = orderDAO.get(orderId);
        order.setAddressLine1("New Shipping Address");

        orderDAO.update(order);

        BookOrder updateOrder = orderDAO.get(orderId);

        assertEquals(order.getAddressLine1(), updateOrder.getAddressLine1());
    }

    @Test
    public void testUpdateBookOrderDetail() {
        Integer orderId = 24;
        BookOrder order = orderDAO.get(orderId);

        Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();

        while (iterator.hasNext()) {
            OrderDetail orderDetail = iterator.next();
            if (orderDetail.getBook().getBookId() == 1) {
                orderDetail.setQuantity(3);
                orderDetail.setSubtotal(120);
            }
        }

        orderDAO.update(order);

        BookOrder updateOrder = orderDAO.get(orderId);

        iterator = order.getOrderDetails().iterator();

        int expectedQuantity = 3;
        float expectedSubtotal = 120;
        int actualQuantity = 0;
        float actualSubtotal = 0.0f;

        while (iterator.hasNext()) {
            OrderDetail orderDetail = iterator.next();
            if (orderDetail.getBook().getBookId() ==1) {
                actualQuantity = orderDetail.getQuantity();
                actualSubtotal = orderDetail.getSubtotal();
            }
        }

        assertEquals(expectedQuantity, actualQuantity);
        assertEquals(actualSubtotal, actualSubtotal);
    }


    @Test
    public void testUpdateBookOrderShippingOrder() {
        Integer orderId = 24;
        BookOrder order = orderDAO.get(orderId);
        order.setAddressLine1("New shipping address");
        orderDAO.update(order);
        BookOrder updateOrder = orderDAO.get(orderId);
        assertEquals(order.getAddressLine1(),updateOrder.getAddressLine1());
    }


    @Test
    public void testGet() {
        Integer orderId = 24;
        BookOrder order = orderDAO.get(orderId);
        assertEquals(1,order.getOrderDetails().size());
    }

    @Test
    public void testListAll() {
        List<BookOrder> listOrders = orderDAO.listAll();
        for (BookOrder order : listOrders) {
            System.out.println(order.getOrderId() + " - " + order.getCustomer().getFirstname()
                    + " - " + order.getTotal() + " - " + order.getStatus());
//            for (OrderDetail detail : order.getOrderDetails()) {
//                Book book = detail.getBook();
//                int quantity = detail.getQuantity();
//                float subtotal = detail.getSubtotal();
//                System.out.println("\t" + book.getTitle() + " - " + quantity + " - " + subtotal);
//            }
        }
        assertTrue(listOrders.size() > 0 );
    }

    @Test
    public void testCount() {
        long totalOrders =orderDAO.count();
        assertEquals(1,totalOrders);
    }

    @Test
    public void testDeleteOrder() {
        Integer orderId = 24;
        orderDAO.delete(orderId);
        BookOrder order = orderDAO.get(orderId);
        assertNull(order);
    }

    @Test
    public void testListByCustomerNoOrders() {
        Integer customerId = 25;
        List<BookOrder> listOrder = orderDAO.ListByCustomer(customerId);
        assertTrue(listOrder.isEmpty());
    }

    @Test
    public void testListByCustomerWithOrders() {
        Integer customerId = 14;
        List<BookOrder> listOrder = orderDAO.ListByCustomer(customerId);
        assertTrue(listOrder.size()>0);
    }

//    @Test
//    public void testGetByIdAndCustomerNull() {
//        Integer orderId = 20;
//        Integer customerId = 15;
//        BookOrder order = orderDAO.get(orderId,customerId);
//        assertNull(order);
//    }
//
//    @Test
//    public void testGetByIdAndCustomerNotNull() {
//        Integer orderId = 25;
//        Integer customerId = 14;
//        BookOrder order = orderDAO.get(orderId,customerId);
//        assertNotNull(order);
//    }

    @Test
    public void testListMostRecentSales() {
        List<BookOrder> recentOrder = orderDAO.listMostRecentSales();
        assertEquals(1,recentOrder.size());
    }


}
