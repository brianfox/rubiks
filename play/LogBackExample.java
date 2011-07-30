import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class LogBackExample {

	public static void main(String[] args) {

		Logger logger = LoggerFactory
				.getLogger("chapters.introduction.HelloWorld2");
		logger.debug("Hello world.");

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);

	}

}