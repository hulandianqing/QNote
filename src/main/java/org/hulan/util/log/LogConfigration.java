package org.hulan.util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：
 * 时间：2017/4/14 17:46
 *
 * @author ：zhaokuiqiang
 */
@Configuration
public class LogConfigration {

    @Bean
    public Logger info(){
        return LogManager.getLogger("info");
    }
    @Bean
    public Logger error(){
        return LogManager.getLogger("error");
    }
}
