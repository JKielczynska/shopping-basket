package com.onlineshop.shoppingbasket.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.onlineshop.shoppingbasket.domain.Basket;
import com.onlineshop.shoppingbasket.domain.BasketProduct;
import com.onlineshop.shoppingbasket.domain.Product;
import com.onlineshop.shoppingbasket.service.BasketService;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BasketController.class)
public class BasketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketService basketService;

    @Test public void shouldOpenNewBasket() throws Exception {
         //Given
         Basket basket = new Basket(1L,true);
         when(basketService.openNewBasket()).thenReturn(basket);

         Gson gson = new Gson();
         String jsonContent = gson.toJson(basket);
         //When & Then
         mockMvc.perform(post("/baskets").contentType(MediaType.APPLICATION_JSON)
                 .characterEncoding("UTF-8").content(jsonContent))
                 .andExpect(status().isOk());
    }

    @Test
    public void shouldAddItemToBasket() throws Exception {
        //Given
        Basket basket = prepareTestBasket();
        when(basketService.addProductToBasket(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyInt())).thenReturn(basket);

        //When & Then
        mockMvc.perform(post("/baskets/1/1/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateProductInBasket() throws Exception {
        //Given
        Basket basket = prepareTestBasket();
        when(basketService.updateProductInBasket(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyInt())).thenReturn(basket);
        //When & Then
        mockMvc.perform(put("/baskets/1/1/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void calculateTotalPriceAndCloseBasket() throws Exception {
        //Given
        Basket basket = prepareTestBasket();
        when(basketService.totalPrice(ArgumentMatchers.anyLong())).thenReturn(BigDecimal.valueOf(70));
        //When & Then
        mockMvc.perform(get("/baskets/" + basket.getBasketId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Basket prepareTestBasket() {
        Basket basket = new Basket(1L,true);
        Product product = new Product(10L,"A", new BigDecimal(40), 3, new BigDecimal(70));
        BasketProduct basketProduct = new BasketProduct(3);

        return basket;
    }
}