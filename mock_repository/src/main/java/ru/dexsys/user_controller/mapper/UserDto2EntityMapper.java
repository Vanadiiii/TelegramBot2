package ru.dexsys.user_controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserDto2;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserDto2EntityMapper {

    @Mappings({
            @Mapping(target = "birthday", source = "birthDay", qualifiedByName = "date2birthday"),
            @Mapping(target = "id", source = "chatId", qualifiedByName = "chatId2Id"),
            @Mapping(target = "chatId", source = "chatId", qualifiedByName = "chatId2Id")
    })
    UserEntity apply(UserDto2 userEntity);

    @Named("date2birthday")
    static Birthday date2birthday(Date birthDay) {
        Calendar calendar = Calendar.getInstance();
        return Optional.ofNullable(birthDay)
                .map(birthday -> new Birthday(
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH)
                ))
                .orElse(null);
    }

    @Named("chatId2Id")
    static Long chatId2ChatId(String chatId) {
        try {
            return Long.parseLong(chatId);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

}
