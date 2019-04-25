package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConvertObject {
	public byte[] getByteArrayObject(Object object){
        byte[] byteArrayObject = null;
        
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(); // byte array output stream
            ObjectOutputStream oos = new ObjectOutputStream(bos); // object output stream
            oos.writeObject(object);
            
            oos.close();
            bos.close();
            byteArrayObject = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return byteArrayObject;
        }
        return byteArrayObject;
    }
	
	public UserDataModel getJavaObject(byte[] convertObject){
        UserDataModel obj = null;
        
        ByteArrayInputStream bais; // byte array input stream
        ObjectInputStream ins; // object input stream
        
        try {
        	bais = new ByteArrayInputStream(convertObject);
        
        	ins = new ObjectInputStream(bais);
        	obj =(UserDataModel)ins.readObject();
        
        	ins.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return obj;
	}
}
