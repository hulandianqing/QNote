package org.hulan.util.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

/**
 * 功能描述：log日志工具类
 * 时间：2017/4/14 17:52
 *
 * @author ：zhaokuiqiang
 */
public class LogUtil {

    private static volatile String name;
    static {
        name = LogUtil.class.getName();
    }

    /**
     * 功能描述：记录info日志
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     * @param fileName 文件名
     * @param message {} or messsage
     * @param args 参数
     */
    public static void info(String fileName,String message,Object ... args){
        Logger logger = LogFactory.getLogger(fileName,null, Level.INFO);
        logger.info(MarkerManager.getMarker(fileName),message,args);
    }

    /**
     * 功能描述：记录debug日志
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     * @param message
     */
    public static void debug(String message,Object ... args){
        String className = superClassName();
        Logger logger = LogManager.getLogger(className);
        logger.debug(message,args);
    }

    /**
     * 功能描述：记录error日志
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     * @param message
     * @param args
     */
    public static void error(String message,Object ... args){
        String className = superClassName();
        Logger logger = LogManager.getLogger(className);
        logger.error(message,args);
    }

    /**
     * 功能描述：记录error日志
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     * @param message
     * @param e
     */
    public static void error(String message,Throwable e){
        String className = superClassName();
        Logger logger = LogManager.getLogger(className);
        logger.error(message,e);
    }

    /**
     * 功能描述：记录error日志
     * @author:zhaokuiqiang
     * @时间：2017-04-25
     * @param e
     */
    public static void error(Throwable e){
        String className = superClassName();
        Logger logger = LogManager.getLogger(className);
        logger.error(e.getMessage(),e);
    }
    
    private static String superClassName(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement element = null;
        for(int i = 1; i < elements.length; i++) {
            element = elements[i];
            if(!name.equals(element.getClassName())){
                break;
            }
        }
        return element.getClassName();
    }

    public static void main(String[] args) {
        info("logUtil","准备执行{}"," main ");
        try {
            debug("try");
            throw new RuntimeException("报错");
        } catch(Exception e) {
            error(e);
        }
        debug("执行完毕");
//
//        debug("{}","d","b","g");
//        error("{}{}{}","1","2","3");
    }
}
