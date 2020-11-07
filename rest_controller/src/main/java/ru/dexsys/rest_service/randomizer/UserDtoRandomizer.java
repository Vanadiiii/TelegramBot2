package ru.dexsys.rest_service.randomizer;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.springframework.stereotype.Component;
import ru.dexsys.rest_service.dto.UserDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class UserDtoRandomizer {
    public UserDto randomize() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .dateRange(LocalDate.now().minusYears(100), LocalDate.now())
                .randomize(
                        field -> field.getName().equals("phone"),
                        () -> "8" + Math.random() * (9_999_999_999L - 1_000_000_000L) + 1_000_000_000L
                )
                .randomize(
                        field -> field.getName().equals("name"),
                        () -> nameList.get(new Random().nextInt(nameList.size()))
                );
        return new EasyRandom(parameters)
                .nextObject(UserDto.class);
    }

    private final List<String> nameList = List.of(
            "Jack", "Harry", "Jacob", "Charlie", "Thomas",
            "George", "Oscar", "James", "William", "Max",
            "Alfie", "Joshua", "Oliver", "Henry", "Leo",
            "Archie", "Ethan", "Joseph", "Freddie", "Samuel",
            "Alexander", "Logan", "Daniel", "Isaac"
    );
}
