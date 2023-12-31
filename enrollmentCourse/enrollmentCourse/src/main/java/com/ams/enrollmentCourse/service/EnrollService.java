package com.ams.enrollmentCourse.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ams.enrollmentCourse.dto.EnrollDTO;
import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import com.ams.enrollmentCourse.exception.AlreadySubscribeException;
import com.ams.enrollmentCourse.exception.CoursesNotFoundException;
import com.ams.enrollmentCourse.repository.EnrollRepository;

@Service
public class EnrollService {

    private final EnrollRepository enrollRepository;

    @Autowired
    public EnrollService(EnrollRepository enrollRepository) {
        this.enrollRepository = enrollRepository;
    }

    /**
     * @param courseId
     * @return List<Enroll>
     * @throws Exception
     */
    public List<Enroll> listOfSubscribers(Long courseId) throws Exception {
        if (enrollRepository.findByCourseId(courseId) == null) {
            throw new CoursesNotFoundException("No users registered in this course");
        }
        Enroll exampleEntity = new Enroll();
        exampleEntity.setCourseId(courseId);
        exampleEntity.setSubscribe(true);

        Example<Enroll> example = Example.of(exampleEntity);
        return enrollRepository.findAll(example);
    }

    /**
     * @param courseId
     * @return List<Enroll>
     * @throws Exception
     */
    public List<Enroll> listOfUnSubscribers(Long courseId) throws Exception {
        if (enrollRepository.findByCourseId(courseId) == null) {
            throw new Exception("No users registered in this course");
        }
        Enroll exampleEntity = new Enroll();
        exampleEntity.setCourseId(courseId);
        exampleEntity.setSubscribe(false);

        Example<Enroll> example = Example.of(exampleEntity);
        return enrollRepository.findAll(example);
    }

    public List<Enroll> listOfCourses(Long userId) throws Exception {
        if (enrollRepository.findByUserId(userId) == null) {
            throw new Exception("This user is not enrolled in any course");
        }
        Enroll exampleEntity = new Enroll();
        exampleEntity.setUserId(userId);
        exampleEntity.setSubscribe(true);

        Example<Enroll> example = Example.of(exampleEntity);
        return enrollRepository.findAll(example);
    }

    public Progress getStatus(Long userId, Long courseId) throws Exception {
        return enrollRepository.findByUserIdAndCourseIdAndSubscribe(userId, courseId, true).getStatus();
    }

    public Enroll getById(Long id) throws Exception {
        return enrollRepository.findById(id).orElseThrow(Exception::new);
    }

    public Enroll subscribe(EnrollDTO enroll) throws AlreadySubscribeException, Exception {
        Enroll alrealyExist = enrollRepository.findByUserIdAndCourseId(enroll.getUserId(), enroll.getCourseId());
        if (alrealyExist != null) {
            if (alrealyExist.getSubscribe())
                throw new AlreadySubscribeException("User already subscribe at this course");
            else {
                alrealyExist.setSubscribe(true);
                return enrollRepository.save(alrealyExist);
            }
        } else {
            Enroll newEnroll = new Enroll();
            newEnroll.setCourseId(enroll.getCourseId());
            newEnroll.setUserId(enroll.getUserId());
            newEnroll.setStatus(Progress.ENROLLED);
            newEnroll.setSubscribe(true);
            newEnroll.setCreatedAt(new Date());
            newEnroll.setUpdatedAt(new Date());
            return enrollRepository.save(newEnroll);
        }
    }

    public Enroll unSubscribe(EnrollDTO enroll) throws AlreadySubscribeException, Exception {
        Enroll alrealyExist = enrollRepository.findByUserIdAndCourseId(enroll.getUserId(), enroll.getCourseId());
        if (alrealyExist != null) {
            if (!alrealyExist.getSubscribe())
                throw new AlreadySubscribeException("User already unsubscribe at this course");
            else {
                alrealyExist.setSubscribe(false);
                return enrollRepository.save(alrealyExist);
            }
        } else {
            throw new Exception("User is not enrolled in this course");
        }
    }

    public Enroll updateStatusOfCourse(Long courseId, Long userId, Progress status)
            throws AlreadySubscribeException, Exception {
        Enroll alrealyExist = enrollRepository.findByUserIdAndCourseIdAndSubscribe(userId, courseId, true);
        if (alrealyExist != null) {
            alrealyExist.setStatus(status);
            return enrollRepository.save(alrealyExist);
        } else {
            throw new Exception("User is not enrolled in this course");
        }
    }

}
