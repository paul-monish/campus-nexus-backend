package edu.in.mckvie.CampusNexus.services.servicesimpl;

import edu.in.mckvie.CampusNexus.services.ConcurrentLoginInterceptorService;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class ConcurrentLoginInterceptorServiceImple implements ConcurrentLoginInterceptorService {
    Logger logger= LoggerFactory.getLogger(ConcurrentLoginInterceptorServiceImple.class);
    private final Jedis jedis;
    @Value("${jwt.secret}")
    private String secret;

    public ConcurrentLoginInterceptorServiceImple(@Value("${spring.redis.host}") String host,
                                                  @Value("${spring.redis.port}") int port) {
        jedis = new Jedis(host, port);
        System.out.println("Server is running: "+jedis.ping());
    }

    public void blacklistToken(String key) {
        long ttl = Jwts.parser().setSigningKey(secret).parseClaimsJws(getData(key)).getBody().getExpiration().getTime()
                - System.currentTimeMillis();
        System.out.println(ttl);
        jedis.expire(key, 60);
    }

    public void saveData(String key, String value) {
        jedis.set(key, value);
        logger.info("save: "+key+" : "+value);
    }

    public String getData(String key) {
        logger.info("get: "+key+" : "+jedis.get(key));
        return jedis.get(key);
    }

    public boolean contain(String key) {
        logger.info("exits: "+key+" : "+jedis.exists(key));
        return jedis.exists(key);
    }

    public void deleteData(String key) {
        logger.info("delete: "+key+" : "+jedis.get(key));
        jedis.del(key);
        logger.info("delete: "+key+" : "+jedis.get(key));
    }
}