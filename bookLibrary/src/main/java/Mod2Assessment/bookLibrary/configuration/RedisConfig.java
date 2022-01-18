package Mod2Assessment.bookLibrary.configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RedisConfig {
    private static final Logger logger = Logger.getLogger(RedisConfig.class.getName());
    public static final String REDIS_PASSWORD = "REDIS_PASSWORD"; 
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;

    //change bean name
    @Bean("TODO_REDIS")
    @Scope("singleton")
    public RedisTemplate<String, String> createRedisTemplate(){

        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setDatabase(redisDatabase);
        
        final String redisPassword = System.getenv(REDIS_PASSWORD);
        if (redisPassword != null){
            logger.log(Level.INFO, "Setting Redis Password");
            redisConfig.setPassword(redisPassword);
        }

        final JedisClientConfiguration jedisClient = 
            JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = 
            new JedisConnectionFactory(redisConfig, jedisClient);

        jedisFac.afterPropertiesSet();

        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;

    }


}
