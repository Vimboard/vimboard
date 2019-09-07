package com.github.vimboard.starter;

import org.springframework.beans.factory.annotation.Autowired;

public class FooBean {

    private final FooProperties fooProperties;

    @Autowired
    public FooBean(FooProperties fooProperties) {
        this.fooProperties = fooProperties;
    }

    public String getVar1() {
        return fooProperties.getVar1();
    }

    public String getVar2() {
        return fooProperties.getVar2();
    }
}
