package edu.du._waxing_home.user.service;

import edu.du._waxing_home.user.domain.Role;
import edu.du._waxing_home.user.domain.User;
import edu.du._waxing_home.user.dto.UserRequestDto;
import edu.du._waxing_home.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 회원가입 로직
     * @param userRequestDto 회원가입 요청 데이터
     * @throws IllegalArgumentException 비밀번호 불일치 또는 중복된 아이디인 경우 예외 발생
     */

    public void registerUser(UserRequestDto userRequestDto) {
        // 아이디 중복 검사
        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        // 비밀번호 일치 여부 검사
        if (!userRequestDto.getPassword().equals(userRequestDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // User 엔티티 생성 및 데이터 저장
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword()); // 시큐리티 제거로 평문 저장
        user.setName(userRequestDto.getName());
        user.setAddress(userRequestDto.getAddress());
        user.setPhone(userRequestDto.getPhone());
        user.setEmail(userRequestDto.getEmail());

        // 데이터베이스에 저장
        userRepository.save(user);
    }
}