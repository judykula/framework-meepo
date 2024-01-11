/*
 * easy come, easy go.
 *
 * contact : syiae.jwy@gmail.com
 *
 * · · · · ||   ..     __       ___      ____  ®
 * · · · · ||  ||  || _ ||   ||    ||   ||      ||
 * · · · · ||  ||  \\_ ||_.||    ||   \\_  ||
 * · · _//                                       ||
 * · · · · · · · · · · · · · · · · · ·· ·    ___//
 */
package com.jwy.meepo.generator;

import com.jwy.meepo.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 *     默认id生成方式，采用snowflake实现，适合低qps场景
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2024/1/10
 */
@Slf4j
@Component
public class DefaultIdGenerator implements IdGenerator{

    @Value("${sys.snowflake.workid:1}")
    private long workId;
    @Value("${sys.snowflake.datacenterId:1}")
    private long datacenterId;

    private SnowFlake snowFlake;
    private AtomicLong latestId = new AtomicLong();

    @PostConstruct
    public void init(){
        snowFlake = new SnowFlake(workId, 1);
    }

    @Override
    public long nextId() {

        long id = 0;
        try {
            id = snowFlake.nextId();
        } catch (Exception e) {
            log.warn("【DIG055】generate id fail", e);
        }

        /*如果qps一直很高(>1000) ，则此段代码会持续执行(因为latestId一直在++，总是比新生成的Id大)，造成性能影响*/
        if(id <= latestId.get()){
            synchronized (latestId){
                if(id <= latestId.get()){
                    log.warn("【DIG064】new generate id: {} and latest id: {}", id, latestId.get());
                    return latestId.incrementAndGet();
                }
            }
        }

        latestId.compareAndSet(latestId.get(), id);
        log.debug("【DIG071】new generate id: {} and latestId: {}", id, latestId.get());

        return id;
    }

}
