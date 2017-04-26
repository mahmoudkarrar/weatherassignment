package com.weather.app;

import com.weather.app.com.weather.app.dao.UsersRepository;
import com.weather.app.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Mahmoud.Fathy on 4/25/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherAssignmentApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UsersRepository usersRepository;
    private User user;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        this.usersRepository.deleteAllInBatch();
        this.user = new User("Mahmoud", "mahmoud@gmail.com", "Y", "1234", "0128188838");
        usersRepository.save(this.user);
    }


    @Test
    public void findUser() throws Exception {
        String jasonUser = json(this.user);
        this.mockMvc.perform(post("/users/login")
                .contentType(contentType)
                .content(jasonUser))
                .andExpect(status().isOk())
                .andExpect(content().json(jasonUser));
    }

    @Test
    public void addUser() throws Exception {
        String jasonUser = json(new User("Alex", "alex@gmail.com", "Y", "1234", "0128188838"));
        this.mockMvc.perform(post("/users/create")
                .contentType(contentType)
                .content(jasonUser))
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}