package org.hulan.util.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.MarkerFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.util.StringUtils;

/**
 * 功能描述：logger生成工厂类
 * 时间：2017/4/24 14:21
 *
 * @author ：zhaokuiqiang
 */
public class LogFactory {

    static String DEFAULT_PATH = "/datalook/";

    /**
     * 功能描述：创建logger
     *
     * @param name
     * @param level
     * @return
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     */
    protected static Logger getLogger(String name, String filePath, Level level) {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration configuration = context.getConfiguration();
        if(configuration.getAppender(name) == null) {
            StrLookup lookup = configuration.getStrSubstitutor().getVariableResolver();
            String fileSize = lookup.lookup("every_file_size");
            String pattern = lookup.lookup("log_pattern");
            if(StringUtils.hasText(filePath)) {
                filePath = lookup.lookup("file_path") + filePath;
            } else {
                filePath = lookup.lookup("file_path") + DEFAULT_PATH;
            }
            LoggerConfig infoConfig = configuration.getLoggerConfig(level.name());
            createAppender(configuration, name, filePath, fileSize, pattern);
            infoConfig.addAppender(createAsyncAppender(configuration, name), infoConfig.getLevel(), null);
        }
        return LogManager.getLogger(level.name());
    }

    /**
     * 功能描述：创建记录日志appender
     *
     * @param configuration
     * @param name
     * @return
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     */
    private static Appender createAppender(Configuration configuration, String name, String filePath, String fileSize, String pattern) {
        RollingFileAppender newAppender = null;
        PatternLayout layout = PatternLayout.createLayout(pattern, null, configuration, null, null, true, true, null, null);
        RollingFileAppender.Builder builder = RollingFileAppender.newBuilder()
                .withFileName(filePath + name + ".log")
                .withName(name)
                .withFilePattern(filePath + name+ ".%d{yyyy-MM-dd}-%i")
                .withConfiguration(configuration)
                .withLayout(layout)
                .withPolicy(TimeBasedTriggeringPolicy.createPolicy("1","true"));
//        if(StringUtils.hasText(fileSize)) {
//            builder.withPolicy(SizeBasedTriggeringPolicy.createPolicy(fileSize));
//        }
        newAppender = builder.build();
        newAppender.start();
        configuration.addAppender(newAppender);
        return newAppender;
    }

    /**
     * 功能描述：创建异步写日志appender
     *
     * @param name
     * @return
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     */
    private static Appender createAsyncAppender(Configuration configuration, String name) {
        AppenderRef appenderRef = AppenderRef.createAppenderRef(name, null,
                null);
        AsyncAppender asyncAppender = AsyncAppender.newBuilder()
                .setName(name)
                .setConfiguration(configuration)
                .setFilter(MarkerFilter.createFilter(name,
                        Filter.Result.ACCEPT, Filter.Result.DENY))
                .setAppenderRefs(new AppenderRef[]{appenderRef}).build();
        asyncAppender.start();
        return asyncAppender;
    }

}
