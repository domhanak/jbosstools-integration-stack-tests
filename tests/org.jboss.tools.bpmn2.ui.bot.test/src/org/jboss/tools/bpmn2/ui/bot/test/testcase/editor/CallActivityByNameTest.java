package org.jboss.tools.bpmn2.ui.bot.test.testcase.editor;

import org.jboss.tools.bpmn2.ui.bot.test.JBPM6BaseTest;
import org.jboss.tools.bpmn2.ui.bot.test.requirements.ProcessDefinitionRequirement.ProcessDefinition;
import org.junit.Ignore;
import org.kie.api.runtime.KieSession;

@ProcessDefinition(name="BPMN2-CallActivityByName", project="EditorTestProject")
public class CallActivityByNameTest extends JBPM6BaseTest {

	/**
	 * ISSUES - There's no way to set the 'call by name' attribute. Is this a BUG?
	 *  
	 * @throws Exception
	 */
	@Ignore
	@Override
	public void buildProcessModel() {
	}

	@Override
	public void assertRunOfProcessModel(KieSession kSession) {
		// TODO Auto-generated method stub
		
	}
}