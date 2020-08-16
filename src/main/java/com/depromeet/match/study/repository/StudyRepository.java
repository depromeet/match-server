package com.depromeet.match.study.repository;


import com.depromeet.match.study.Study;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StudyRepository {

    private final EntityManager em;

    public StudyRepository(EntityManager em) {
        this.em = em;
    }

    public Study save(Study study) {
        if (em.contains(study)) {
            em.persist(study);
            return study;
        }

        em.merge(study);
        return study;
    }

    public List<Study> findByLatitude(double latitude, double longitude, int offset, int limit) {
        double northLatitude = latitude + 0.02;
        double southLatitude = latitude - 0.02;

        double westLongitude = longitude - 0.02;
        double eastLongitude = longitude + 0.02;

        return em.createQuery("SELECT s FROM Study s WHERE s.spot.latitude BETWEEN :southLatitude AND :northLatitude AND s.spot.longitude BETWEEN :westLongitude AND :eastLongitude", Study.class)
                .setParameter("northLatitude", northLatitude)
                .setParameter("southLatitude", southLatitude)
                .setParameter("westLongitude", westLongitude)
                .setParameter("eastLongitude", eastLongitude)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
