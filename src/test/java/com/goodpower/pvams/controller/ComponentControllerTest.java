package com.goodpower.pvams.controller;

import com.goodpower.pvams.controller.maintaininfo.ComponentController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComponentControllerTest {

    @Autowired
    ComponentController componentController;

    @Test
    public void confirm(){
        componentController.confirm(1L,6L,1);
    }

}
