import java.util.HashMap;
import java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;

class faux{ // collection of non-OO auxiliary functions (currently just error)
    public static void error(String msg){
	System.err.println("Interpreter error: "+msg);
	System.exit(-1);
    }
}

abstract class AST{
}

class Start extends AST{
    public List<DataTypeDef> datatypedefs;
    Start(List<DataTypeDef> datatypedefs){
	this.datatypedefs=datatypedefs;
    }
    public String translate(){
    	String returnString = "";
    	String indent = "    ";
		returnString += "import java.util.*;\nabstract class AST{}\n\n";
    	for (DataTypeDef data : datatypedefs) {
    		returnString += data.translate();
        }
        return returnString;
    }
}

class DataTypeDef extends AST{
    public String dataTypeName;
    public String functionHead;
    public List<Alternative> alternatives;
    DataTypeDef(String dataTypeName, String functionHead, List<Alternative> alternatives){
	this.dataTypeName=dataTypeName;
	this.functionHead=functionHead;
	this.alternatives=alternatives;
    }
    public String translate(){
    		String returnString = "";
    		String indent = "    ";
    		returnString += "abstract class " + dataTypeName + " extends AST {\n" + indent;
            returnString += "public abstract " + functionHead + ";\n" + "}\n\n";
            for (Alternative alt : alternatives) {
                    returnString += "class " + alt.constructor + " extends " + dataTypeName+"{\n";
                    returnString += indent;
                    returnString += alt.translate();
                    returnString += "}\n" + indent;
                    returnString += "public " + functionHead + alt.code +"\n";
                    returnString += "}";
                    returnString += "\n\n";
        }
        return returnString;
    }
}

class Alternative extends AST{
    public String constructor;
    public List<Argument> arguments;
    public String code;
    Alternative(String constructor, List<Argument> arguments,  String code){
	this.constructor=constructor;
	this.arguments=arguments;
	this.code=code;
    }
    public String translate() {
    	String returnString = "";
    	String indent = "    ";
    	for(Argument arg : arguments) {
    		returnString += arg.type + " " + arg.name + ";\n";
    		returnString += indent;
    	}
    	returnString += constructor + "(";
    	for (Argument arg : arguments) {
    		if(!(arg.equals(arguments.get(arguments.size() - 1))))  returnString += arg.type + " " + arg.name + ", ";
    		else returnString += arg.type + " " + arg.name + ")";
    	}
    	returnString += "{";
    	for (Argument arg : arguments) {
    		returnString += "this." + arg.name + "=" + arg.name + "; ";
    	}
    	return returnString;
    }
}

class Argument extends AST{
    public String type;
    public String name;
    Argument(String type, String name){this.type=type; this.name=name;}
    public String translate() {return "";}
}
