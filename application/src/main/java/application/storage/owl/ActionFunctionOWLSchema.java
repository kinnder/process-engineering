package application.storage.owl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.ontology.Individual;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionFunction;
import planning.model.LuaScriptLine;

public class ActionFunctionOWLSchema implements OWLSchema<ActionFunction> {

	private PlanningOWLModel owlModel;

	private LuaScriptLineOWLSchema luaScriptLineOWLSchema;

	public ActionFunctionOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new LuaScriptLineOWLSchema(owlModel));
	}

	ActionFunctionOWLSchema(PlanningOWLModel owlModel, LuaScriptLineOWLSchema luaScriptLineOWLSchema) {
		this.owlModel = owlModel;
		this.luaScriptLineOWLSchema = luaScriptLineOWLSchema;
	}

	@Override
	public Individual combine(ActionFunction actionFunction) {
		final Individual ind_actionFunction = owlModel.newIndividual_ActionFunction();
		final Collection<LuaScriptLine> scriptLines = actionFunction.getScriptLines();
		for (LuaScriptLine scriptLine : scriptLines) {
			final Individual ind_line = luaScriptLineOWLSchema.combine(scriptLine);
			ind_actionFunction.addProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}
		return ind_actionFunction;
	}

	// TODO (2022-12-07 #73): пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	@Override
	public ActionFunction parse(Individual ind_actionFunction) {
		final Map<Integer, LuaScriptLine> scriptLines = new TreeMap<Integer, LuaScriptLine>();
		owlModel.getClass_Line().listInstances().filterKeep((ind_line) -> {
			return ind_actionFunction.hasProperty(owlModel.getObjectProperty_hasLine(), ind_line);
		}).forEachRemaining((ind_line) -> {
			final LuaScriptLine scriptLine = luaScriptLineOWLSchema.parse(ind_line.asIndividual());
			scriptLines.put(scriptLine.getNumber(), scriptLine);
		});
		return new ActionFunction(globals, scriptLines.values());
	}
}
