package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
    //given
        String email = "renneadz@gmail.com";
        Student student = new Student("Renne", email, Gender.FEMALE);
        studentRepository.save(student);


    //when
        boolean expected = studentRepository.selectExistsEmail(email);

    //then
        assertThat(expected).isTrue();
    }


    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        //given
        String email = "renneadz@gmail.com";

        //when
        boolean expected = studentRepository.selectExistsEmail(email);

        //then
        assertThat(expected).isFalse();
    }
}