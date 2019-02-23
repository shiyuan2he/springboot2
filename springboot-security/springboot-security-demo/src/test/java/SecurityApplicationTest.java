import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class SecurityApplicationTest {

    @Test
    public void main() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String password = encoder.encode("123");
        log.info("加密串：{}",password);

    }
}