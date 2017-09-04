package org.hulan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 功能描述：
 * 时间：2017/6/10 17:30
 * @author ：zhaokuiqiang
 */
@SpringBootApplication
public class QNoteApplication /*extends SpringBootServletInitializer*/ {
	public static void main(String[] args) {
		SpringApplication.run(QNoteApplication.class);
	}
	
//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(QNoteApplication.class);
//    }
}
