package com.depromeet.match.user.service;

import com.depromeet.match.core.jwt.JwtResolver;
import com.depromeet.match.error.MatchServiceException;
import com.depromeet.match.jobgroup.JobGroup;
import com.depromeet.match.jobgroup.JobGroupRepository;
import com.depromeet.match.user.User;
import com.depromeet.match.user.dto.UpdateUserRequest;
import com.depromeet.match.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JobGroupRepository jobGroupRepository;

    public UserService(
            UserRepository userRepository,
        JobGroupRepository jobGroupRepository
    ) {
        this.userRepository = userRepository;
        this.jobGroupRepository = jobGroupRepository;
    }

    public String createJwt(long id, String nickName, String profileImageUrl) {
        createUser(id, nickName, profileImageUrl);

        return userRepository.findById(id)
                .map(user -> JwtResolver.createJwt(user.getId(), user.getNickName(), user.getProfileImageUrl()))
                .orElseThrow(() ->
                        new MatchServiceException(String.format("IllegalState occurred in processing sign-in %d", id))
                );
    }

    private void createUser(
            final long id,
            final String nickName,
            final String profileImageUrl
    ) {
        User newUser = User.UserBuilder.anUser()
                .id(id)
                .nickName(nickName)
                .profileImageUrl(profileImageUrl)
                .build();

        userRepository.save(newUser);
    }

    @Transactional
    public User update(long id, UpdateUserRequest request) {
        User byId = userRepository.findById(id).orElseThrow();

        JobGroup jobGroup = jobGroupRepository.findByName(request.getJobGroup());
        byId.update(
                request.getUserName(),
                request.getEmail(),
                request.getPhone(),
                jobGroup,
                request.getBirthday(),
                request.getYearExperience()
        );

        return byId;
    }
}
