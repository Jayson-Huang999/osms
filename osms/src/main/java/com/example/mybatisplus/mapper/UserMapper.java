package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.model.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * �û���Ϣ�� Mapper 接口
 * </p>
 *
 * @author lgl
 * @since 2022-03-01
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


    String hasPhone(String phone);

    String hasEmail(String email);

    void addUser(User user);

    String hasUserName(String username);

    Long login(String phone, String password);

    Map<String, String> getInfoByPhone(String phone);

    Map<String, String> getInfoByEmail(String email);

    void updateMessage(String image, short sex, Long id, LocalDateTime dateTime,String nickname,String phone,String username);

    void updatePhone(String phone, Long id, LocalDateTime dateTime);

    void updateEmail(String email, Long id, LocalDateTime dateTime);

    void updateLoginInfo( String password, Long id, LocalDateTime dateTime);

    void removeById(Long id);

    void updateUserScore(Long user_id, Long score);
    
}
