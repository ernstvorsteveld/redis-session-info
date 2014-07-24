package nl.vorstdev.db.sessionstore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * http://dyashkir.com/2012/01/24/Starting-with-Redis-tutorial.html
 *
 * Some kind of session variable management.
 * <p/>
 * What I try to do here:
 * Create a "user" key value with a set of values.
 * The values are in the map.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdisConfiguration.class)
@ActiveProfiles("development")
public class SessionDataITest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Before
    public void cleanUp() {
        redisTemplate.delete("user1");
    }

    @Test
    public void should_store_session_data() {
        SessionData sessionData = new SessionData();
        sessionData.getValues().put("name", "Name");
        sessionData.getValues().put("status", "Active");

        redisTemplate.boundHashOps("user1").putAll(sessionData.getValues());
        System.out.println(redisTemplate.boundHashOps("user1").values());
        System.out.println(redisTemplate.boundHashOps("user1").keys());

        String name = (String) redisTemplate.boundHashOps("user1").get("name");
        assertThat(name, is("Name"));

        redisTemplate.boundHashOps("user1").put("new", "New");
        System.out.println(redisTemplate.boundHashOps("user1").values());
        System.out.println(redisTemplate.boundHashOps("user1").keys());

        String newValue = (String) redisTemplate.boundHashOps("user1").get("new");
        assertThat(newValue, is("New"));
    }
}
