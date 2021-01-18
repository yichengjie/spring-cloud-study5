package com.yicj.study.namedcontextfactory2.service.impl;

import com.yicj.study.namedcontextfactory2.service.IHelloContext;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyShowContextBean implements IHelloContext {
    private String name ;
}
