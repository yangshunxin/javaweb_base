package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangshunxin
 * @create 2021-07-06-14:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private int age;
}
