package com.depromeet.match.study;

import com.depromeet.match.core.BaseEntity;
import com.depromeet.match.jobgroup.JobGroup;
import com.depromeet.match.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studyIdGen")
    @SequenceGenerator(name = "studyIdGen", sequenceName = "STUDY_ID_SEQ", allocationSize = 25)
    private long id;

    private String studyName;

    @Embedded
    private Spot spot;

    @Embedded
    private Duration duration;

    @Enumerated(EnumType.STRING)
    private StudyConductWay studyConductWay;

    @Enumerated(EnumType.STRING)
    private StudySubject studySubject;

    @OneToOne
    private User owner;

    @OneToMany
    private Set<JobGroup> target;

    public long getId() {
        return id;
    }

    public String getStudyName() {
        return studyName;
    }

    public Spot getSpot() {
        return spot;
    }

    public Duration getDuration() {
        return duration;
    }

    public StudyConductWay getStudyConductWay() {
        return studyConductWay;
    }

    public StudySubject getStudySubject() {
        return studySubject;
    }

    public User getOwner() {
        return owner;
    }

    public Set<JobGroup> getTarget() {
        return target;
    }


    public static final class StudyBuilder {
        private long id;
        private String studyName;
        private Spot spot;
        private Duration duration;
        private StudyConductWay studyConductWay;
        private StudySubject studySubject;
        private User owner;
        private Set<JobGroup> target;

        private StudyBuilder() {
        }

        public static StudyBuilder aStudy() {
            return new StudyBuilder();
        }

        public StudyBuilder id(long id) {
            this.id = id;
            return this;
        }

        public StudyBuilder studyName(String studyName) {
            this.studyName = studyName;
            return this;
        }

        public StudyBuilder spot(Spot spot) {
            this.spot = spot;
            return this;
        }

        public StudyBuilder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public StudyBuilder studyConductWay(StudyConductWay studyConductWay) {
            this.studyConductWay = studyConductWay;
            return this;
        }

        public StudyBuilder studySubject(StudySubject studySubject) {
            this.studySubject = studySubject;
            return this;
        }

        public StudyBuilder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public StudyBuilder target(Set<JobGroup> target) {
            this.target = target;
            return this;
        }

        public Study build() {
            Study study = new Study();
            study.spot = this.spot;
            study.owner = this.owner;
            study.duration = this.duration;
            study.studySubject = this.studySubject;
            study.studyConductWay = this.studyConductWay;
            study.target = this.target;
            study.studyName = this.studyName;
            study.id = this.id;
            return study;
        }
    }
}
