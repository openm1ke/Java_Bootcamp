package org.example.model;

import org.example.annotations.OrmColumn;
import org.example.annotations.OrmColumnId;
import org.example.annotations.OrmEntity;

@OrmEntity(table = "simple_user")
public class User {
    @OrmColumnId
    private Long id;
    @OrmColumn(name = "first_name", length = 10)
    private String firstName;
    @OrmColumn(name = "last_name", length = 10)
    private String lastName;
    @OrmColumn(name = "age")
    private Integer age;

    // setters/getters
    public User(){}

    public User(Long id, String firstName, String lastName, Integer age){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return lastName;
    }

    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return age;
    }

    @Override
    public String toString() {
        return
            "[\n\tid=" + id + "\n" +
                "\tfirstName=" + firstName + "\n" +
                "\tlastName=" + lastName + "\n" +
                "\tage=" + age + "\n]";
    }
}