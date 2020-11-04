package ru.dexsys.user_controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserFromMockDto;

@Mapper(componentModel = "spring")
public interface UserFromMock2EntityMapper {

    @Mappings({
            @Mapping(target = "birthday", source = "birthDay"),
            @Mapping(target = "chatId", source = "chatId", qualifiedByName = "chatId2Id")
    })
    UserEntity apply(UserFromMockDto userEntity);

    @Named("chatId2Id")
    static Long chatId2ChatId(String chatId) {
        try {
            return Long.parseLong(chatId);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

}
