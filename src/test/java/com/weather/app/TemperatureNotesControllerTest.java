package com.weather.app;

import com.weather.app.com.weather.app.dao.DefualtTemperatureRepository;
import com.weather.app.com.weather.app.dao.TemperatureRepository;
import com.weather.app.com.weather.app.dao.UsersRepository;
import com.weather.app.model.TemperatureNote;
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
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class TemperatureNotesControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private DefualtTemperatureRepository defualtTemperatureRepository;
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
        this.temperatureRepository.deleteAllInBatch();
        this.usersRepository.deleteAllInBatch();

        usersRepository.save(new User("Mahmoud", "mahmoud@gmail.com", "Y", "1234", "0128188838"));
        user = usersRepository.findByEmailEquals("mahmoud@gmail.com");

        temperatureRepository.save(new TemperatureNote(Calendar.getInstance().getTime(), "cold", user.getId()));
    }

    @Test
    public void getNote() throws Exception {
        mockMvc.perform(get("/temperature/note/5").
                contentType(contentType)).
                andExpect(status().isOk()).
                andExpect(content().string("Chilly weather"));
    }

    @Test
    public void addNote() throws Exception {
        String jasonNote = json(new TemperatureNote(Calendar.getInstance().getTime(),
                "Warm weather", this.user.getId()));
        this.mockMvc.perform(post("/temperature/create")
                .contentType(contentType)
                .content(jasonNote))
                .andExpect(status().isOk());
    }

    @Test
    public void findNotesByUserId() throws Exception {
        mockMvc.perform(get("/temperature/allnotes/" + this.user.getId()).
                contentType(contentType)).
                andExpect(status().isOk());
    }

    @Test
    public void findDefaultNotes() throws Exception {

        mockMvc.perform(get("/temperature/defaultnotes").
                contentType(contentType)).
                andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}