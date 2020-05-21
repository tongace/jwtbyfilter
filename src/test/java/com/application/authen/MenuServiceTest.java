package com.application.authen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuServiceTest {
    @Autowired
    MenuService menuService;
    @Test
    void testMenuOfUsercsampahoNotNull(){
        List<Menu> menuList = menuService.getMenuFromUserId("csamphao");
        assertNotNull(menuList);
    }
    @Test
    void testMenuOfUsercsampahoMustbe11(){
        List<Menu> menuList = menuService.getMenuFromUserId("csamphao");
        assertEquals(11,menuList.size(),"menu of csamphao must be 11 items");
    }

    @Test
    void testMenuOfsuser123MustbeNotNull(){
        List<Menu> menuList = menuService.getMenuFromUserId("suser123");
        assertNotNull(menuList);
    }
    @Test
    void testMenuOfsuser123Mustbe4(){
        List<Menu> menuList = menuService.getMenuFromUserId("suser123");
        assertEquals(4,menuList.size(),"menu of suser123 must be 4 items");
    }
}