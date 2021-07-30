package com.hemingyue;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        //通过xml文件中的bean标签来定义Bean
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //System.out.println(context.getBean("person"));

        //通过配置类和@Bean来定义Bean
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        //Object person = context.getBean("person",Person.class);
        //System.out.println(person);

        //BeanDefiniton定义Bean
        //AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        //beanDefinition.setBeanClass(Person.class);
        //context.registerBeanDefinition("person", beanDefinition);
        //Object person = context.getBean("person");
        //System.out.println(person);
        context.registerBean(Person.class,()->{
            Person person = new Person();
            person.setName("何明月");
            return person;
        });
        Person person = context.getBean("person", Person.class);
        System.out.println(person.getName());
        //PersonFactoryBean bean = context.getBean("&person", PersonFactoryBean.class);
        //System.out.println(bean);


    }
}
