package be.ordina.jworks.controller;

import be.ordina.jworks.GameDataApiApplication;
import com.lordofthejars.nosqlunit.redis.RedisRule;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import redis.embedded.RedisServer;

import java.io.IOException;

import static com.lordofthejars.nosqlunit.redis.RedisRule.RedisRuleBuilder.newRedisRule;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GameDataApiApplication.class)
public class GameControllerDocumentation {

    private static RedisServer redisServer;

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Rule
    public RedisRule redisRule = newRedisRule().defaultManagedRedis(6379);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeClass
    public static void beforeClass() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @Before
    public void setUp() {
        RestDocumentationResultHandler document = document("{method-name}", preprocessResponse(prettyPrint()));
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document)
                .build();
    }

    @Test
    public void getGames() throws Exception {
        mockMvc.perform(get("/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @AfterClass
    public static void afterClass() {
        redisServer.stop();
    }
}
