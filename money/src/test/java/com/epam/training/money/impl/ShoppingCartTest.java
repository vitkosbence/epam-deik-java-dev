package com.epam.training.money.impl;

import com.epam.training.webshop.cart.impl.ShoppingCart;
import com.epam.training.webshop.gross.impl.GrossPriceCalculatorDecorator;
import com.epam.training.webshop.order.Observer;
import com.epam.training.webshop.order.OrderRepository;
import com.epam.training.webshop.order.confirmation.impl.DummyOrderConfirmationService;
import com.epam.training.webshop.product.Product;
import com.epam.training.webshop.product.ProductRepository;
import com.epam.training.webshop.product.impl.SimpleProduct;
import com.epam.training.webshop.warehouse.impl.DummyWareHouse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class ShoppingCartTest {

    private static final String APPLE_PRODUCT_NAME = "Alma";
    public static final String WEIGHT_PACKAGING = "1kg";
    private static final Product ALMA = SimpleProduct.builder(APPLE_PRODUCT_NAME)
            .withNetPrice(100)
            .withPackaging(WEIGHT_PACKAGING)
            .build();
    private static final Product DINNYE = SimpleProduct.builder("Dinnye")
            .withNetPrice(199)
            .withPackaging(WEIGHT_PACKAGING)
            .build();

    @Mock
    private GrossPriceCalculatorDecorator grossPriceCalculatorDecorator;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;


    private ShoppingCart underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ShoppingCart(
                orderRepository,
                productRepository,
                grossPriceCalculatorDecorator
        );
    }

    @Test
    void testListProductsShouldReturnEmptyListWhenNoProductsAdded() {
        // Given
        List<SimpleProduct> expectedResult = Collections.emptyList();
        // When
        List<Product> actualResult = underTest.getProducts();
        // Then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testListProductShouldReturnTheListOfProductsWhenNotEmpty() {
        // Given
        Product product = SimpleProduct.builder("Alma")
                .build();
        List<Product> products = Collections.singletonList(product);
        underTest = new ShoppingCart(orderRepository, productRepository,
                grossPriceCalculatorDecorator, products, Collections.emptyList(),
                Collections.emptyList()
        );
        // When
        final List<Product> actual = underTest.getProducts();
        // Then
        Assertions.assertEquals(products, actual);
    }

    @Test
    void testAddProductShouldAddProductWhenGivenOne() {
        // Given
        ArrayList<Product> products = Mockito.spy(new ArrayList<>());
        underTest = new ShoppingCart(orderRepository, productRepository,
                grossPriceCalculatorDecorator, products,
                Collections.emptyList(), Collections.emptyList());
        Product alma = SimpleProduct
                .builder(APPLE_PRODUCT_NAME)
                .build();
        /* Fontos, hogy mindig override-olva legyen az 'equals' és a 'hashCode' metódusok,

        mert az 'equals' nélkül csak az objektum referenciák lesznek össze hasonlítva.

        A Lenti Mock konfiguráció nem fog működni, mert a konfigurációban definiált product objektum
        állapota nem egyezik majd meg az 'alma' nevű objektum állapotával.

        BDDMockito.given(productRepository.getProductByName("Alma"))
                .willReturn(Optional.of(
                        SimpleProduct.builder("Alma")
                                .withNetPrice(199)
                                .build()));
        */
        BDDMockito.given(productRepository.getProductByName(APPLE_PRODUCT_NAME))
                .willReturn(Optional.of(alma));
        // When
        underTest.addProduct(APPLE_PRODUCT_NAME);
        // Then
        /* A Spy objektumoknak van állapota de nyomon követhető, hogy milyen hivások történtek rajtuk. */
        Mockito.verify(products).add(alma);
        Assertions.assertEquals(List.of(alma), products);
    }

    @Test
    void testOrderShouldCallSaveOrderOnOrderRepositoryWhenCallOrder() {
        // Given
        Observer confirmationService = Mockito.mock(DummyOrderConfirmationService.class);
        Observer wareHouse =  Mockito.mock(DummyWareHouse.class);
        final List<Observer> observers = List.of(confirmationService, wareHouse);
        underTest = new ShoppingCart(orderRepository, productRepository, grossPriceCalculatorDecorator,
                Collections.emptyList(), observers, Collections.emptyList());
        // When
        underTest.order();
        // Then
        /*
        A Mockito.times(1) VerificationMode-t elhagyhatjuk, ha a hívások elvárt száma egy.
        Mockito.verify(orderRepository).saveOrder(ArgumentMatchers.any());
         */
        Mockito.verify(orderRepository, Mockito.times(1)).saveOrder(ArgumentMatchers.any());
        Mockito.verify(confirmationService).notify(underTest);
        Mockito.verify(wareHouse).notify(underTest);
    }

    @Test
    void testGetTotalGrossPriceShouldReturnAggregatedPriceOfGivenCart() {
        // Given
        double aggregatedPrice = 999.1;
        BDDMockito.given(grossPriceCalculatorDecorator.getAggregatedGrossPrice(underTest)).willReturn(aggregatedPrice);
        // When
        double actual = underTest.getTotalGrossPrice();
        // Then
        Assertions.assertEquals(aggregatedPrice, actual);
    }

    @Test
    void testGetTotalNetPriceShouldReturnAggregatedPriceOfProductsWhenGivenCartWithProducts() {
        // Given
        final List<Product> products = List.of(ALMA, DINNYE);
        underTest = new ShoppingCart(orderRepository, productRepository,
                grossPriceCalculatorDecorator, products, Collections.emptyList(),
                Collections.emptyList());
        // When
        final double actual = underTest.getTotalNetPrice();
        // Then
        Assertions.assertEquals(299, actual);
    }

    @Test
    void testGetDiscountForCouponsShouldReturnZeroWhenGivenCartWithoutAnyCoupons() {
        // Given
        // When
        final double actual = underTest.getDiscountForCoupons();
        // Then
        Assertions.assertEquals(0, actual);
    }

    @Test
    void testSubscribeShouldAddNewObserverWhenGivenOne() {
        // Given
        final List<Observer> observers = Mockito.spy(new ArrayList<>());
        underTest = new ShoppingCart(orderRepository, productRepository,
                grossPriceCalculatorDecorator, Collections.emptyList(), observers, Collections.emptyList());
        Observer confirmationService = new DummyOrderConfirmationService();
        // When
        underTest.subscribe(confirmationService);
        // Then
        Mockito.verify(observers).add(confirmationService);
    }
}