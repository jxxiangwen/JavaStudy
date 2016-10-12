package cn.edu.shu.knowledge;


import com.google.common.base.Optional;

import java.lang.reflect.Constructor;

/**
 * Created by jxxiangwen on 16-9-23.
 */
public class OptionalTest {
    public void testOptional() throws Exception {

        Optional<Integer> possible=Optional.of(6);
        if(possible.isPresent()){
            System.out.println("possible isPresent:"+possible.isPresent());
            System.out.println("possible value:"+possible.get());
        }
        Constructor<?>[] constructors = OptionalTest.class.getConstructors();
    }
}
