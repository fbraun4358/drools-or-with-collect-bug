package com.example;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.drools.modelcompiler.ExecutableModelProject;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ExecutableModelOrWithCollectErrorsTests {

	private static final String PROCESS_ID = "example";

	@Test
	public void withExecutableModel_Setter() throws IOException {

		KieBase kbase = loadRules(true);
		KieSession session = kbase.newKieSession();

		List<ClassWithValue> list = new ArrayList<>();
		ClassWithValue cwov = new ClassWithValue();
		ClassWithValue cwv = new ClassWithValue();
		cwv.setValue("VALUE");
		
		session.insert(list);
		session.insert(cwov);
		session.insert(cwv);
		
		session.startProcess(PROCESS_ID);
		
		session.fireAllRules();

		assertThat(list)
				.containsExactlyInAnyOrder(cwov, cwv);
	}
	
	@Test
	public void withoutExecutableModel_Setter() throws IOException {

		KieBase kbase = loadRules(false);
		KieSession session = kbase.newKieSession();

		List<ClassWithValue> list = new ArrayList<>();
		ClassWithValue cwov = new ClassWithValue();
		ClassWithValue cwv = new ClassWithValue();
		cwv.setValue("VALUE");

		session.insert(list);
		session.insert(cwov);
		session.insert(cwv);
		
		session.startProcess(PROCESS_ID);
		
		session.fireAllRules();

		assertThat(list)
				.containsExactlyInAnyOrder(cwov, cwv);

	}
	
	private static KieBase loadRules(boolean useExecutable) throws IOException {

		Path resources = Paths.get("src", "test", "resources");
		
		KieServices services = KieServices.Factory.get();
		KieFileSystem kfs = services.newKieFileSystem();

		Resource drl = services.getResources()
				.newFileSystemResource(
						resources.resolve("drl/rules.drl").toFile());
		
		Resource rf = services.getResources()
				.newFileSystemResource(
						resources.resolve("rf/ruleflow.rf").toFile());

		kfs.write(drl);
		kfs.write(rf);
		
		KieBuilder builder = services.newKieBuilder(kfs);
		
		builder = useExecutable ?
				builder.buildAll(ExecutableModelProject.class) :
					builder.buildAll();
		
		if (builder.getResults().hasMessages(Level.ERROR)) {
	        List<Message> errors = builder.getResults().getMessages(Level.ERROR);
	        StringBuilder sb = new StringBuilder("Errors:");
	        for (Message msg : errors) {
	            sb.append("\n  " + prettyBuildMessage(msg));
	        }
	        
	        throw new RuntimeException(sb.toString());
	    }
		
		KieContainer container = services.newKieContainer(
				services.getRepository().getDefaultReleaseId());
		
		return container.getKieBase();
	}
	
	private static String prettyBuildMessage(Message msg) {
	    return "Message: {"
	        + "id="+ msg.getId()
	        + ", level=" + msg.getLevel()
	        + ", path=" + msg.getPath()
	        + ", line=" + msg.getLine()
	        + ", column=" + msg.getColumn()
	        + ", text=\"" + msg.getText() + "\""
	        + "}";
	}
}
