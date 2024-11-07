package fptu.mobile_shop.MobileShop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Collection;

@Slf4j
public class CommonUtil {
    public static <T> boolean isEmpty(T obj) {
        if (obj == null) return true;
        if (obj instanceof Collection)
            return ((Collection<?>) obj).size() == 0;
        return "".equals(obj);
    }

    public static <T> boolean isNotEmpty(T obj) {
        return !isEmpty(obj);
    }

    public static final ObjectMapper mapper = new ObjectMapper();
    public static String toJson(Object instance) {
        if (instance == null) return null;
        try {
            return mapper.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            log.warn(ExceptionUtils.getStackTrace(e));
            return instance.toString();
        }
    }
}
