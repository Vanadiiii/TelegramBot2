package ru.dexsys.user_controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ru.dexsys.domain.entity.Birthday;
import ru.dexsys.domain.entity.UserEntity;
import ru.dexsys.user_controller.dto.UserDto2;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserEntity2DtoMapper {

    @Mappings({
            @Mapping(target = "birthDay", source = "birthday", qualifiedByName = "birthday2date"),
            @Mapping(target = "id", source = "id", qualifiedByName = "generateUUID")
    })
    UserDto2 apply(UserEntity userEntity);

    @Named("birthday2date")
    static Date birthday2date(Birthday birthday) {
        return Optional.ofNullable(birthday)
                .map(birthday1 ->
                        new GregorianCalendar(
                                0,
                                birthday1.getMonth() - 1,
                                birthday1.getDay())
                                .getTime()
                )
                .orElse(null);
    }

    @Named("generateUUID")
    static UUID generateUUID(Long id) {
        return UUID.randomUUID();
    }


}
