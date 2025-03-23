import javax.script.*;

public class CalculatorEngine {
    private final ScriptEngine engine;

    public CalculatorEngine() {
        // Using GraalVM JavaScript engine. Earlier you used "graalvm.js".
        // You can change the engine name if your environment needs it.
        ScriptEngineManager mgr = new ScriptEngineManager();
        engine = mgr.getEngineByName("graal.js");
    }
    public double evaluate(String expression) throws ScriptException {
        Object result = engine.eval(expression);
        return Double.parseDouble(result.toString());
    }
}
