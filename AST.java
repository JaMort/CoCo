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
    public String returnString = "";
    public String indent = "    ";
    public String translate(){
        returnString += "import java.util.*;\nabstract class AST{}\n\n";
    	for (DataTypeDef data : datatypedefs) {
            returnString += "abstract class " + data.dataTypeName + " extends AST {\n" + indent;
            returnString += "public abstract " + data.functionHead + ";\n" + "}\n\n";
            for (Alternative alt : data.alternatives) {
                    returnString += "class " + alt.constructor + " extends " + data.dataTypeName+"{\n";
                    returnString += indent;
                    for (Argument arg : alt.arguments) {
                        returnString += arg.type + " " + arg.name +";\n";
                        returnString += indent;
                    }
                    returnString += alt.constructor +"(";
                    for (Argument arg : alt.arguments) {
                        if(!(arg.equals(alt.arguments.get(alt.arguments.size() - 1)))) {
                            returnString += arg.type + " " + arg.name + ", ";
                        }
                        else {
                            returnString += arg.type + " " + arg.name + ")";
                        }
                    }
                    returnString += "{";
                    for (Argument arg : alt.arguments) {
                        returnString += "this." + arg.name + "=" + arg.name + "; ";
                    }
                    returnString += "}\n" + indent;
                    returnString += "public " + data.functionHead + alt.code +"\n";
                    returnString += "}";
                    returnString += "\n\n";
            }
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
}

class Argument extends AST{
    public String type;
    public String name;
    Argument(String type, String name){this.type=type; this.name=name;}
}
