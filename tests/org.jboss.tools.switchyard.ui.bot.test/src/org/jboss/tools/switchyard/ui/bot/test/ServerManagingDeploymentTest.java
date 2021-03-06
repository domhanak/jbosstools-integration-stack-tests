package org.jboss.tools.switchyard.ui.bot.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.junit.annotation.RequirementRestriction;
import org.eclipse.reddeer.junit.requirement.inject.InjectRequirement;
import org.eclipse.reddeer.junit.requirement.matcher.RequirementMatcher;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.server.ServerRequirementState;
import org.eclipse.reddeer.workbench.impl.editor.TextEditor;
import org.jboss.tools.runtime.reddeer.ServerBase;
import org.jboss.tools.runtime.reddeer.requirement.ServerImplementationType;
import org.jboss.tools.switchyard.reddeer.binding.HTTPBindingPage;
import org.jboss.tools.switchyard.reddeer.component.Service;
import org.jboss.tools.switchyard.reddeer.component.SwitchYardComponent;
import org.jboss.tools.switchyard.reddeer.editor.SwitchYardEditor;
import org.jboss.tools.switchyard.reddeer.requirement.SwitchYardRequirement;
import org.jboss.tools.switchyard.reddeer.requirement.SwitchYardRequirement.SwitchYard;
import org.jboss.tools.switchyard.reddeer.requirement.SwitchYardServerRestriction;
import org.jboss.tools.switchyard.ui.bot.test.util.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author apodhrad
 *
 */
@SwitchYard(state = ServerRequirementState.RUNNING)
@RunWith(RedDeerSuite.class)
public class ServerManagingDeploymentTest {

	public static final String PROJECT_NAME = "deploy";

	@InjectRequirement
	private SwitchYardRequirement switchyardRequirement;

	@RequirementRestriction
	public static RequirementMatcher getRequirementMatcher() {
		return new SwitchYardServerRestriction(ServerImplementationType.ANY);
	}

	@Test
	public void deployTest() throws Exception {
		switchyardRequirement.project(PROJECT_NAME).impl("Bean").binding("HTTP").create();

		new SwitchYardEditor().addBeanImplementation().createJavaInterface("Hello").finish();

		new SwitchYardComponent("Hello").doubleClick();
		TextEditor helloEditor = new TextEditor("Hello.java");
		helloEditor.setText("package com.example.switchyard.deploy;\n" + "public interface Hello {\n"
				+ "\tString sayHello(String name);\n}");
		helloEditor.save();
		helloEditor.close();

		new SwitchYardComponent("HelloBean").doubleClick();
		TextEditor helloBeanEditor = new TextEditor("HelloBean.java");
		helloBeanEditor
				.setText("package com.example.switchyard.deploy;\n" + "import org.switchyard.component.bean.Service;\n"
						+ "@Service(Hello.class)\n" + "public class HelloBean implements Hello {\n" + "\t@Override\n"
						+ "\tpublic String sayHello(String name) {\n" + "\t\treturn \"Hello \" + name;\n\t}\n}");
		helloBeanEditor.save();
		helloBeanEditor.close();

		new Service("Hello").promoteService().activate().setServiceName("HelloService").finish();

		WizardDialog wizard = new Service("HelloService").addBinding("HTTP");
		HTTPBindingPage page = new HTTPBindingPage();
		page.setName("soap-binding");
		page.getContextPath().setText("hello");
		wizard.finish();

		new SwitchYardEditor().save();

		final ServerBase server = switchyardRequirement.getConfiguration().getServer();
		server.deployProject(PROJECT_NAME);
		new WaitUntil(new AbstractWaitCondition() {

			@Override
			public boolean test() {
				try {
					new HttpClient(server.getUrl("hello")).post("World");
				} catch (Throwable t) {
					return false;
				}
				return true;
			}

			@Override
			public String description() {
				return "Checking the deployed project";
			}
		}, TimePeriod.LONG, false);
		String response = new HttpClient(server.getUrl("hello")).post("World");
		assertEquals("Hello World", response);
	}
}
