package dbservice.models.dto.mapper;

import dbservice.models.User;
import dbservice.models.dto.UserAuthDto;

public interface UserAuthMapper {
    UserAuthDto map(User user);
}
