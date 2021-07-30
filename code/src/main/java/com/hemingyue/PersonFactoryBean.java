package com.hemingyue;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

//@Component("person")
public class PersonFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        System.out.println("persondingyi");
        return new Person();
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

}
