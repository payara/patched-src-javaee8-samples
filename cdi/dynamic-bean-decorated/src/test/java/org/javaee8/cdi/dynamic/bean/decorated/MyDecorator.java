package org.javaee8.cdi.dynamic.bean.decorated;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

@Decorator
@Priority(100)
public class MyDecorator implements MyBean {

    @Inject
    @Delegate
    MyBean mybean;

    @Override
    public String sayHi() {
        return mybean.sayHi() + " decorated";
    }

}
