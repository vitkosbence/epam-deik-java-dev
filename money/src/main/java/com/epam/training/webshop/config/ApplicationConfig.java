package com.epam.training.webshop.config;

import com.epam.training.webshop.cart.Cart;
import com.epam.training.webshop.cart.impl.ShoppingCart;
import com.epam.training.webshop.gross.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.gross.impl.HungarianGrossPriceCalculator;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.order.confirmation.OrderConfirmationService;
import com.epam.training.webshop.order.confirmation.impl.DummyOrderConfirmationService;
import com.epam.training.webshop.order.impl.DummyOrderRepository;
import com.epam.training.webshop.presentation.cli.CliInterpreter;
import com.epam.training.webshop.presentation.cli.command.CommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.AddProductCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.ExitCommandLineParser;
import com.epam.training.webshop.presentation.cli.command.impl.OrderCommandLineParser;
import com.epam.training.webshop.product.ProductRepository;
import com.epam.training.webshop.product.impl.DummyProductRepository;
import com.epam.training.webshop.warehouse.WareHouse;
import com.epam.training.webshop.warehouse.impl.DummyWareHouse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

@Configuration
@ComponentScan("com.epam.training.webshop")
public class ApplicationConfig {


    public static Writer cliWriter() {
        return new OutputStreamWriter(System.out);
    }

    public static Reader cliReader() {
        return new InputStreamReader(System.in);
    }

    @Bean
    public static CliInterpreter cliInterpreter() {
        Cart cart = ApplicationConfig.cart();
        CliInterpreter cliInterpreter = new CliInterpreter(cliReader(), cliWriter());
        CommandLineParser commandLineParserChain = new ExitCommandLineParser(cliInterpreter);
        commandLineParserChain.setSuccessor(new AddProductCommandLineParser(ApplicationConfig.productRepository(), cart));
        commandLineParserChain.setSuccessor(new OrderCommandLineParser(cart));
        cliInterpreter.updateCommandLineParser(commandLineParserChain);
        return cliInterpreter;
    }
    public static Cart cart() {
        final ShoppingCart shoppingCart = new ShoppingCart(orderRepository(), productRepository(), grossPriceCalculatorDecorator());
        shoppingCart.subscribe(warehouse());
        shoppingCart.subscribe(orderConfirmationService());
        return shoppingCart;
    }

    public static WareHouse warehouse() {
        return new DummyWareHouse();
    }

    public static OrderConfirmationService orderConfirmationService() {
        return new DummyOrderConfirmationService();
    }

    public static GrossPriceCalculatorDecorator grossPriceCalculatorDecorator() {
        return new HungarianGrossPriceCalculator(GrossPriceCalculatorSingleton.getGrossPriceCalculatorDecorator());
    }

    public static OrderRepository orderRepository() {
        return new DummyOrderRepository();
    }

    public static ProductRepository productRepository() {
        return new DummyProductRepository();
    }
}
