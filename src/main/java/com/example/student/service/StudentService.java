package com.example.student.service;

import com.example.student.VO.Department;
import com.example.student.VO.ResponseTemplateVO;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RestTemplate restTemplate;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Retry(name="basic")
    @RateLimiter(name="limitbasic", fallbackMethod = "HandleFallBack")

    public ResponseTemplateVO getStudentWithDepartment(Long id) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Student student = studentRepository.findById(id).get();
        vo.setStudent(student);
        Department department =
                restTemplate.getForObject("http://localhost:9001/departments/"
                                + student.getDepartmentId(),
                        Department.class);
        vo.setDepartment(department);
        return vo;
    }

    private ResponseTemplateVO HandleFallBack(Long id, RequestNotPermitted rnp) {
        return new ResponseTemplateVO();
    }
}
