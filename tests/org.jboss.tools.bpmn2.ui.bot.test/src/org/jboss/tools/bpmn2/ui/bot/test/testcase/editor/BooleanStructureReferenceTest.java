package org.jboss.tools.bpmn2.ui.bot.test.testcase.editor;

import org.jboss.tools.bpmn2.reddeer.editor.ElementType;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.FromDataOutput;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.ParameterMapping;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.Process;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.ToVariable;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.ScriptTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.activities.UserTask;
import org.jboss.tools.bpmn2.reddeer.editor.jbpm.startevents.StartEvent;
import org.jboss.tools.bpmn2.ui.bot.test.JBPM6BaseTest;
import org.jboss.tools.bpmn2.ui.bot.test.requirements.ProcessDefinitionRequirement.ProcessDefinition;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

@ProcessDefinition(name="BPMN2-BooleanStructureRef", project="EditorTestProject")
public class BooleanStructureReferenceTest extends JBPM6BaseTest {

	@Override
	public void buildProcessModel() {
		Process process = new Process("BPMN2-BooleanStructureRef");
		process.addLocalVariable("test", "Boolean");
		
		StartEvent start = new StartEvent("StartProcess");
		start.append("User Task", ElementType.USER_TASK);

		UserTask userTask = new UserTask("User Task");
		userTask.addParameterMapping(new ParameterMapping(new FromDataOutput("testHT", "String"), new ToVariable("test"), ParameterMapping.Type.OUTPUT));
		userTask.append("Script", ElementType.SCRIPT_TASK);

		ScriptTask scriptTask = new ScriptTask("Script");
		scriptTask.setScript("", "System.out.println(\"Result \" + test)");
		scriptTask.append("EndProcess", ElementType.TERMINATE_END_EVENT);
	}

	@Override
	public void assertRunOfProcessModel(KieSession kSession) {
		ProcessInstance processInstance = kSession.startProcess("BPMN2BooleanStructureRef");
		assertTrue(processInstance.getState() == ProcessInstance.STATE_COMPLETED);
	}
	
}