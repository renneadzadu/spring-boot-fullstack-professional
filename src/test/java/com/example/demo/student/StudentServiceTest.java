package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock // used because student repository is already tested and we do not need to test it again
    private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        studentService.getAllStudents();
        //then
        verify(studentRepository).findAll();


    }


    @Test
        //@Disabled //they won't run
    void canAddStudent() {
        //given
        // (given this student)
        Student student = new Student("Renne",
                "renneadz@gmail.com",
                Gender.FEMALE);

        //when
        // (when this is called)
        studentService.addStudent(student);

        //then
        // (then verify if student repository has that student
        //if so then capture it and save it. check if the
        //captured student is equal to the expected student.)

        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);

    }

        @Test
        //@Disabled //they won't run
    void willThrowWhenEmailIsTaken() {
        //given ( given this student,)
        Student student = new Student("Renne",
                "renneadz@gmail.com",
                Gender.FEMALE);
        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        //when (when this is called
        //then
        assertThatThrownBy(() -> studentService.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " take");

        verify(studentRepository, never()).save(any());
    }


    @Test
    @Disabled
    void deleteStudentByIdIfExist() {

                //given
        Long id = 1L;
        Student student = new Student(1L,"Renne",
                "renneadz@gmail.com",
                Gender.FEMALE);
        //when
        studentRepository.delete(student);

        //then

        assertThatThrownBy(() -> studentService.deleteStudent(student.getId()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + student.getId() + " does not exists");

        Assertions.assertNull(studentRepository);
        //verify(studentRepository, never()).delete(any());
//        Assertions.assertFalse(id.describeConstable().isPresent());
//        studentRepository.deleteById(student.getId());




    }


}