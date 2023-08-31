package com.github.axinger;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class FastJson2Test {

    static Person person;

    @BeforeAll
    public static void before() {
        person = Person.builder()
                .id("1")
                .name("jin")
                .books(List.of(

                        Book.builder()
                                .id("1")
                                .name("西游记")
                                .build(),

                        Book.builder()
                                .id("2")
                                .name("水浒传")
                                .build()
                ))
                .build();
    }

    @Test
    void test1() throws JsonProcessingException {

        Person person = Person.builder()
                .id("1")
                .name("jin")
                .books(List.of(

                        Book.builder()
                                .id("1")
                                .name("西游记")
                                .build(),

                        Book.builder()
                                .id("2")
                                .name("水浒传")
                                .build()
                ))
                .build();


        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(person), JSONObject.class);


        String book1Name = jsonObject.getJSONArray("books").getJSONObject(1).getString("name");
        System.out.println("book1Name = " + book1Name);
        jsonObject.getJSONArray("books").getJSONObject(1).put("name", "tom");
        System.out.println("jsonObject = " + jsonObject);
    }


    @Test
    void test_JSONObject() {
        Person person = Person.builder()
                .id("1")
                .name("jin")
                .books(List.of(
                        Book.builder()
                                .id("1")
                                .name("西游记")
                                .build(),
                        Book.builder()
                                .id("2")
                                .name("水浒传")
                                .build()
                ))
                .build();
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(person), new TypeReference<>() {
        });
        JSONObject jsonObject = new JSONObject(map);
        jsonObject.put("name", "修改用户名");
        jsonObject.getJSONArray("books").getJSONObject(1).put("name", "修改书名");
        System.out.println("jsonObject2 = " + jsonObject);
    }

    @Test
    void test_JSONObject2() {

        Person person = Person.builder()
                .id("1")
                .name("jin")
                .books(List.of(
                        Book.builder()
                                .id("1")
                                .name("西游记")
                                .build(),
                        Book.builder()
                                .id("2")
                                .name("水浒传")
                                .build()
                ))
                .build();

        JSONObject jsonObject = JSONObject.from(person);
        jsonObject.put("name", "修改用户名");
        jsonObject.getJSONArray("books").getJSONObject(1).put("name", "修改书名");
        System.out.println("jsonObject2 = " + jsonObject);
    }

    @Test
    void test3() throws JsonProcessingException {


        Person person = Person.builder()
                .id("1")
                .name("jin")
                .books(List.of(

                        Book.builder()
                                .id("1")
                                .name("西游记")
                                .build(),

                        Book.builder()
                                .id("2")
                                .name("水浒传")
                                .build()
                ))
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(person);

        JsonNode jsonNode = objectMapper.readTree(s);


        System.out.println("jsonNode.get(\"name\").asText() = " + jsonNode.get("name").asText());

        System.out.println("jsonNode.path(\"name\").asText() = " + jsonNode.path("name").asText());


    }


    @Test
    void test4() {

        System.out.println("person = " + person);

        String jsonString = JSONObject.toJSONString(person);
        System.out.println("jsonString = " + jsonString);


        Person person1 = JSONObject.parseObject(jsonString).to(Person.class);
        System.out.println("person1 = " + person1);

    }

    @Test
    void test5() {
        System.out.println("person = " + person);
        System.out.println(JSONPath.of("$.books[1].name").eval(person));

        JSONPath.set(person, "$.books[1].name", "红楼梦");
        System.out.println("person2 = " + person);

        System.out.println(JSONPath.of("$.books[1].name").eval(person));
    }
}
