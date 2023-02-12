package account.Utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {

    public static LocalDate toLocalDate(String period){
        int[] date = Arrays.stream(period.split("-")).mapToInt(i-> Integer.parseInt(i)).toArray();
        return LocalDate.of(date[1], date[0],1);
    }
}
