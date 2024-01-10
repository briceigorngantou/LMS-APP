package com.ams.enrollmentCourse.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ams.enrollmentCourse.dto.EnrollDTO;
import com.ams.enrollmentCourse.entity.Enroll;
import com.ams.enrollmentCourse.entity.Progress;
import com.ams.enrollmentCourse.exception.AlreadySubscribeException;
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
        return enrollRepository.findByCourseIdAndSubscribe(courseId, true);
    }

    /**
     * @param courseId
     * @return List<Enroll>
     * @throws Exception
     */
    public List<Enroll> listOfUnSubscribers(Long courseId) throws Exception {
        return enrollRepository.findByCourseIdAndSubscribe(courseId, false);
    }

    /**
     * @param userId
     * @return List<Enroll>
     * @throws Exception
     */
    public List<Enroll> listOfCourses(Long userId) throws Exception {
        return enrollRepository.findByUserIdAndSubscribe(userId, true);
    }

    /**
     * @param userId
     * @param courseId
     * @return Progress
     * @throws Exception
     */
    public Progress getStatus(Long userId, Long courseId) throws Exception {
        Enroll result = enrollRepository.findByUserIdAndCourseIdAndSubscribe(userId, courseId, true);
        if (result != null) {
            return result.getStatus();
        }
        return null;
    }

    /**
     * @param id
     * @return Enroll
     * @throws Exception
     */
    public Enroll getById(Long id) throws Exception {
        return enrollRepository.findById(id).orElseThrow(Exception::new);
    }

    /**
     * @param enroll
     * @return Enroll
     * @throws AlreadySubscribeException
     * @throws Exception
     */
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

    /**
     * @param enroll
     * @return Enroll
     * @throws AlreadySubscribeException
     * @throws Exception
     */
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

    /**
     * @param courseId
     * @param userId
     * @param status
     * @return Enroll
     * @throws AlreadySubscribeException
     * @throws Exception
     */
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
