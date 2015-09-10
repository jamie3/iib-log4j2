package example.log4j2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class Log4j2 extends MbJavaComputeNode {

	@Override
	public void onInitialize() throws MbException {
		super.onInitialize();

		InputStream in = Log4j2.class.getResourceAsStream("log4j2.xml");
		try {
			Configurator.initialize(null, new ConfigurationSource(in));
		} catch(IOException e) {
			throw new MbUserException(this, "onInitialize()", "", "", e.toString(), null);
		}
	}
	
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, new MbMessage(inAssembly.getMessage()));

		Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
		log.info("Test Log Message");
		
		out.propagate(outAssembly);
	}

}
