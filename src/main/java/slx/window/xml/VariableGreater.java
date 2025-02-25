package slx.window.xml;

import slx.window.common.util.CommonUtil;
import slx.window.ex.SlxReadException;

public class VariableGreater {

    private static final String[] baseTypes = {"int", "byte", "short", "long", "float", "double", "boolean", "char", "String"};

    private static Object getBaseTypeObj(String type, String defaultVal) {
        Object o = null;
        // 基本类型
        switch (type) {
            case "int":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Integer.parseInt(defaultVal);
                } else {
                    o = 0;
                }
                break;
            case "byte":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Byte.parseByte(defaultVal);
                } else {
                    o = (byte) 0;
                }
                break;
            case "short":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Short.parseShort(defaultVal);
                } else {
                    o = (short) 0;
                }
                break;
            case "long":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Long.parseLong(defaultVal);
                } else {
                    o = 0L;
                }
                break;
            case "double":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Double.parseDouble(defaultVal);
                } else {
                    o = (double) 0.0;
                }
                break;
            case "float":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Float.parseFloat(defaultVal);
                } else {
                    o = (float) 0.0;
                }
                break;
            case "boolean":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = Boolean.parseBoolean(defaultVal);
                } else {
                    o = false;
                }
                break;
            case "char":
                if (!CommonUtil.isEmpty(defaultVal) && !defaultVal.equals("null")) {
                    o = defaultVal.charAt(0);
                } else {
                    o = (char) 0;
                }
                break;
            case "String":
                if (defaultVal != null && !defaultVal.equals("null")) {
                    o = defaultVal;
                }
                break;
            default:
                break;
        }
        return o;
    }

    private static Object getBaseTypeArr(String type, String[] defaultVal) {
        Object o = null;
        // 基本类型
        switch (type) {
            case "int":
                o = new int[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((int[])o)[i] = (int)getBaseTypeObj("int",defaultVal[i]);
                }
                break;
            case "byte":
                o = new byte[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((byte[])o)[i] = (byte)getBaseTypeObj("byte",defaultVal[i]);
                }
                break;
            case "short":
                o = new short[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((short[])o)[i] = (short)getBaseTypeObj("short",defaultVal[i]);
                }
                break;
            case "long":
                o = new long[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((long[])o)[i] = (long)getBaseTypeObj("long",defaultVal[i]);
                }
                break;
            case "double":
                o = new double[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((double[])o)[i] = (double)getBaseTypeObj("double",defaultVal[i]);
                }
                break;
            case "float":
                o = new float[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((float[])o)[i] = (float)getBaseTypeObj("float",defaultVal[i]);
                }
                break;
            case "boolean":
                o = new boolean[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((boolean[])o)[i] = (boolean)getBaseTypeObj("boolean",defaultVal[i]);
                }
                break;
            case "char":
                o = new char[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((char[])o)[i] = (char)getBaseTypeObj("char",defaultVal[i]);
                }
                break;
            case "String":
                o = new String[defaultVal.length];
                for (int i = 0; i < defaultVal.length; i++) {
                    ((String[])o)[i] = (String)getBaseTypeObj("String",defaultVal[i]);
                }
                break;
            default:
                break;
        }
        return o;
    }

    private static boolean isBaseType(String type){
        for (String baseType : baseTypes) {
            if (baseType.equals(type)){
                return true;
            }
        }
        return false;
    }

    public static Object create(String type, String defaultVal) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 是否为基本类型
        Object o = getBaseTypeObj(type, defaultVal);

        if (o==null){
            // 不是基本类型
            // 查看是否为数组类型
            if (type != null && type.endsWith("[]")) {
                //数组类型
                String relType = type.replace("[]", "");
                boolean isBaseType = isBaseType(relType);

                if (isBaseType){
                    if (CommonUtil.isEmpty(defaultVal)) {
                        throw new SlxReadException("default value can not be bull by [" + type + "]");
                    }
                    //基本类型数组
                    return getBaseTypeArr(relType,defaultVal.split(","));
                }else{
                    // 目前不支持引用类型数组
                    return null;
                }
            }
        }

        if (o == null&&!isBaseType(type)) {
            o = Class.forName(type).newInstance();
            if (defaultVal.equals("null")) {
                o = null;
            }
        }
        return o;
    }
}
