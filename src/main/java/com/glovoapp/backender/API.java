package com.glovoapp.backender;

import com.glovoapp.backender.courier.Courier;
import com.glovoapp.backender.courier.CourierRepository;
import com.glovoapp.backender.order.model.OrderVM;
import com.glovoapp.backender.order.service.OrderRepository;
import com.glovoapp.backender.order.service.OrdersFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
class API {
    private final String welcomeMessage;

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final OrdersFetcher ordersFetcher;

    @Autowired
    API(
            @Value("${backender.welcome_message}") String welcomeMessage,
            OrderRepository orderRepository,
            CourierRepository courierRepository,
            OrdersFetcher ordersFetcher) {
        this.welcomeMessage = welcomeMessage;
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
        this.ordersFetcher = ordersFetcher;
    }

    @RequestMapping("/")
    @ResponseBody
    String root() {
        return welcomeMessage;
    }

    @RequestMapping("/orders")
    @ResponseBody
    List<OrderVM> orders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(Collectors.toList());
    }

    @RequestMapping("/orders/{courierId:^courier-[0-9A-Fa-f]+$}")
    @ResponseBody
    List<OrderVM> ordersByCourierId(@PathVariable String courierId) {
        final Courier courier = courierRepository.findById(courierId);
        if (courier == null) {
            return Collections.emptyList();
        }

        return ordersFetcher.fetchOrders(courier)
                .stream()
                .map(order -> new OrderVM(order.getId(), order.getDescription()))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SpringApplication.run(API.class);
    }
}
