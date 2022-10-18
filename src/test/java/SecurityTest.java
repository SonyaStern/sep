import com.kth.mse.sep.SepApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = SepApplication.class)
public class SecurityTest {
    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    void testSuccessfulLogin() {
        mvc.perform(formLogin("/perform_login").user("am").password("amPassword"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @SneakyThrows
    @Test
    void testBadPassword() {
        mvc.perform(formLogin("/perform_login").user("am").password("pmPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andDo(print());
    }
    @SneakyThrows
    @Test
    void testBadUsername() {
        mvc.perform(formLogin("/perform_login").user("pm").password("amPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andDo(print());
    }
}