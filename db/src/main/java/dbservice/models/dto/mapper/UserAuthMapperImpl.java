package dbservice.models.dto.mapper;

import dbservice.models.User;
import dbservice.models.dto.UserAuthDto;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapperImpl implements UserAuthMapper {

    @Override
    public UserAuthDto map(User user) {
        if (user == null) {
            return null;
        }

        return UserAuthDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .createTokenDateTime(user.getCreateTokenDateTime())
                .role(user.getRole())
                .status(user.isStatus())
                .build();
    }
}
