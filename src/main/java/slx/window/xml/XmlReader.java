package slx.window.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import slx.window.common.util.CommonUtil;
import slx.window.ex.SlxException;
import slx.window.ex.SlxReadException;
import slx.window.model.MessageType;
import slx.window.model.SlxButton;
import slx.window.win.SlxWindow;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlReader {

    public static void readWindow(String path){
        try {
            InputStream inputFile = XmlReader.class.getClassLoader().getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputFile);

            Element rootElement = document.getRootElement();
            List<Element> variables = rootElement.elements("variable");
            Map<String, Object> variable = getVariable(variables);
            SlxWindow window = getWindow(rootElement, true);
            addButton(window,rootElement,variable);
            variable.put("main",window);
            // system variable
            variable.put("error",0);
            variable.put("info",1);
            variable.put("warning",2);
            variable.put("question",3);
            // TODO 目前仅实现addButton  后续开发中
            //look and feel
            Element lookAndFeel = rootElement.element("lookAndFeel");
            if (lookAndFeel != null) {
                String s = lookAndFeel.attributeValue("value");
                if (!CommonUtil.isEmpty(s)) UIManager.setLookAndFeel(s);
            }
            window.start();
        } catch (DocumentException | UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> getVariable(List<Element> variables){
        Map<String, Object> variableMap = new HashMap<>();
        for (Element variable : variables){
            String name = variable.attributeValue("name");
            String type = variable.attributeValue("type");
            if (CommonUtil.isEmpty(name)||CommonUtil.isEmpty(type)){
                throw new SlxReadException("The name or type of the variable is not set");
            }
            String defaultVal = variable.attributeValue("default");
            try {
                Object o = VariableGreater.create(type, defaultVal);
                variableMap.put(name, o);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return variableMap;
    }

    public static SlxWindow getWindow(Element element,boolean main){
        String title = element.attributeValue("title");
        String width = element.attributeValue("width");
        String height = element.attributeValue("height");
        return new SlxWindow(title,Integer.parseInt(width),Integer.parseInt(height),main);
    }

    public static void addButton(SlxWindow window,Element root,Map<String, Object> variables){
        List<Element> button = root.elements("button");
        for (Element buttonElement : button){
            String buttonLabel = buttonElement.getText();
            if (buttonLabel.startsWith("{{")&&buttonLabel.endsWith("}}")) {
                buttonLabel = buttonLabel.replace("{{", "").replace("}}", "");
                buttonLabel = (String)variables.get(buttonLabel);
            }
            String x= buttonElement.attributeValue("x");
            String y= buttonElement.attributeValue("y");
            String width = buttonElement.attributeValue("width");
            String height = buttonElement.attributeValue("height");
            SlxButton btn = new SlxButton(buttonLabel);
            window.addComponent(btn,Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(width),Integer.parseInt(height));
            String s = buttonElement.attributeValue("onClick");
            if (!CommonUtil.isEmpty(s)){
                Element click = root.element(s);
                String clickCode = click.getTextTrim();
                for (Map.Entry<String, String> e : GrammarLibrary.library.entrySet()) {
                    if (clickCode.contains(e.getKey())){
                        clickCode =  clickCode.replace(e.getKey(),e.getValue());
                    }
                }
                String finalClickCode = clickCode;
                btn.onClick(e->{
                    executeCode(finalClickCode,variables);
                });
            }
        }
    }

    private static void executeCode(String code,Map<String, Object> variables){
        String importStr = "import slx.window.ex.SlxException;\n";
        code = importStr+code;
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        for (String key : variables.keySet()) {
            engine.put(key, variables.get(key));
        }
        try {
            engine.eval(code);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readWindow("index/main.xml");
    }
}
