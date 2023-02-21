package apicore.environment;

public class PropertiesAppRegister {
    
    private static String propAppPerfix = null;
    private static String[] propAppArray = null;

    public static String prefix(){
        return propAppPerfix;
    }
    
    public static String[] propAppArray(){
        return propAppArray;
    }

    public static void prefix(String propAppPerfix) {
        if( PropertiesAppRegister.propAppPerfix == null ){
            PropertiesAppRegister.propAppPerfix = propAppPerfix;
        }
    }

    public static void declareAppProps(String... propApp) {
        if( PropertiesAppRegister.propAppArray == null ){
            PropertiesAppRegister.propAppArray = propApp;
        }
    }
}
